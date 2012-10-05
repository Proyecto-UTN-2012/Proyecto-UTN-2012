package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;
import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.User;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.EventBus;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.DataStorageService;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.SparseIntArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.inject.Inject;

@ContentView(R.layout.organizar_t)
public class OrganizarTListActivity extends RoboActivity{
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
	
	@InjectView(R.id.list)
	private ListView list;
	
	private List<Task> tasks;
	
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
		tasks = loader.loadUnrepeatableTaskFromDay(date);
		TaskListAdapter adapter = new TaskListAdapter(this, tasks);
		list.setAdapter(adapter);
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
	
	private class TaskListAdapter extends BaseAdapter{
		private final Context context;
		private final List<Task> tasks;
		private final View[] views;
		
		private final Typeface font;
		public TaskListAdapter(Context context, List<Task> tasks){
			this.context = context;
			this.tasks = tasks;
			this.views = new View[tasks.size()];
			this.font = Typeface.createFromAsset(getAssets(), "fonts/EraserRegular.ttf");
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return views.length;
		}

		@Override
		public Object getItem(int position) {
			return tasks.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(views[position] == null){
				Task task = tasks.get(position);
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				ViewGroup view = (ViewGroup) inflater.inflate(R.layout.task_row, null);
				TextView timeText = (TextView) view.findViewById(R.id.timeText);
				timeText.setTypeface(font);
				timeText.setText(task.getTimeString());
				
				ImageView image = (ImageView)view.findViewById(R.id.image);
				image.setImageDrawable(task.getSmallImage());
				
				TextView nameText = (TextView) view.findViewById(R.id.nameText);
				nameText.setTypeface(font);
				nameText.setText(task.getName());
				
				views[position] = view;
			}
			return views[position];
		}
		
	}
}
