package org.utn.proyecto.helpful.integrart.integrar_t_android.services;

import java.lang.reflect.Type;
import java.util.List;

import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.ActivityResource;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.ComunicationService.ExternalResourceType;

import roboguice.inject.ContextSingleton;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;

@ContextSingleton
public class UpdateService {
	private final static String RESOURCE_NAME = "updateRequest";
	private final ComunicationService comunicationService;
	
	private final Context context;
	
	private String deviceId;
	
	private final Gson gson = new Gson();
	
	@Inject
	public UpdateService(ComunicationService comunicationService, Context context){
		this.comunicationService = comunicationService;
		this.context = context;
	}
	
	//TODO hay que poner un callback
	public void findUpdates(String activityName, OnArriveNewResources handler){
		try {
			String json = comunicationService.findResource(ExternalResourceType.SETTINGS, RESOURCE_NAME,
					new String[]{"pasutmarcelo", getDeviceId(), activityName}).get();
			Log.i("UpdateService", json);
			List<ActivityResource> list = getActivitiesFromJson(json);
			Log.i("UpdateService", list.toString());
			handler.onArriveNewResources(list);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private List<ActivityResource> getActivitiesFromJson(String json){
		Type collectionType = new TypeToken<List<ActivityResource>>(){}.getType();
		List<ActivityResource> list = gson.fromJson(json, collectionType);
		return list;
	}
	
	private String getDeviceId(){
		if(deviceId==null) deviceId = ((TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
		return deviceId;
	}
	
	public interface OnArriveNewResources{
		public void onArriveNewResources(List<ActivityResource> resources);
	}
}