package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.utn.proyecto.helpful.integrart.integrar_t_android.R;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar.EmptyMinuteView.OnSelectMinuteListener;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar.Task.TaskData;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar.TaskView.OnDeleteTaskListener;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar.TaskView.OnInitDragTaskListener;
import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.User;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.DataStorageService;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.FileSystemService;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Toast;
import android.widget.ZoomControls;

public class CalendarDetailFragment extends Fragment implements OnSelectMinuteListener, OnDeleteTaskListener, OnInitDragTaskListener{
	private static final String ACTIVITY_NAME = "calendarActivity";
	private Calendar date;
	private ZoomControls zoom;
	private ZoomHelper zoomHelper;
	private final DataStorageService db;
	private final User user;
	private HorizontalScrollView grid;
	private ListView hoursView;
	private HoursAdapter adapter;
	
	private boolean blockScroll;
	
	private List<Task> tasks = new ArrayList<Task>();
	
	private List<TaskType> taskTypes = new ArrayList<TaskType>();
	
	private List<View> taskViews;
	
	private Task edittingTask;
	
	public CalendarDetailFragment(User user, DataStorageService db, FileSystemService fileService){
		this.db = db;
		this.user = user;
		if(!(db.contain(OrganizarTUpdateService.getTaskTypesKey(user)))){
			Toast.makeText(getActivity(), getString(R.string.emptyTaskTypes), Toast.LENGTH_LONG);
			return;
		}
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
		grid = (HorizontalScrollView) view.findViewById(R.id.grid);
//		grid.setOnTouchListener(new View.OnTouchListener() {
//			
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				if(blockScroll){
//					if(event.getAction()==MotionEvent.ACTION_MOVE){
//						Log.i("CalendarDetailFragment", "MOVE: " + v.getClass().getSimpleName());
//						View currentView = findViewByPoint((int)event.getRawX(), (int)event.getRawY());
//						if(currentView instanceof EmptyMinuteView){
//							EmptyMinuteView view = (EmptyMinuteView)currentView;
//							editTask(view.getHour(), view.getMinute());
//							Log.i("CalendarDetailFragment", "VISITING: " + view.getHour() + ":" + view.getMinute());
//						}
//					}
//					if(event.getAction()==MotionEvent.ACTION_CANCEL || event.getAction()==MotionEvent.ACTION_UP)
//						blockScroll = false;
//					return blockScroll;
//				}
//				return false;
//			}
//		});
		hoursView = (ListView) view.findViewById(R.id.list);
//		hoursView.setOnTouchListener(new View.OnTouchListener() {
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				return blockScroll && event.getAction() == MotionEvent.ACTION_MOVE;
//			}
//		});
		
		zoomHelper = new ZoomHelper(grid, zoom);
		adapter = new HoursAdapter(getActivity(), this, this, this);
		hoursView.setAdapter(adapter);
		setZoomVisible(showZoom);
		updateCalendar();
		return view;
	}
	
//	private View findViewByPoint(int x, int y){
//		for(View view : taskViews){
//			int locations[] = new int[2];
//			view.getLocationOnScreen(locations);
//			int vx= locations[0];
//			int vy= locations[1];
//			int vx2 = vx + view.getWidth();
//			int vy2 = vy + view.getHeight();
//			
//			if(x >= vx && x <= vx2
//					&& y >= vy && y <= vy2)
//				return view;
//		}
//		return null;
//	}
	
	private void editTask(int hour, int minute){
		int currentHour = edittingTask.getHour();
		int currentMinute = edittingTask.getMinute();
		int size = (hour - currentHour)*60 + minute - currentMinute + 5;
		edittingTask.setSize(size);
		updateCalendar();
	}
	
	private void updateCalendar(){
		if(adapter!=null)
			taskViews = adapter.update(tasks);
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
			TaskData[] taskArray = db.get(user.getUserName() + OrganizarActivity.ORGANIZAR_T_PACKAGE_KEY + dateKey, TaskData[].class);
			for(TaskData task : taskArray){
				for(TaskType type : taskTypes){
					if(type.getName().equals(task.getType())){
						tasks.add(new Task(type, task.buildCalendar(), task.getSize()));
						break;
					}
				}
			}
		}
		updateCalendar();
	}
	
