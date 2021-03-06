package org.utn.proyecto.helpful.integrart.integrar_t_android;

import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar.LaunchCalendarEvent;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar.LaunchOrganizarEvent;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar.LaunchShowCalendarTaskEvent;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar.LaunchTaskTransitionEvent;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.cantaconcali.LaunchCantaConCaliEvent;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.comosehace.LaunchComoSeHaceEvent;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.conociendoacali.LaunchConociendoACaliEvent;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.cuentos.LaunchCuentosEvent;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.dibujaconcali.LaunchDibujaConCaliEvent;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.hablaconcali.LaunchHablaConCaliEvent;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.handplay.LaunchHandPlayEvent;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.pictogramas.LaunchPictogramEvent;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.testactivity.LaunchTestActivityEvent;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.Event;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.EventBus;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.EventListener;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.LaunchMenuEvent;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.ShowLoginEvent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ActividadManager implements EventListener<Class<Activity>>{
	@Inject
	public ActividadManager(EventBus bus){
		bus.addEventListener(LaunchMenuEvent.class, this);
		bus.addEventListener(ShowLoginEvent.class, this);
		bus.addEventListener(LaunchPictogramEvent.class, this);
		bus.addEventListener(LaunchComoSeHaceEvent.class, this);
		bus.addEventListener(LaunchCalendarEvent.class, this);
		bus.addEventListener(LaunchOrganizarEvent.class, this);
		bus.addEventListener(LaunchHablaConCaliEvent.class, this);
		bus.addEventListener(LaunchCantaConCaliEvent.class, this);
		bus.addEventListener(LaunchTestActivityEvent.class, this);
		bus.addEventListener(LaunchCuentosEvent.class, this);
		bus.addEventListener(LaunchShowCalendarTaskEvent.class, this);
		bus.addEventListener(LaunchTaskTransitionEvent.class, this);
		bus.addEventListener(LaunchHandPlayEvent.class, this);
		bus.addEventListener(LaunchDibujaConCaliEvent.class, this);
		bus.addEventListener(LaunchConociendoACaliEvent.class, this);
	}
	
    @Override
    public void onEvent(Event<Class<Activity>> event) {
        Context context = event.getContext();
        Intent intent = new Intent(context, event.getData());
        context.startActivity(intent);
    }
}
