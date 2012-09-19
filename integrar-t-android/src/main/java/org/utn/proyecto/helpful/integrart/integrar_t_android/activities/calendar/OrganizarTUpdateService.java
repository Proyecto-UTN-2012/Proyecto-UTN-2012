package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar;

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

import com.google.gson.Gson;
import com.google.inject.Inject;

public class OrganizarTUpdateService implements OnArriveNewResources, OnArriveResource{
	private static final String ACTIVITY_NAME = "calendarActivity";
	private static final String GET_DATA_RESOURCE = "updateData";
	
	private final UpdateService updateService;
	private final ComunicationService comService;
	private final FileSystemService fileService;
	private final DataStorageService db;
	private final User user;
	private final EventBus bus;
	private final Gson gson = new Gson();
	
	@Inject
	public OrganizarTUpdateService(UpdateService updateService, ComunicationService comService, FileSystemService fileService, EventBus bus, User user, DataStorageService db){
		this.updateService = updateService;
		this.comService = comService;
		this.fileService = fileService;
		this.bus = bus;
		this.user = user;
		this.db = db;
	}
	
	public void findUpdate(){
		updateService.findUpdates(ACTIVITY_NAME, this);
	}

	@Override
	public void onArriveNewResources(List<ActivityResource> resources) {
		if(resources.isEmpty()){
			bus.dispatch(new UpdateTaskTypesCompleteEvent());
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
		TaskTypeData[] tasks = gson.fromJson(json, TaskTypeData[].class);
		for(TaskTypeData task : tasks){
			addTask(task);
		}
		bus.dispatch(new UpdateTaskTypesCompleteEvent());
	}
	
	private void addTask(TaskTypeData task){
		if(!db.contain(getTasksKey())) db.put(getTasksKey(), new TaskTypeData[0]);
		List<TaskTypeData> list = new ArrayList<TaskTypeData>();
		TaskTypeData[] tasks = db.get(getTasksKey(), TaskTypeData[].class);
		for(TaskTypeData data : tasks){
			list.add(data);
		}
		list.add(task);
		db.put(getTasksKey(), list.toArray(new TaskTypeData[0]));
	}
	
	private String getTasksKey(){
		return getTaskTypesKey(user);
	}
	
	public static String getTaskTypesKey(User user){
		return user.getUserName() + "." + ACTIVITY_NAME + ".taskTypes";
	}
}
