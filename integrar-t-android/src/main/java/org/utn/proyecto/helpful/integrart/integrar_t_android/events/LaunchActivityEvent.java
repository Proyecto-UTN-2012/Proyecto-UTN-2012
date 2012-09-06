package org.utn.proyecto.helpful.integrart.integrar_t_android.events;

import android.app.Activity;
import android.content.Context;

public abstract class LaunchActivityEvent extends Event<Class<? extends Activity>> {
	public LaunchActivityEvent(Context context, Class<?extends Activity> clazz){
		super(context, clazz);
	}
}
