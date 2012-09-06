package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.testactivity;

import org.utn.proyecto.helpful.integrart.integrar_t_android.events.LaunchActivityEvent;

import android.content.Context;

public class LaunchTestActivityEvent extends LaunchActivityEvent {
	public LaunchTestActivityEvent(Context context){
		super(context, TestActivity.class);
	}
}
