
	 package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.cuentos;

	 import org.utn.proyecto.helpful.integrart.integrar_t_android.events.LaunchActivityEvent;

	 import android.content.Context;

	 public class LaunchCuentosEvent extends LaunchActivityEvent {
	     public LaunchCuentosEvent(Context context){
	         super(context, CuentosActivity.class);
	     }
	 }
