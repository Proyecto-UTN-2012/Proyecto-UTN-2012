package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar;

import org.utn.proyecto.helpful.integrart.integrar_t_android.events.LaunchActivityEvent;

import android.content.Context;

public class LaunchOrganizarEvent extends LaunchActivityEvent {

	public LaunchOrganizarEvent(Context context) {
		super(context, OrganizarActivity.class);
	}

}
