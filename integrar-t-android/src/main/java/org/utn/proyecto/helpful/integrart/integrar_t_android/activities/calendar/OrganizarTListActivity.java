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
		
		public TaskListAdapter(Context context, List<Task> tasks){
			this.context = context;
			this.tasks = tasks;
			this.views = new View[tasks.size()];
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
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				ViewGroup view = (ViewGroup) inflater.inflate(R.layout.task_row, null);
				TextView timeText = (TextView) view.findViewById(R.id.timeText);
				timeText.setText(tasks.get(position).getTimeString());
				
				ImageView image = (ImageView)view.findViewById(R.id.image);
				image.setImageDrawable(tasks.get(position).getSmallImage());
				
				views[position] = view;
//				RelativeLayout.LayoutParams params;
//				RelativeLayout layout = new RelativeLayout(context);
//				layout.setPadding(2, 10, 2, 10);
//				
//				TextView timeText = new TextView(context);
//				timeText.setId(1000);
//				params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
//				//params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//				//params.addRule(RelativeLayout.CENTER_VERTICAL);
//				//params.setMargins(0, 0, 2, 0);
//				timeText.setGravity(Gravity.CENTER_VERTICAL);
//				timeText.setText(tasks.get(position).getTimeString());
//				timeText.setTextColor(0xff00ff00);
//				timeText.setBackgroundColor(0xffffffff);
//				layout.addView(timeText, params);
//				
//				ImageView divider = new ImageView(context);
//				divider.setId(1001);
//				divider.setImageResource(R.drawable.line_divider);
//				params = new RelativeLayout.LayoutParams(2, RelativeLayout.LayoutParams.MATCH_PARENT);
//				params.addRule(RelativeLayout.RIGHT_OF, timeText.getId());
//				params.setMargins(0, 0, 2, 0);
//				layout.addView(divider, params);
//				
//				ImageView image = new ImageView(context);
//				params = new RelativeLayout.LayoutParams(120, 120);
//				params.addRule(RelativeLayout.RIGHT_OF, divider.getId());
//				image.setBackgroundResource(R.drawable.grey_image_background);
//				image.setImageDrawable(tasks.get(position).getSmallImage());
//				layout.addView(image, params);
				
//				views[position] = layout;
			}
			return views[position];
		}
		
	}
}
