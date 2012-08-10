package org.utn.proyecto.helpful.integrart.integrar_t_android;

import org.utn.proyecto.helpful.integrart.integrar_t_android.events.Event;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.EventBus;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.EventListener;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.LaunchMenuEvent;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.ShowLoginEvent;
import org.utn.proyecto.helpful.integrart.integrar_t_android.login.LoginActivity;

import android.content.Context;
import android.content.Intent;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ActividadManager{
	@Inject
	public ActividadManager(EventBus bus){
		bus.addEventListener(LaunchMenuEvent.class, new LaunchMenuListener());
		bus.addEventListener(ShowLoginEvent.class, new LaunchLoginListener());
	}
	
	private class LaunchMenuListener implements EventListener<Void>{
		@Override
		public void onEvent(Event<Void> event) {
//			Context context = event.getContext();
//			Intent intent = new Intent(context, MenuActivity.class);
//			context.startActivity(intent);
		}
	}
	
	private class LaunchLoginListener implements EventListener<Void>{
		@Override
		public void onEvent(Event<Void> event) {
			Context context = event.getContext();
			Intent intent = new Intent(context, LoginActivity.class);
			context.startActivity(intent);
		}
	}
}
