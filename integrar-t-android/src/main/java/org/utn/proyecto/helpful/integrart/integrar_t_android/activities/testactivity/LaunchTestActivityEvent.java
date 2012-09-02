package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.testactivity;

import org.utn.proyecto.helpful.integrart.integrar_t_android.events.Event;

import android.content.Context;

public class LaunchTestActivityEvent extends Event<Void> {
	public LaunchTestActivityEvent(Context context){
		super(context);
	}
}
