package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.pictogramas;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.ActivityResource;
import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.User;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.EventBus;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.ComunicationService;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.ComunicationService.ExternalResourceType;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.ComunicationService.OnArriveResource;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.DataStorageService;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.FileSystemService;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.UpdateService;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.UpdateService.OnArriveNewResources;

import roboguice.inject.ContextSingleton;

import com.google.gson.Gson;
import com.google.inject.Inject;

@ContextSingleton
public class PictogramUpdateService implements OnArriveNewResources, OnArriveResource{
	private static final String ACTIVITY_NAME = "pictogramActivity";
	private static final String[] PICTOGRAM_LEVEL = {
		"pictogramActivity.pictograms.level.1",
		"pictogramActivity.pictograms.level.2",
		"pictogramActivity.pictograms.level.3"
	};
	private static final String GET_DATA_RESOURCE = "updateData";
	private final UpdateService updateService;
	private final ComunicationService comService;
	private final FileSystemService fileService;
	private final DataStorageService db;
	private final User user;
	private final EventBus bus;
	private final Gson gson = new Gson();
	
	@Inject
	public PictogramUpdateService(UpdateService updateService, ComunicationService comService, FileSystemService fileService, EventBus bus, User user, DataStorageService db){
		this.updateService = updateService;
		this.comService = comService;
		this.fileService = fileService;
		this.bus = bus;
		this.user = user;
		this.db = db;
		prepareData(PICTOGRAM_LEVEL[0]);
		prepareData(PICTOGRAM_LEVEL[1]);
		prepareData(PICTOGRAM_LEVEL[2]);
	}
	
	private void prepareData(String key){
		key = user.getUserName() + "." + key;
		if(!db.contain(key)) 
			db.put(key, new PictogramData[0]);
	}
	
	public void findUpdate(){
		updateService.findUpdates(ACTIVITY_NAME, this);
	}

	@Override
	public void onArriveNewResources(List<ActivityResource> resources) {
			if(resources.isEmpty()){
				bus.dispatch(new UpdatePictogramsCompleteEvent());
				return;
			}
			for(ActivityResource res : resources){
				InputStream is = comService.findStream(ExternalResourceType.STATICS, res.getPath());
				fileService.addResource(ACTIVITY_NAME, res.getResourceName(), res.getResourceType(), is); 
			}
			findData();
	}
	
	private void findData(){
		comService.findResource(
				ExternalResourceType.SETTINGS, 
				GET_DATA_RESOURCE, 
				new String[]{ACTIVITY_NAME, user.getUserName(), updateService.getDeviceId()}, 
				this);
	}

	@Override
	public void onArrive(String json) {
		PictogramData[] pictograms = gson.fromJson(json, PictogramData[].class);
		for(PictogramData pictogram : pictograms){
			addPictogram(pictogram);
		}
		bus.dispatch(new UpdatePictogramsCompleteEvent());
	}
	
	private void addPictogram(PictogramData pictogram){
		List<PictogramData> list = new ArrayList<PictogramData>();
		for(int level : pictogram.getLevels()){
			PictogramData[] pictograms = db.get(user.getUserName() + "." + PICTOGRAM_LEVEL[level-1], PictogramData[].class);
			for(PictogramData data : pictograms){
				list.add(data);
			}
			list.add(pictogram);
			db.put(user.getUserName() + "." + PICTOGRAM_LEVEL[level-1], list.toArray(new PictogramData[0]));			
		}
	}
}
