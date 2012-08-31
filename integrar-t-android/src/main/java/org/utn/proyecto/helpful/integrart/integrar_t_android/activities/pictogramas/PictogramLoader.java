package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.pictogramas;

import java.util.ArrayList;
import java.util.List;

import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.Resource;
import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.User;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.DataStorageService;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.FileSystemService;

import roboguice.inject.ContextSingleton;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;

import com.google.inject.Inject;

@ContextSingleton
public class PictogramLoader {
	private final FileSystemService fileService;
	private final DataStorageService db;
	private final User user;
	
	private static final String ACTIVITY_NAME = "pictogramActivity";
	private static final String[] PICTOGRAM_LEVEL = {
		"pictogramActivity.pictograms.level.1",
		"pictogramActivity.pictograms.level.2",
		"pictogramActivity.pictograms.level.3"
	};
	
	@Inject
	public PictogramLoader(FileSystemService fileService, DataStorageService db, User user){
		this.fileService = fileService;
		this.db = db;
		this.user = user;
	}
	
	public List<Pictogram> getPictograms(int level){
		List<Pictogram> list = new ArrayList<Pictogram>();
		PictogramData[] dataArray = db.get(user.getUserName() + "." + PICTOGRAM_LEVEL[level-1], PictogramData[].class);
		for(PictogramData data : dataArray){
			Pictogram pictogram = buildPictogram(data, level);
			list.add(pictogram);
		}
		return list;
	}
	
	private Pictogram buildPictogram(PictogramData data, int level){
		Drawable image = findImage(data);
		MediaPlayer sound = findSound(data);
		Pictogram pictogram = new Pictogram(data.getName(), level, image, sound);
		return pictogram;
	}
	
	private Drawable findImage(PictogramData data){
		String name = data.getName().replace(" ", "_") + "_image.png";
		Resource<Drawable> resource = fileService.getResource(ACTIVITY_NAME, name);
		return resource.getResource();
	}
	
	private MediaPlayer findSound(PictogramData data){
		String name = data.getName().replace(" ", "_") + "_sound.mp3";
		Resource<MediaPlayer> resource = fileService.getResource(ACTIVITY_NAME, name);
		return resource.getResource();
	}
}
