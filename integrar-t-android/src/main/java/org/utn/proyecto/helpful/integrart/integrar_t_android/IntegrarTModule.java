package org.utn.proyecto.helpful.integrart.integrar_t_android;

import com.google.inject.AbstractModule;

public class IntegrarTModule extends AbstractModule {

	@Override
	protected void configure() {
		//Con la annotation ContextSingleton se puede hacer esto
		//this.bind(ComunicationService.class);
	}

}
