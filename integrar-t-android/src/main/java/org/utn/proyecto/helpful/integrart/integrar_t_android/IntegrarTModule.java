package org.utn.proyecto.helpful.integrart.integrar_t_android;

import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.User;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.DataStorageService;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.DataStorageServiceImpl;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.FileSystemService;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.FileSystemServiceImpl;

import com.google.inject.AbstractModule;

public class IntegrarTModule extends AbstractModule {

	@Override
	protected void configure() {
		this.bind(DataStorageService.class).to(DataStorageServiceImpl.class);
		this.bind(FileSystemService.class).to(FileSystemServiceImpl.class);
		this.bind(User.class).toProvider(UserProvider.class);
	}

}
