package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.utn.proyecto.helpful.integrart.integrar_t_android.R;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar.EmptyMinuteView.OnSelectMinuteListener;
import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.User;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.DataStorageService;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.FileSystemService;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ZoomControls;

@SuppressLint({ "ValidFragment", "ValidFragment" })
public class CalendarDetailFragment extends Fragment implements OnSelectMinuteListener{
	private static final String ACTIVITY_NAME = "calendarActivity";
	private Calendar date;
	private ZoomControls zoom;
	private ZoomHelper zoomHelper;
	private final DataStorageService db;
	private final User user;
	private ListView hoursView;
	private HoursAdapter adapter;
	
	private List<Task> tasks = new ArrayList<Task>();
	
	private List<TaskType> taskTypes = new ArrayList<TaskType>();
	
	public CalendarDetailFragment(User user, DataStorageService db, FileSystemService fileService){
		this.db = db;
		this.user = user;
		TaskTypeData[] taskData = db.get(OrganizarTUpdateService.getTaskTypesKey(user), TaskTypeData[].class);
		for(TaskTypeData data : taskData){
			Drawable pictogram = (Drawable) fileService.getResource(ACTIVITY_NAME, data.getName() + "_pictogram.png").getResource();
			Drawable large = (Drawable) fileService.getResource(ACTIVITY_NAME, data.getName() + "_large.png").getResource();
			Drawable small = (Drawable) fileService.getResource(ACTIVITY_NAME, data.getName() + "_small.png").getResource();
			taskTypes.add(new TaskType(data.getName(), pictogram, large, small));
		}
		
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		final ViewGroup view = (ViewGroup) inflater.inflate(R.layout.calendar_detail_fragment, container, false);
		zoom = (ZoomControls)view.findViewById(R.id.zoom);
		boolean showZoom = db.get(user.getUserName() + CalendarActivity.SHOW_ZOOM_KEY, Boolean.class);
		final ViewGroup grid = (ViewGroup) view.findViewById(R.id.grid);
		hoursView = (ListView) view.findViewById(R.id.list);
		zoomHelper = new ZoomHelper(grid, zoom);
		adapter = new HoursAdapter(getActivity(), this);
		hoursView.setAdapter(adapter);
		setZoomVisible(showZoom);
		updateCalendar();
		return view;
	}
	
	private void updateCalendar(){
		if(adapter!=null)
			adapter.update(tasks);
	}
	
	public void setZoomVisible(boolean visible){
		zoomHelper.showControls(visible);
		zoom.setVisibility(visible? View.VISIBLE : View.GONE);
	}
	
	public void setDate(Calendar date){
		this.date = date;
		String dateKey = DateFormat.format("yyyy.MMMM.dd", date).toString();
		if(db.contain(user.getUserName() + OrganizarActivity.ORGANIZAR_T_PACKAGE_KEY + dateKey)){
			tasks.clear();
			Task[] taskArray = db.get(user.getUserName() + OrganizarActivity.ORGANIZAR_T_PACKAGE_KEY + dateKey, Task[].class);
			for(Task task : taskArray){
				tasks.add(task);
			}
		}
		updateCalendar();
	}
	
	@Override
	public void onSelectMinute(int hour, int minute) {
		
		Toast.makeText(getActivity(), "Seleccionó: " + hour + ":" + minute, Toast.LENGTH_LONG).show();
	}
		
	private class HoursAdapter extends BaseAdapter{
		private Context context;
		private CalendarHourView[] views = new CalendarHourView[24];
		private OnSelectMinuteListener onSelectMinuteListener;
		public HoursAdapter(Context context, OnSelectMinuteListener onSelectMinuteListener){
			this.context = context;
			this.onSelectMinuteListener = onSelectMinuteListener;
		}
		@Override
		public int getCount() {
			return 24;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}
		
		public void update(List<Task> tasks){
			for(int i=0;i<24;i++){
				final int j = i;
				List<Task> filteredList = new ArrayList<Task>();
				CollectionUtils.filter(filteredList, new Predicate() {
					@Override
					public boolean evaluate(Object object) {
						Task task = (Task)object;
						return task.inHour(j);
					}
				});
				if(views[i]==null){
					views[i] = (CalendarHourView) getView(i, null, hoursView);
				}
				views[i].update(filteredList);
			}
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {			
			if(views[position]==null){
				LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				CalendarHourView view = (CalendarHourView) inflater.inflate(R.layout.calendar_hour_item, null);
				view.setHour(position);
				view.setOnSelectMinuteListener(onSelectMinuteListener);
				view.init();
				views[position] = view;
			}
			return views[position];
		}
		
	}
	
	private class ZoomHelper{
		private final static float SCALE_FACTOR = .25f;
		
		private final View view;

		private int initialViewWidth;
		private int initialViewHeight;
		private int controlHeight;
		private float zoomLevel = 1f;
		private boolean visible = false;
		private int toAddHeight = 0;
		
		public ZoomHelper(View theView, final ZoomControls controls){
			this.view = theView;
			controlHeight = controls.getHeight();
			controls.setOnZoomInClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(initialViewWidth==0){
						controlHeight = controls.getHeight();
						initialViewWidth = view.getWidth();
						initialViewHeight = view.getHeight();
					}
					changeZoom(SCALE_FACTOR);
				}
			});
			controls.setOnZoomOutClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(initialViewWidth==0){
						initialViewWidth = view.getWidth();
						initialViewHeight = view.getHeight();
					}
					changeZoom(-SCALE_FACTOR);
				}
			});
		}
		
		public void showControls(boolean show){
			if(controlHeight==0) return;
			visible = show;
			toAddHeight = visible ? 0 : controlHeight;
			view.setLayoutParams(new LinearLayout.LayoutParams((int)(initialViewWidth/zoomLevel), (int)((initialViewHeight + toAddHeight)/zoomLevel)));
		}
		
		private void changeZoom(float factor){
			zoomLevel+=factor;
			if(zoomLevel<=0) zoomLevel = 0.25f;
			view.setScaleX(zoomLevel);
			view.setScaleY(zoomLevel);
			view.setPivotX(0f);
			view.setPivotY(0f);
			view.setLayoutParams(new LinearLayout.LayoutParams((int)(initialViewWidth/zoomLevel), (int)(initialViewHeight/zoomLevel)));
		}
	}
}
