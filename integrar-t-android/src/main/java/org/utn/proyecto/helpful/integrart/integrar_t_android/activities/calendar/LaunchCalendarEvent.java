package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar;

import org.utn.proyecto.helpful.integrart.integrar_t_android.events.LaunchActivityEvent;

import android.content.Context;

public class LaunchCalendarEvent extends LaunchActivityEvent {
	public LaunchCalendarEvent(Context context){
		super(context, CalendarActivity.class);
	}

}
