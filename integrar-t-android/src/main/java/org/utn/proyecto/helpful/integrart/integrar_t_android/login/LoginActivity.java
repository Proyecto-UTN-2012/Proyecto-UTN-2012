package org.utn.proyecto.helpful.integrart.integrar_t_android.login;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;
import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.User;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.Event;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.EventBus;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.EventListener;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.LaunchMenuEvent;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.ComunicationService;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.ComunicationService.ExternalResourceType;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.DataStorageService;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;
import android.app.AlertDialog;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.inject.Inject;

@ContentView(R.layout.login)
public class LoginActivity extends RoboActivity implements EventListener<User>{
	private LoginStrategy googleLoginStrategy;
	private LoginStrategy othersLoginStrategy;
	private LoginStrategy customLoginStrategy;
	
	@InjectView(R.id.googleLogin)
	private Button googleLogin;

	@InjectView(R.id.customLogin)
	private Button customLogin;
	
	@Inject
	private ComunicationService comunicationService;
	
	@Inject
	private DataStorageService dbService;
	
	@Inject
	private EventBus bus;
	
	@InjectResource(R.string.notUserExceptionMessage)
	private String notUserExceptionMessage;
	
	@InjectResource(R.string.notUserExceptionTitle)
	private String notUserExceptionTitle;
	
	private User[] users;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		googleLoginStrategy = new GoogleLoginStrategy(this, comunicationService, dbService, bus);
		//TODO othersLoginStrategy = new OthersLoginStrategy(this, comunicationService, dbService);
		customLoginStrategy = new CustomLoginStrategy(this, comunicationService, dbService, bus);
		
		bus.addEventListener(SetUserEvent.class, this);
		
		googleLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try{
					googleLoginStrategy.login();					
				} catch (NotAccountException e){
					showExceptionMessage();
				}
			}
		});
		customLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try{
					customLoginStrategy.login();					
				} catch (NotAccountException e){
					showExceptionMessage();
				}
			}
		});
	}
	
	public void showExceptionMessage(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(notUserExceptionMessage)
			.setTitle(notUserExceptionTitle);
		builder.create().show();
	}
	
	public void setUser(User user){
		checkFirstUser(user);
		//TODO checkNewUser(user);
		setCurrentUser(user);
		bus.dispatch(new LaunchMenuEvent(this));
		this.finish();
	}
	
	private void checkFirstUser(User user) {
		String serialId = Secure.getString(getContentResolver(), Secure.ANDROID_ID);
		if(dbService.contain("users")) return;
		users = new User[0];
		dbService.put("users", users);
		user.setDevice(serialId);
		comunicationService.sendMessage(ExternalResourceType.SETTINGS, "signIn", new Gson().toJson(user));
	}

	private void setCurrentUser(User user){
		dbService.put("currentUser", user);
	}

	@Override
	public void onEvent(Event<User> event) {
		setUser(event.getData());
	}

	public LoginStrategy getOthersLoginStrategy() {
		return othersLoginStrategy;
	}

	public void setOthersLoginStrategy(LoginStrategy othersLoginStrategy) {
		this.othersLoginStrategy = othersLoginStrategy;
	}
}
