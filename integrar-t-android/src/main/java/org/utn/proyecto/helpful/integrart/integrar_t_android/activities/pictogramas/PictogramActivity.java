package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.pictogramas;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.Event;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.EventBus;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.EventListener;

import android.os.Bundle;

import com.google.inject.Inject;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;

@ContentView(R.layout.pictogramas)
public class PictogramActivity extends RoboActivity implements EventListener<Void>{
	@Inject
	private PictogramLoader loader;
	@Inject
	private PictogramUpdateService updateService;
	@Inject
	private EventBus bus;
	
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 bus.addEventListener(UpdatePictogramsCompleteEvent.class, this);
		 updateService.findUpdate();
	 }

	@Override
	public void onEvent(Event<Void> event) {
		
	}
}
