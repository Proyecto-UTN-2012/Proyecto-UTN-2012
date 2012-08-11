package org.utn.proyecto.helpful.integrart.integrar_t_android.login;

import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.User;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.EventBus;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.ComunicationService;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.DataStorageService;

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

public class GoogleLoginStrategy extends LoginStrategy {
	private static final String GOOGLE_ACCOUNT_TYPE = "com.google";
	private static final String AUTH_TOKEN_TYPE = "oauth2:https://www.googleapis.com/auth/tasks.readonly";

	public GoogleLoginStrategy(Context context, ComunicationService comService,
			DataStorageService dbService, EventBus bus) {
		super(context, comService, dbService, bus);
	}

	@Override
	public void login() {
		//throw new NotAccountException("This device not has account yet");
		AccountManager accountManager = AccountManager.get(context);
		Account[] accounts = accountManager.getAccountsByType(GOOGLE_ACCOUNT_TYPE);
		if(accounts.length < 1) throw new NotAccountException("This device not has account yet");
		final String userName = accounts[0].name;
		final String accountType = accounts[0].type;
		Bundle options = new Bundle();
		if(comService.isOffLine()){
			setCurrentUser(new User(userName, userName, accountType));
			return;
		}
		accountManager.getAuthToken(accounts[0], AUTH_TOKEN_TYPE, options, (Activity)context, 
				new AccountManagerCallback<Bundle>() {
					@Override
					public void run(AccountManagerFuture<Bundle> future) {
						String token = "";
						try {
							Bundle result = future.getResult();
							token = result.getString(AccountManager.KEY_AUTHTOKEN);
							
							setCurrentUser(new User(userName, userName, accountType, token));
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
}
