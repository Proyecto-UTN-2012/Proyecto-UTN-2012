package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.cantaconcali;

import java.util.ArrayList;
import java.util.List;

import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.Resource;
import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.User;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.DataStorageService;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.FileSystemService;

import roboguice.inject.ContextSingleton;
import android.media.MediaPlayer;

import com.google.inject.Inject;

@ContextSingleton
public class CantaLoader {
	private final FileSystemService fileService;
	private final DataStorageService db;
	private final User user;
	
	private static final String ACTIVITY_NAME = "cantaActivity";
	
	@Inject
	public CantaLoader(FileSystemService fileService, DataStorageService db, User user){
		this.fileService = fileService;
		this.db = db;
		this.user = user;
	}
	
	public List<Cancion> getSongs(){
		List<Cancion> list = new ArrayList<Cancion>();
		CantaData[] dataArray = db.get(user.getUserName() + "." + ACTIVITY_NAME + ".songs", CantaData[].class);
		for(CantaData data : dataArray){
			Cancion song = new Cancion(data.getName(), findSound(data));
			list.add(song);
		}
		return list;
	}
	
	private MediaPlayer findSound(CantaData data){
		String name = data.getName().replace(" ", "_") + "_sound.mp3";
		Resource<MediaPlayer> resource = fileService.getResource(ACTIVITY_NAME, name);
		return resource.getResource();
	}
}
