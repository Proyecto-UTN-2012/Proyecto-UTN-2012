package org.utn.proyecto.helpful.integrart.integrar_t_android;

import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.User;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.DataStorageService;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class UserProvider implements Provider<User> {
	private final static String USER_KEY = "currentUser";
	private final DataStorageService dbService;
	
	@Inject
	public UserProvider(DataStorageService dbService){
		this.dbService = dbService;
	}
	
	@Override
	public User get() {
		return dbService.get(USER_KEY, User.class);
	}

}
