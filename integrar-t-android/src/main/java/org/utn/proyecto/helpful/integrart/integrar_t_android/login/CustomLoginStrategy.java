package org.utn.proyecto.helpful.integrart.integrar_t_android.login;

import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.User;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.EventBus;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.ComunicationService;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.DataStorageService;

import android.content.Context;

//TODO Hay que implementar
public class CustomLoginStrategy extends LoginStrategy {

	public CustomLoginStrategy(Context context, ComunicationService comService,
			DataStorageService dbService, EventBus bus) {
		super(context, comService, dbService, bus);
	}

	@Override
	public void login() {
		User user = new User("dummyUser", "", "integrart");
		setCurrentUser(user);
	}

}
