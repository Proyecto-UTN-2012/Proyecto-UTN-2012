package org.utn.proyecto.helpful.integrart.integrar_t_android.login;

import org.utn.proyecto.helpful.integrart.integrar_t_android.events.EventBus;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.ComunicationService;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.DataStorageService;

import android.content.Context;

public abstract class LoginStrategy {
	protected final Context context;
	protected final ComunicationService comService;
	protected final DataStorageService dbService;
	protected final EventBus bus;
	
	public LoginStrategy(Context context, ComunicationService comService, DataStorageService dbService, EventBus bus){
		this.context = context;
		this.comService = comService;
		this.dbService = dbService;
		this.bus = bus;
	}
	public abstract void login();
}
