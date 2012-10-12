package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.handplay;

import org.utn.proyecto.helpful.integrart.integrar_t_android.events.LaunchActivityEvent;

import android.content.Context;

public class LaunchHandPlayEvent extends LaunchActivityEvent {

	public LaunchHandPlayEvent(Context context) {
		super(context, HandPlayActivity.class);
	}

}
