package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar;

import org.utn.proyecto.helpful.integrart.integrar_t_android.events.LaunchActivityEvent;

import android.content.Context;

public class LaunchShowCalendarTaskEvent extends LaunchActivityEvent {

	public LaunchShowCalendarTaskEvent(Context context) {
		super(context, ShowTaskActivity.class);
	}

}
