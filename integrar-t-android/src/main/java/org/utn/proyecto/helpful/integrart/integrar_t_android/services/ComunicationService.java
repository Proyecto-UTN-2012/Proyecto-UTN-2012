package org.utn.proyecto.helpful.integrart.integrar_t_android.services;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.utn.proyecto.helpful.integrart.integrar_t_android.R;

import roboguice.inject.InjectResource;
import android.net.http.AndroidHttpClient;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
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
				return Executors.newSingleThreadExecutor().submit(new Callable<OnLineMode>() {
					@Override
					public OnLineMode call() throws Exception {
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
				}).get();
			} catch (Exception e) {
				return OnLineMode.OFF;
			}
	}
	
	public void sendMessage(ExternalResourceType type, String resource){
		sendMessage(type, resource, new String[0]);
	}
	
	public void sendMessage(ExternalResourceType type, String resource, final String json, OnArriveEmpty handler ){
		if(mode == OnLineMode.OFF) return;
		final String url = buildUrl(type, resource, new String[0]);
		try{		
			final AndroidHttpClient client = AndroidHttpClient.newInstance("Integrar-T");
			ExecutorService executor = new ComunicationThreadPoolExecutor(client, handler);
			executor.submit(new Callable<String>() {
				@Override
				public String call() throws Exception {
					HttpPost post = new HttpPost(url);
					StringEntity entity = new StringEntity(json);
					post.setEntity(entity);
					post.setHeader("Accept", "application/json");
					post.setHeader("Content-type", "application/json");
					return client.execute(post, new BasicResponseHandler());
				}
			});
		}catch(Exception e){
			setOnLineMode(OnLineMode.OFF);
			throw new ComunicationException(e);
		}
	}
	
	public void sendMessage(ExternalResourceType type, String resource, final String json){
		sendMessage(type, resource, json, null);
	}
	
	public void sendMessage(ExternalResourceType type, String resource, String[] attributes){
		if(mode == OnLineMode.OFF) return;
		findResource(type, resource, null);
	}
	
	public void findResource(ExternalResourceType type, String resource, OnArriveResource handler){
		findResource(type, resource, new String[0], handler);
	}
	
	public void findResource(ExternalResourceType type, String resource, String[] attributes, OnArriveResource handler){
		String url = buildUrl(type, resource, attributes);
		findResourceByUrl(url, handler);
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
	
	public Future<String> findResourceByUrl(final String url, OnArriveResource handler){
		try{		
			final AndroidHttpClient client = AndroidHttpClient.newInstance("Integrar-T");
			ExecutorService executor = new ComunicationThreadPoolExecutor(client, handler);
			return executor.submit(new Callable<String>() {
				@Override
				public String call() throws Exception {
					//AndroidHttpClient client = AndroidHttpClient.newInstance("Integrar-T");
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
			return metricsUrl + "/rest";
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
	
	public interface OnArriveEmpty{
		public void onArrive();
	}
	
	private class ComunicationThreadPoolExecutor extends ThreadPoolExecutor{
		private final OnArriveResource handler;
		private final OnArriveEmpty emptyHandler;
		private final AndroidHttpClient client;
		private Future<String> future;
		
		public ComunicationThreadPoolExecutor(AndroidHttpClient client){
			super(2,2,10, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(5));
			this.client = client;
			this.handler = null;
			this.emptyHandler = null;
		}
		public ComunicationThreadPoolExecutor(AndroidHttpClient client, OnArriveResource handler){
			super(2,2,10, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(5));
			this.client = client;
			this.handler = handler;
			this.emptyHandler = null;
		}

		public ComunicationThreadPoolExecutor(AndroidHttpClient client, OnArriveEmpty handler){
			super(2,2,10, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(5));
			this.client = client;
			this.handler = null;
			this.emptyHandler = handler;
		}
		@Override
		protected void afterExecute(Runnable r, Throwable t){
			try {
				client.close();
				if(handler != null)
					handler.onArrive(future.get());
				if(emptyHandler!=null)
					emptyHandler.onArrive();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		@SuppressWarnings({ "hiding", "unchecked" })
		@Override
		public <String> Future<String> submit(Callable<String> task){
			future = (Future<java.lang.String>) super.submit(task);
			return (Future<String>) future;
		}
	}
}


