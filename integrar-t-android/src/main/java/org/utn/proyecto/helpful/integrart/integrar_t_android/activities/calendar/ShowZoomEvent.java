package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar;

import org.utn.proyecto.helpful.integrart.integrar_t_android.events.Event;

import android.content.Context;

public class ShowZoomEvent extends Event<Boolean> {
	public ShowZoomEvent(Context context, Boolean data){
		super(context, data);
	}
}
