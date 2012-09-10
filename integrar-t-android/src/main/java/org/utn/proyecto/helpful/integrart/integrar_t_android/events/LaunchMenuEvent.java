package org.utn.proyecto.helpful.integrart.integrar_t_android.events;

import org.utn.proyecto.helpful.integrart.integrar_t_android.menu.ItemListActivity;

import android.content.Context;



public class LaunchMenuEvent extends LaunchActivityEvent {
	
	public LaunchMenuEvent(Context context){
		super(context, ItemListActivity.class);
	}
}
