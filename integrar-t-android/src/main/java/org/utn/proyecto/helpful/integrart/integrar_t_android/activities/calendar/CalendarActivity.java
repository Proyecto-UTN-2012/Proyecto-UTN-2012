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
import org.utn.proyecto.helpful.integrart.integrar_t_android.utils.CalendarView.OnSelectDateListener;

import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.ContentView;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.inject.Inject;

@ContentView(R.layout.calendar_activity)
public class CalendarActivity extends RoboFragmentActivity implements OnSelectDateListener, EventListener<Boolean>{
	public final static String SHOW_ZOOM_KEY = ".calendar.zoom";
	
    private boolean mTwoPane;
    private CalendarFragment calendarFragment;
    private CalendarDetailFragment detailFragment;
    
    @Inject
    private DataStorageService db;
    
    @Inject
    private User user;
    
    @Inject
    private EventBus bus;
    
    @Inject
    private FileSystemService fs;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!db.contain(user.getUserName() + SHOW_ZOOM_KEY)){
			db.put(user.getUserName() + SHOW_ZOOM_KEY, false);
		}
		calendarFragment = ((CalendarFragment) getSupportFragmentManager()
				.findFragmentById(R.id.calendar));
		calendarFragment.setOnSelectDateListener(this);

		if (findViewById(R.id.calendar_detail_container) != null) {
            mTwoPane = true;
            bus.addEventListener(ShowZoomEvent.class, this);
            //Bundle arguments = new Bundle();
            //arguments.putString(ItemDetailFragment.ARG_ITEM_ID, id);
            detailFragment = new CalendarDetailFragment(user, db, fs);
            //fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.calendar_detail_container, detailFragment)
                    .commit();
        }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		super.onCreateOptionsMenu(menu);
		if(mTwoPane){
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.calendar_detail_menu, menu);
			//View view = menu.findItem(R.id.pictogramLevel).getActionView();
			return true;			
		}
		return false;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		if(mTwoPane){
			if(item.getItemId() == R.id.calendarZoom){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("zoom", db.get(user.getUserName() + SHOW_ZOOM_KEY, Boolean.class));
				ZoomMenuProvider provider = new ZoomMenuProvider(this, bus, map);
				return provider.execute();
			}
		}
		return false;
	}
	
	@Override
	public void onDestroy(){
		bus.removeEventListener(ShowZoomEvent.class, this);
		super.onDestroy();
	}

	@Override
	public void onSelectDate(Calendar date) {
		if(mTwoPane){
			detailFragment.setDate(date);
		}
		else{
			Intent detailIntent = new Intent(this, CalendarDetailActivity.class);
            
			detailIntent.putExtra(CalendarDetailActivity.DATE, date);
            startActivity(detailIntent);
		}
	}

	@Override
	public void onEvent(Event<Boolean> event) {
		detailFragment.setZoomVisible(event.getData());
	}
}
