package org.utn.proyecto.helpful.integrart.integrar_t_android.services;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.utn.proyecto.helpful.integrart.integrar_t_android.R;

import roboguice.inject.ContextSingleton;
import roboguice.inject.InjectResource;
import android.net.http.AndroidHttpClient;

import com.google.inject.Inject;

@ContextSingleton
public class ComunicationService {
	@InjectResource(R.string.staticsUrl)	private String staticUrl;
	@InjectResource(R.string.settingsUrl)	private String settingsUrl;
	@InjectResource(R.string.metricsUrl)	private String metricsUrl;
	
	private OnLineMode mode;
	
	private final List<OnChangeOnLineMode> changeOnLineModeListeners = new ArrayList<ComunicationService.OnChangeOnLineMode>();
	@Inject
	public ComunicationService(){
		this.mode = OnLineMode.ON;
	}
	
	public String getSettingsUrl(){
		return settingsUrl;
	}

	public String getStaticUrl() {
		return staticUrl;
	}

	public String getMetricsUrl() {
		return metricsUrl;
	}
	
	public OnLineMode evaluateComunication(){
		try {
			URLConnection conn = new URL(settingsUrl).openConnection();
			conn.setConnectTimeout(500);
			conn.connect();
			return OnLineMode.ON;
		} catch (Exception e) {
			setOnLineMode(OnLineMode.OFF);
			return mode;
		}
	}
	
	public Future<String> findResource(ExternalResourceType type, String resource){
		return findResource(type, resource, new String[0]);
	}
	
	public Future<String> findResource(ExternalResourceType type, String resource, String[] attributes){
		String url = buildUrl(type, resource, attributes);
		return findResourceByUrl(url);
	}
	
	public InputStream findStream(ExternalResourceType type, String resource){
		return findStream(type, resource, new String[0]);
	}
	
	public InputStream findStream(ExternalResourceType type, String resource, String[] attributes){
		String url = buildUrl(type, resource, attributes);
		return findStreamByUrl(url);
	}
	
	public InputStream findStreamByUrl(String address){
		try {
			URL url = new URL(address);
			return (InputStream) url.getContent();
		} catch (Exception e) {
			setOnLineMode(OnLineMode.OFF);
			throw new ComunicationException(e);
		}
	}
	
	public Future<String> findResourceByUrl(final String url){
		try{		
			return Executors.newSingleThreadExecutor().submit(new Callable<String>() {
				@Override
				public String call() throws Exception {
					AndroidHttpClient client = AndroidHttpClient.newInstance("Integrar-T");
					HttpGet get = new HttpGet(url);
					return client.execute(get, new BasicResponseHandler());
				}
			});
		}catch(Exception e){
			setOnLineMode(OnLineMode.OFF);
			throw new ComunicationException(e);
		}			
	}
	
	public void addChangeOnLineModeListener(OnChangeOnLineMode listener){
		this.changeOnLineModeListeners.add(listener);
	}
	
	public void removeChangeOnLineModeListener(OnChangeOnLineMode listener){
		this.changeOnLineModeListeners.remove(listener);
	}
	
	public void setOnLineMode(OnLineMode mode){
		if(this.mode == mode) return;
		changeOnLineMode(mode);
	}
	
	public OnLineMode getOnLineMode(){
		return mode;
	}
	
	public boolean isOnLine(){
		return mode.isOn();
	}
	
	public boolean isOffLine(){
		return mode.isOff();
	}
	
	private void changeOnLineMode(OnLineMode mode){
		this.mode = mode;
		for(OnChangeOnLineMode listener : changeOnLineModeListeners){
			listener.onChangeMode(mode);
		}
	}
	
	private String buildUrl(ExternalResourceType type, String resource, String[] attributes){
		StringBuffer sb = new StringBuffer(getExternalresourceUrl(type));
		sb.append("/" + resource);
		for(String attribute : attributes){
			sb.append("/" + attribute);
		}
		return sb.toString();
	}
	
	private String getExternalresourceUrl(ExternalResourceType type){
		if(type == ExternalResourceType.SETTINGS)
			return settingsUrl + "/rest";
		if(type == ExternalResourceType.METRICS)
			return metricsUrl + "rest";
		return staticUrl;
	}
	
	public enum ExternalResourceType{
		SETTINGS,
		METRICS,
		STATICS;
	}
	
	public enum OnLineMode{
		ON(0),
		OFF(1);
		
		private int mode;
		
		private OnLineMode(int value){
			this.mode = value;
		}
		
		public boolean isOn(){
			return mode == 0;
		}
		
		public boolean isOff(){
			return mode == 1;
		}
	}
	
	public interface OnChangeOnLineMode{
		public void onChangeMode(OnLineMode mode);
	}
	
	public interface OnArriveResource{
		public void onArrive(String resource);
	}
}


