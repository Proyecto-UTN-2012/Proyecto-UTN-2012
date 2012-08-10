package org.utn.proyecto.helpful.integrart.integrar_t_android.login;

import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.User;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.Event;

import android.content.Context;

public class SetUserEvent extends Event<User> {
	public SetUserEvent(Context context, User user){
		super(context, user);
	}

}
