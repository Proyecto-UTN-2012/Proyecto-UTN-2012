package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.testactivity;

import java.io.InputStream;
import java.util.List;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.pictogramas.LaunchPictogramEvent;
import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.ActivityResource;
import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.ResourceType;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.EventBus;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.ComunicationService;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.ComunicationService.ExternalResourceType;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.FileSystemService;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.UpdateService;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.UpdateService.OnArriveNewResources;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.inject.Inject;

@ContentView(R.layout.test_activity)
public class TestActivity extends RoboActivity {
	private final static String NAME = "testActivity";
	@InjectView(R.id.testImage)
	private ImageView image;
	
	@InjectView(R.id.updateTestButton)
	private Button updateButton;

	@InjectView(R.id.testPictogram)
	private Button pictogramsButton;

	@InjectView(R.id.loadTestResourceButton)
	private Button loadResourcesButton;

	@InjectView(R.id.loadTestSoundButton)
	private Button loadSoundButton;
	
	@Inject
	private UpdateService updateService;
	
	@Inject
	private ComunicationService comService;
	
	@Inject
	private FileSystemService fileService;
	
	@Inject
	private EventBus bus;
	
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 updateButton.setOnClickListener(new UpdateButtonListener());
		 loadResourcesButton.setOnClickListener(new LoadImageListener());
		 loadSoundButton.setOnClickListener(new LoadSoundListener());
		 pictogramsButton.setOnClickListener(new LaunchPictogramsListener(this));
	 }
	 
	 @Override
	 public void onStop(){
		 super.onStop();
		 Log.d("Test Activity", "Stop");
	 }
	 
	 @Override
	 public void onDestroy(){
		 super.onDestroy();
		 Log.d("Test Activity", "Destroy");
	 }
	 
	private class LaunchPictogramsListener implements View.OnClickListener{
		private final Context context;
		
		public LaunchPictogramsListener(Context context){
			this.context = context;
		}
		
		@Override
		public void onClick(View v) {
			bus.dispatch(new LaunchPictogramEvent(context));
		}
		
	}
	 
	private class UpdateButtonListener implements View.OnClickListener{
		@Override
		public void onClick(View v) {
	        if(comService.isOnLine()){
	        	updateService.findUpdates("testActivity", new OnArriveNewResources(){
					@Override
					public void onArriveNewResources(
							List<ActivityResource> resources) {
							if(resources.isEmpty()) return;
							for(ActivityResource res : resources){
								InputStream is = comService.findStream(ExternalResourceType.STATICS, res.getPath());
								fileService.addResource(NAME, res.getResourceName(), res.getResourceType(), is); 
							}
					}
	        		
	        	});
			}		
		}
	}
	
	private class LoadImageListener implements View.OnClickListener{
		@Override
		public void onClick(View v) {
			String[] names = fileService.getResourcesNames(NAME, ResourceType.IMAGE);
			if(names.length > 0)
				image.setImageDrawable((Drawable)fileService.getResource(NAME, names[0]).getResource());
		}	
	}
	
	private class LoadSoundListener implements View.OnClickListener, OnPreparedListener, OnCompletionListener{
		@Override
		public void onClick(View v) {
			String[] names = fileService.getResourcesNames(NAME, ResourceType.SOUND);
			for(String name : names){
				MediaPlayer player = (MediaPlayer)fileService.getResource(NAME, name).getResource();
				player.setOnPreparedListener(this);
				player.setOnCompletionListener(this);
				try {
					player.prepare();
				} catch (Exception e) {
					throw new RuntimeException(e);
				} 
			}
		}

		@Override
		public void onPrepared(MediaPlayer mp) {
			mp.start();
			
		}

		@Override
		public void onCompletion(MediaPlayer mp) {
			mp.release();
		}	
	}
}
