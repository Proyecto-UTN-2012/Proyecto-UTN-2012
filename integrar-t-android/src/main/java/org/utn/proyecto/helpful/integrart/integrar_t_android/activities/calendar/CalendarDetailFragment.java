package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.utn.proyecto.helpful.integrart.integrar_t_android.R;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar.EmptyMinuteView.OnSelectMinuteListener;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar.Task.TaskData;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar.TaskView.OnDeleteTaskListener;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar.TaskView.OnInitDragTaskListener;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar.TaskView.OnMoveTaskListener;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar.TaskView.OnRepeatTaskListener;
import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.User;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.DataStorageService;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.TimePicker;
import android.widget.ZoomControls;

public class CalendarDetailFragment extends Fragment implements OnSelectMinuteListener, OnDeleteTaskListener, OnInitDragTaskListener, OnMoveTaskListener, OnRepeatTaskListener{
	private Calendar date;
	private ZoomControls zoom;
	private ZoomHelper zoomHelper;
	private final DataStorageService db;
	private final User user;
	private final CalendarDataLoader loader;
	private HorizontalScrollView grid;
	private ListView hoursView;
	private HoursAdapter adapter;
	
//	private boolean blockScroll;
	
	private List<Task> tasks = new ArrayList<Task>();
	
	private List<TaskType> taskTypes = new ArrayList<TaskType>();
	
	private List<View> taskViews;
	
	private Task edittingTask;
	
	public CalendarDetailFragment(User user, DataStorageService db, CalendarDataLoader loader){
		this.db = db;
		this.user = user;
		this.loader = loader;
		taskTypes = loader.getTaskTypes();
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
		adapter = new HoursAdapter(getActivity(), this, this, this, this, this);
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
	
//	private void editTask(int hour, int minute){
//		int currentHour = edittingTask.getHour();
//		int currentMinute = edittingTask.getMinute();
//		int size = (hour - currentHour)*60 + minute - currentMinute + 5;
//		edittingTask.setSize(size);
//		updateCalendar();
//	}
	
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
		this.tasks.clear();
		Set<Task> set = new HashSet<Task>();
		set.addAll(loader.loadUnrepeatableTaskFromDay(date));
		set.addAll(loader.loadRepeatableTaskFromDay(date));
		this.tasks.addAll(set);
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
	public void onRepeatTask(final Task task) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		String[] daysOfWeek = new String[]{
				getString(R.string.sunday),
				getString(R.string.monday),
				getString(R.string.tuesday),
				getString(R.string.wednesday),
				getString(R.string.thursday),
				getString(R.string.friday),
				getString(R.string.saturday)};
		boolean[] checkeds = new boolean[7];
		for(int i=0;i<7;i++){
			checkeds[i] = task.isRepeatAtDay(i+1);
		}
		builder.setTitle(R.string.dayOfWeekTitle);
		builder.setMultiChoiceItems(daysOfWeek, checkeds, new DialogInterface.OnMultiChoiceClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				if(isChecked){
					task.addRepeatDay(which+1);
				}
				else{
					task.removeRepeatDay(which+1);
				}
			}
		});
		builder.create().show();
	}
	
	@Override
	public void onDeleteTask(Task task) {
		if(task.isRepeatable()){
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle(R.string.deleteTaskTitle);
			builder.setMessage(R.string.deleteTaskMessage);
			builder.create().show();
		}
		else{
			tasks.remove(task);
			updateCalendar();			
		}
	}
	
	@Override
	public void onMoveTask(final Task task){
		TimePickerDialog dialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {			
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				tasks.remove(task);
				minute/=5;
				Calendar newCalendar = task.getData().buildCalendar();
				newCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
				newCalendar.set(Calendar.MINUTE, minute*5);
				Task newTask = new Task(task.getType(), newCalendar, task.getSize());
				tasks.add(newTask);
				updateCalendar();					
			}
		}, task.getHour(), task.getMinute(), false);
		dialog.setTitle(R.string.selectTaskInit);
		dialog.show();
	}
	
	@Override
	public void onInitDragTask(Task task) {
		//this.blockScroll = true;
		edittingTask = task;
		Dialog dialog = new MinutesDialog(getActivity(), task);
		dialog.setTitle(R.string.selectTaskSize);
		dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				edittingTask.setSize(((MinutesDialog)dialog).getValue());
				updateCalendar();
			}
		});
		dialog.show();
	}
	
	@Override
	public void onPause(){
		super.onPause();
		loader.saveUnrepeatableTasks(date, tasks);
		loader.saveRepeatableTasks(tasks);
	}

	private class HoursAdapter extends BaseAdapter{
		private Context context;
		private CalendarHourView[] views = new CalendarHourView[24];
		private OnSelectMinuteListener onSelectMinuteListener;
		private OnDeleteTaskListener onDeleteTaskListener;
		private OnInitDragTaskListener onInitDragTaskListener;
		private OnMoveTaskListener onMoveTaskListener;
		private OnRepeatTaskListener onRepeatTaskListener;
		public HoursAdapter(Context context, 
				OnSelectMinuteListener onSelectMinuteListener, 
				OnDeleteTaskListener onDeleteTaskListener, 
				OnInitDragTaskListener onInitDragTaskListener,
				OnMoveTaskListener onMoveTaskListener,
				OnRepeatTaskListener onRepeatTaskListener){
			this.context = context;
			this.onSelectMinuteListener = onSelectMinuteListener;
			this.onDeleteTaskListener = onDeleteTaskListener;
			this.onInitDragTaskListener = onInitDragTaskListener;
			this.onMoveTaskListener = onMoveTaskListener;
			this.onRepeatTaskListener = onRepeatTaskListener;
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
				view.setMoveTaskListener(onMoveTaskListener);
				view.setRepeatTaskListener(onRepeatTaskListener);
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
		private final NumberPicker view;
		public MinutesDialog(Context context, Task task) {
			super(context);
			view = new NumberPicker(context);
			String[] values = new String[287];
			for(int i=1;i<288;i++){
				values[i-1] = ""+i*5;
			}
			view.setDisplayedValues(values);
			view.setMinValue(0);
			view.setMaxValue(286);
			view.setValue(task.getSize()/5 -1);
			view.setWrapSelectorWheel(false);
			this.setContentView(view);
		}
		
		public int getValue(){
			return (view.getValue() + 1)*5;
		}
		
	}
}
