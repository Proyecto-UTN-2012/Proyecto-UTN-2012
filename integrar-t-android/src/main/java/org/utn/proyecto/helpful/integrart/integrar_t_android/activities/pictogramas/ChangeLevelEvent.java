package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.pictogramas;

import org.utn.proyecto.helpful.integrart.integrar_t_android.events.Event;

import android.content.Context;

public class ChangeLevelEvent extends Event<Integer> {
	
	public ChangeLevelEvent(Context context, Integer level){
		super(context,level);
	}

}
