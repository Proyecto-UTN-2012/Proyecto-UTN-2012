package org.utn.proyecto.helpful.integrart.integrar_t_android.services;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.ActivityResource;
import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.User;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.ComunicationService.ExternalResourceType;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.ComunicationService.OnArriveResource;

import roboguice.inject.ContextSingleton;
import android.content.Context;
import android.provider.Settings.Secure;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;

@ContextSingleton
public class UpdateService{
	private final static String RESOURCE_NAME = "updateRequest";
	private final ComunicationService comunicationService;
	private final Context context;
	
	private final User user;
	
	private String deviceId;
	
	private final Gson gson = new Gson();
	
	@Inject
	public UpdateService(Context context, ComunicationService comunicationService, User user){
		this.comunicationService = comunicationService;
		this.user = user;
		this.context = context;
	}
	
	public void findUpdates(String activityName, final OnArriveNewResources handler){
		if(comunicationService.isOffLine()){
			handler.onArriveNewResources(new ArrayList<ActivityResource>());
			return;
		}
		comunicationService.findResource(ExternalResourceType.SETTINGS, RESOURCE_NAME,
				new String[]{user.getUserName(), getDeviceId(), activityName}, new OnArriveResource() {
					@Override
					public void onArrive(String json) {
						try {
							Log.i("UpdateService", json);
							List<ActivityResource> list = getActivitiesFromJson(json);
							Log.i("UpdateService", list.toString());
							handler.onArriveNewResources(list);
						} catch (Exception e) {
							throw new RuntimeException(e);
						}	
					}
				});
	}
	
	private List<ActivityResource> getActivitiesFromJson(String json){
		Type collectionType = new TypeToken<List<ActivityResource>>(){}.getType();
		List<ActivityResource> list = gson.fromJson(json, collectionType);
		return list;
	}
	
	public String getDeviceId(){
		if(deviceId==null) deviceId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
		//if(deviceId==null) deviceId = ((TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
		return deviceId;
	}
	
	public interface OnArriveNewResources{
		public void onArriveNewResources(List<ActivityResource> resources);
	}
}
