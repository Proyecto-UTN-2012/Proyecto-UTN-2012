package org.utn.proyecto.helpful.integrart.integrar_t_android.login;

import java.util.ArrayList;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;
import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.User;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.Event;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.EventBus;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.EventListener;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.ComunicationService;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.DataStorageService;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.inject.Inject;

@ContentView(R.layout.login)
public class LoginActivity extends RoboActivity implements EventListener<User>{
	@Inject
	private GoogleLoginStrategy googleLoginStrategy;
	private LoginStrategy othersLoginStrategy;
	private LoginStrategy customLoginStrategy;
	
	@InjectView(R.id.googleLogin)
	private Button googleLogin;
	
	@Inject
	private ComunicationService comunicationService;
	
	@Inject
	private DataStorageService dbService;
	
	@Inject
	private EventBus bus;
	
	private User[] users;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		//googleLoginStrategy = new GoogleLoginStrategy(this, comunicationService, dbService, bus);
		//TODO othersLoginStrategy = new OthersLoginStrategy(this, comunicationService, dbService);
		//TODO customLoginStrategy = new CustomLoginStrategy(this, comunicationService, dbService);
		
		bus.addEventListener(SetUserEvent.class, this);
		
		googleLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				googleLoginStrategy.login();
			}
		});
	}
	
	public void setUser(User user){
		checkFirstUser(user);
		//TODO checkNewUser(user);
		setCurrentUser(user);
	}
	
	private void checkFirstUser(User user) {
		if(dbService.contain("users")) return;
		dbService.put("users", new ArrayList<User>());
		//TODO enviar registración a backend
		
	}

	private void setCurrentUser(User user){
		dbService.put("currentUser", user);
	}

	@Override
	public void onEvent(Event<User> event) {
		setUser(event.getData());
	}
}
