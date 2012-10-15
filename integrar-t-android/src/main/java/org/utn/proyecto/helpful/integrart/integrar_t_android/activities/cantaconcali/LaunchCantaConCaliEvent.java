package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.cantaconcali;

import org.utn.proyecto.helpful.integrart.integrar_t_android.events.LaunchActivityEvent;

import android.content.Context;

public class LaunchCantaConCaliEvent extends LaunchActivityEvent {
	public LaunchCantaConCaliEvent(Context context){
		super(context, CantaConCaliActivity.class);
	}
	

}
