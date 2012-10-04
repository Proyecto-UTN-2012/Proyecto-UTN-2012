package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar;

import java.util.Calendar;
import java.util.Date;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;
import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.User;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.EventBus;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.DataStorageService;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.inject.Inject;

@ContentView(R.layout.organizar_t)
public class OrganizarActivity extends RoboActivity{
	public final static String ORGANIZAR_T_PACKAGE_KEY = ".calendar.tasks.";
	public final static String ORGANIZAR_T_PACKAGE_WEEK_KEY = ".calendar.tasks.week.";
	public final static String LAST_UPDATE_KEY = ".calendar.lastUpdate";
	public final static SparseIntArray DAYS_OF_WEEK = new SparseIntArray(7);
	{
		DAYS_OF_WEEK.put(Calendar.SUNDAY, R.string.sunday);
		DAYS_OF_WEEK.put(Calendar.MONDAY, R.string.monday);
		DAYS_OF_WEEK.put(Calendar.TUESDAY, R.string.tuesday);
		DAYS_OF_WEEK.put(Calendar.WEDNESDAY, R.string.wednesday);
		DAYS_OF_WEEK.put(Calendar.THURSDAY, R.string.thursday);
		DAYS_OF_WEEK.put(Calendar.FRIDAY, R.string.friday);
		DAYS_OF_WEEK.put(Calendar.SATURDAY, R.string.saturday);
	}
	private Calendar date = Calendar.getInstance();
	
	@Inject
	private User user;
	
	@Inject
	private DataStorageService db;
	
	@Inject
	private EventBus bus;
	
	@Inject
	private OrganizarTUpdateService updateService;
	
	@Inject
	private CalendarDataLoader loader;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(db.contain(user.getUserName() + LAST_UPDATE_KEY)){
			deleteOldTasks();
		}
		db.put(user.getUserName() + LAST_UPDATE_KEY, new Date().getTime());
		updateService.findUpdate();
		String dateKey = DateFormat.format("yyyy.MMMM.dd", date).toString();
		if(!db.contain(user.getUserName() + ORGANIZAR_T_PACKAGE_KEY + dateKey)){
			showEmptyPanel();
		}
	}
	
	private void deleteOldTasks(){
		long date = db.get(user.getUserName() + LAST_UPDATE_KEY, Long.class);
		Calendar calendar = (Calendar) Calendar.getInstance().clone();
		calendar.setTime(new Date(date));
		loader.deleteTask(calendar, (Calendar)Calendar.getInstance().clone());
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
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
