package org.utn.proyecto.helpful.integrart.integrar_t_android;

import java.util.List;

import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.ActivityResource;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.EventBus;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.LaunchMenuEvent;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.ShowLoginEvent;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.ComunicationService;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.ComunicationService.OnLineMode;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.DataStorageService;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.UpdateService.OnArriveNewResources;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.google.inject.Inject;

@ContentView(R.layout.main)
public class IntegrarTMainActivity extends RoboActivity implements OnArriveNewResources{
//	@Inject
//	private UpdateService updateService;
	
	@Inject 
	private ActividadManager activityManager;
	
	@Inject
	private EventBus bus;
	
	@Inject
	private ComunicationService comunicationService;
	
	@Inject
	private DataStorageService dbService;
	
	@InjectView(R.id.mainInitButton)
	private Button mainButton;
	
	@InjectResource(R.string.registerUserMessage)
	private String registerMessage;

	@InjectResource(R.string.registerUserTitle)
	private String registerTitle;

	private static String TAG = "integrar-t-android";

    /**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after 
     * previously being shut down then this Bundle contains the data it most 
     * recently supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it is null.</b>
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
        OnLineMode mode = comunicationService.evaluateComunication();
        Toast.makeText(this, "OnLine mode: " + mode.name(), Toast.LENGTH_LONG).show();
        
        mainButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				bus.dispatch(new LaunchMenuEvent(v.getContext()));
			}
		});
//        if(mode==OnLineMode.ON)
//        	updateService.findUpdates("testActivity", this);
    }
    
    /**
     * Siempre tiene que validar que el usuario esté registrado.
     * si no, no puede usar la aplicación
     */
    @Override
    public void onResume(){
    	super.onResume();
    	validateLogin();
    }

	@Override
	public void onArriveNewResources(List<ActivityResource> resources) {
//		if(resources.isEmpty()) return;
//		InputStream is = comunicationService.findStream(ExternalResourceType.STATICS, resources.get(0).getPath());
//		Drawable d = Drawable.createFromStream(is, "src");
//		image.setImageDrawable(d);
	}
	
	/**
	 * Valida si ya existe un usuario registrado, sino es la primera vez y tiene que mostrar el login.
	 * 
	 */
	private void validateLogin(){
		if(!dbService.contain("currentUser")){
			showRegisterMessage(new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					showLogin();					
				}
			}, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					android.os.Process.killProcess(android.os.Process.myPid());
				}
			});
		}
		//new ValidateLoginAsyncTask(this, dbService, bus).execute();
	}
	
	private void showLogin(){
		bus.dispatch(new ShowLoginEvent(this));
	}
	
	private void showRegisterMessage(
			DialogInterface.OnClickListener okHandler, 
			DialogInterface.OnClickListener cancelHandler){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(registerMessage)
			.setTitle(registerTitle)
			.setCancelable(false)
			.setPositiveButton(R.string.yes, okHandler)
			.setNegativeButton(R.string.no, cancelHandler);
		builder.create().show();
	}
	
//	private class ValidateLoginAsyncTask extends AsyncTask<Void, Void, Void>{
//		private final DataStorageService service;
//		private final Context context;
//		private final EventBus bus;
//		
//		public ValidateLoginAsyncTask(Context context, DataStorageService service, EventBus bus){
//			this.context = context;
//			this.service = service;
//			this.bus = bus;
//		}
//		
//		@Override
//		protected Void doInBackground(Void... params) {
//			if(!service.contain("currentUser")){
//				bus.dispatch(new ShowLoginEvent(context));
//			}
//			return null;
//		}
//		
//	}
	
}