	@Override
	public void onSelectMinute(final int hour, final int minute) {
		ListView tasksView = new ListView(getActivity());
		tasksView.setAdapter(new SelectTaskAdapter(getActivity(), taskTypes));
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setCancelable(true);
		builder.setTitle(R.string.selectTestTitle);
		builder.setView(tasksView);
		final Dialog dialog = builder.create();
		tasksView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long id) {
				TaskType task = (TaskType) adapter.getItemAtPosition(position);
				addnewTask(task, hour, minute);
				dialog.dismiss();
			}
			
		});
		
		dialog.show();
	}
	
	private void addnewTask(TaskType type, int hour, int minute){
		Calendar date = (Calendar) this.date.clone();
		date.set(Calendar.HOUR_OF_DAY, hour);
		date.set(Calendar.MINUTE, minute);
		Task task = new Task(type, date);
		tasks.add(task);
		updateCalendar();
	}
	
	@Override
	public void onDeleteTask(Task task) {
		tasks.remove(task);
		updateCalendar();
	}
	
	@Override
	public void onInitDragTask(Task task) {
		//this.blockScroll = true;
		edittingTask = task;
		Dialog dialog = new MinutesDialog(getActivity());
		dialog.setTitle("NUMEROS");
		dialog.show();
	}
	
	@Override
	public void onPause(){
		super.onPause();
		String dateKey = DateFormat.format("yyyy.MMMM.dd", date).toString();
		TaskData[] tasks = new TaskData[this.tasks.size()];
		for(int i=0;i<tasks.length;i++){
			tasks[i] = this.tasks.get(i).getData();
		}
		db.put(user.getUserName() + OrganizarActivity.ORGANIZAR_T_PACKAGE_KEY + dateKey, tasks);
	}

	private class HoursAdapter extends BaseAdapter{
		private Context context;
		private CalendarHourView[] views = new CalendarHourView[24];
		private OnSelectMinuteListener onSelectMinuteListener;
		private OnDeleteTaskListener onDeleteTaskListener;
		private OnInitDragTaskListener onInitDragTaskListener;
		public HoursAdapter(Context context, OnSelectMinuteListener onSelectMinuteListener, OnDeleteTaskListener onDeleteTaskListener, OnInitDragTaskListener onInitDragTaskListener){
			this.context = context;
			this.onSelectMinuteListener = onSelectMinuteListener;
			this.onDeleteTaskListener = onDeleteTaskListener;
			this.onInitDragTaskListener = onInitDragTaskListener;
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
		
		public List<View> update(List<Task> tasks){
			List<View> taskViews = new ArrayList<View>();
			for(int i=0;i<24;i++){
				final int j = i;
				List<Task> filteredList = new ArrayList<Task>(tasks);
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
				taskViews.addAll(views[i].update(filteredList));
			}
			return taskViews;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {			
			if(views[position]==null){
				LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				CalendarHourView view = (CalendarHourView) inflater.inflate(R.layout.calendar_hour_item, null);
				view.setHour(position);
				view.setOnSelectMinuteListener(onSelectMinuteListener);
				view.setOndeleteTaskListener(onDeleteTaskListener);
				view.setInitDragTaskListener(onInitDragTaskListener);
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
	
	private class MinutesDialog extends Dialog{

		public MinutesDialog(Context context) {
			super(context);
			NumberPicker view = new NumberPicker(context);
			String[] values = new String[287];
			for(int i=1;i<288;i++){
				values[i-1] = ""+i*5;
			}
			view.setDisplayedValues(values);
			this.setContentView(view);
		}
		
	}
}
