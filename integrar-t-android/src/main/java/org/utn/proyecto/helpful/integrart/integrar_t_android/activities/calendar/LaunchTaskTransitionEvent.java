package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar;

import org.utn.proyecto.helpful.integrart.integrar_t_android.events.LaunchActivityEvent;

import android.content.Context;

public class LaunchTaskTransitionEvent extends LaunchActivityEvent {

	public LaunchTaskTransitionEvent(Context context) {
		super(context, TaskTransitionActivity.class);
	}

}
