package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.pictogramas;

import org.utn.proyecto.helpful.integrart.integrar_t_android.events.LaunchActivityEvent;

import android.content.Context;

public class LaunchPictogramEvent extends LaunchActivityEvent {
	public LaunchPictogramEvent(Context context){
		super(context, PictogramActivity.class);
	}
}
