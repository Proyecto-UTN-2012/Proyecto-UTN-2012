package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar;

import java.util.Calendar;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;
import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.User;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.EventBus;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.DataStorageService;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.inject.Inject;

@ContentView(R.layout.organizar_t)
public class OrganizarActivity extends RoboActivity {
	private final static String ORGANIZAR_T_PACKAGE_KEY = ".calendar.tasks.";
	
	private Calendar date = Calendar.getInstance();
	
	@Inject
	private User user;
	
	@Inject
	private DataStorageService db;
	
	@Inject
	private EventBus bus;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String dateKey = DateFormat.format("yyyy.MMMM.dd", date).toString();
		if(!db.contain(user.getUserName() + ORGANIZAR_T_PACKAGE_KEY + dateKey)){
			showEmptyPanel();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.organizar_t_menu, menu);
		return true;			
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		if(item.getItemId() == R.id.calendar){
			bus.dispatch(new LaunchCalendarEvent(this));
			return true;
		}
		return false;
	}
	
	private void showEmptyPanel(){
		
	}
}
