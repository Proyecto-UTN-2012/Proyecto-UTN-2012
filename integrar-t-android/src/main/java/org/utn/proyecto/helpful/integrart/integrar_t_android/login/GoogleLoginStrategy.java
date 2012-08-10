package org.utn.proyecto.helpful.integrart.integrar_t_android.login;

import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.User;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.EventBus;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.ComunicationService;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.DataStorageService;

import com.google.inject.Inject;

import roboguice.inject.ContextSingleton;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

@ContextSingleton
public class GoogleLoginStrategy extends LoginStrategy {
	private static final String GOOGLE_ACCOUNT_TYPE = "com.google";
	private static final String AUTH_TOKEN_TYPE = "oauth2:https://www.googleapis.com/auth/tasks.readonly";

	@Inject
	public GoogleLoginStrategy(Context context, ComunicationService comService,
			DataStorageService dbService, EventBus bus) {
		super(context, comService, dbService, bus);
	}

	@Override
	public void login() {
		AccountManager accountManager = AccountManager.get(context);
		Account[] accounts = accountManager.getAccountsByType(GOOGLE_ACCOUNT_TYPE);
		final String userName = accounts[0].name;
		final String accountType = accounts[0].type;
		Bundle options = new Bundle();
		if(comService.isOffLine()){
			setUser(new User(userName, userName, accountType));
			return;
		}
		accountManager.getAuthToken(accounts[0], AUTH_TOKEN_TYPE, options, (Activity)context, 
				new AccountManagerCallback<Bundle>() {
					@Override
					public void run(AccountManagerFuture<Bundle> result) {
						String token = "";
						try {
							token = result.getResult().getString(AccountManager.KEY_AUTHTOKEN);
							setUser(new User(userName, userName, accountType, token));
						} catch (OperationCanceledException e) {
							android.os.Process.killProcess(android.os.Process.myPid());
						} catch (Exception e) {
							android.os.Process.killProcess(android.os.Process.myPid());
							e.printStackTrace();
						}
						Log.d("Login", "Token: " + token);
					}
				}, new Handler(new Handler.Callback() {
					
					@Override
					public boolean handleMessage(Message msg) {
						Log.d("Login", msg.toString());
						return false;
					}
				}));
	}
	
	private void setUser(User user){
		bus.dispatch(new SetUserEvent(context, user));
	}

}
