package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;
import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.User;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.Event;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.EventBus;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.EventListener;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.DataStorageService;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.FileSystemService;

import com.google.inject.Inject;

import roboguice.activity.RoboFragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class CalendarDetailActivity extends RoboFragmentActivity implements EventListener<Boolean>{
		public static final String DATE = "date";
		
		public final static String SHOW_ZOOM_KEY = ".calendar.zoom";
		
		@Inject
		private User user;
		
		@Inject
		private DataStorageService db;
		
		@Inject 
		private EventBus bus;
		
		@Inject
		private FileSystemService fs;
		
		private CalendarDetailFragment fragment;
		
		@Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	
	        getActionBar().setDisplayHomeAsUpEnabled(true);
	        bus.addEventListener(ShowZoomEvent.class, this);
	        if (savedInstanceState == null) {
	            Bundle arguments = getIntent().getExtras();
	            fragment = new CalendarDetailFragment(user, db, fs);
	            if(arguments.containsKey(CalendarDetailActivity.DATE))
	            	fragment.setDate((Calendar)arguments.get(DATE));
	            //fragment.setArguments(arguments);
	            getSupportFragmentManager().beginTransaction()
	                    .add(android.R.id.content, fragment)
	                    .commit();
	          
	        }
		}
		
		@Override
		public boolean onCreateOptionsMenu(Menu menu){
			super.onCreateOptionsMenu(menu);
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.calendar_detail_menu, menu);
			//View view = menu.findItem(R.id.pictogramLevel).getActionView();
			return true;			
		}
		
		@Override
		public boolean onOptionsItemSelected(MenuItem item){
			if(item.getItemId() == R.id.calendarZoom){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("zoom", db.get(user.getUserName() + SHOW_ZOOM_KEY, Boolean.class));
				ZoomMenuProvider provider = new ZoomMenuProvider(this, bus, map);
				return provider.execute();
			}
			return false;
		}
		
		@Override
		public void onDestroy(){
			bus.removeEventListener(ShowZoomEvent.class, this);
			super.onDestroy();
		}

		@Override
		public void onEvent(Event<Boolean> event) {
			fragment.setZoomVisible(event.getData());
			db.put(user.getUserName() + SHOW_ZOOM_KEY, event.getData());
		}
}
