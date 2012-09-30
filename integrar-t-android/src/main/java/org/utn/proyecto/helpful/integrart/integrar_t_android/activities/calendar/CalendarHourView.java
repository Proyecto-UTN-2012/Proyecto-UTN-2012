package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar.EmptyMinuteView.OnSelectMinuteListener;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar.TaskView.OnDeleteTaskListener;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar.TaskView.OnInitDragTaskListener;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar.TaskView.OnMoveTaskListener;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar.TaskView.OnRepeatTaskListener;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public class CalendarHourView extends FrameLayout {
	private ViewGroup title;
	private ViewGroup content;
	private int hour;
	private float itemSize;
	private Drawable titleBackground;
	private int titleColor;
	private float titleSize;
	
	private int colorIndex = 0;
	
	private Map<Task, TaskBackground> colorMap = new HashMap<Task, TaskBackground>();
	
	private List<TaskBackground> colors = Arrays.asList(
			TaskBackground.BLUE, 
			TaskBackground.GREEN, 
			TaskBackground.ORANGE,
			TaskBackground.PINK,
			TaskBackground.YELLOW);
	
	private OnSelectMinuteListener onSelectMinuteListener;
	private OnDeleteTaskListener onDeleteTaskListener;
	private OnInitDragTaskListener onInitDragTaskListener;
	private OnMoveTaskListener onMoveTaskListener;
	private OnRepeatTaskListener onRepeatTaskListener;

	public CalendarHourView(Context context) {
		super(context);
		//init();
	}
	public CalendarHourView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setAttributes(attrs);
		//init();
	}
	public CalendarHourView(Context context, AttributeSet attrs, int hour) {
		super(context, attrs);
		setAttributes(attrs);
		this.hour = hour;
		//init();
	}
	
	private void setAttributes(AttributeSet attrs){
		TypedArray ta = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.CalendarHourIntegrarT,0,0);
		hour = ta.getInt(R.styleable.CalendarHourIntegrarT_hour,0);
		itemSize = ta.getDimension(R.styleable.CalendarHourIntegrarT_itemSize,50);
		titleBackground = ta.getDrawable(R.styleable.CalendarHourIntegrarT_titleBackground);
		titleColor = ta.getColor(R.styleable.CalendarHourIntegrarT_titleColor, 0xff000000);
		titleSize = ta.getDimension(R.styleable.CalendarHourIntegrarT_titleSize, 14);
	}
	
	public void init(){
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.calendar_hour, null);
		content = (ViewGroup) view.findViewById(R.id.content);
		content.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN){
					Log.i("CalendarHour", "DOWN");
					return true;
				}
				if(event.getAction()==MotionEvent.ACTION_MOVE){
					Log.i("CalendarHour", "MOVE");
					return true;
				}
				Log.i("CalendarHour", "Other Action " + event.getAction());
				
				return false;
			}
		});
		title = (ViewGroup) view.findViewById(R.id.title);
		setTitle();
		for(int i=0;i<60;i+=5){
			addEmptyView(hour, i);
		}
		this.addView(view);
	}
	
	public void setOnSelectMinuteListener(OnSelectMinuteListener listener){
		this.onSelectMinuteListener = listener;
	}
	
	public void setOndeleteTaskListener(OnDeleteTaskListener listener){
		this.onDeleteTaskListener = listener;
	}

	public void setInitDragTaskListener(OnInitDragTaskListener listener){
		this.onInitDragTaskListener = listener;
	}

	public void setMoveTaskListener(OnMoveTaskListener listener){
		this.onMoveTaskListener = listener;
	}
	
	public void setRepeatTaskListener(OnRepeatTaskListener listener){
		this.onRepeatTaskListener = listener;
	}
	
	private void setTitle(){
		title.removeAllViews();
		for(int min=0;min<60;min+=5){
			TextView minTitle = new TextView(getContext());
			String text = (hour<10)? "0" + hour : ""+hour;
			text+=":";
			text+= (min<10)? "0"+min : ""+min;
			minTitle.setText(text);
			minTitle.setLayoutParams(new LayoutParams((int)itemSize, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
			minTitle.setBackgroundDrawable(titleBackground);
			minTitle.setTextColor(titleColor);
			minTitle.setTextSize(titleSize);
			minTitle.setGravity(Gravity.CENTER);
			title.addView(minTitle);			
		}
	}
	
	public void setHour(int hour){
		this.hour = hour;
		this.colorIndex = hour%5;
	}
	
	public List<View> update(List<Task> tasks){
		content.removeAllViews();
		List<View> views = new ArrayList<View>();
		Collections.sort(tasks);
		int currentMinute = 0;
		do{
			Task currentTask = null;
			for(Task task : tasks){
				if(task.getMinute()==currentMinute || task.getHour() != hour){
					currentTask = task;
					break;
				}
			}
			if(currentTask!=null){
				views.add(addTaskView(currentTask));
				if(currentTask.getHour()!=hour){
					currentMinute+=(currentTask.getSize() + currentTask.getMinute() - (hour - currentTask.getHour())*60);
					tasks.remove(currentTask);
				}
				else currentMinute+=currentTask.getSize();
			}
			else{
				views.add(addEmptyView(hour, currentMinute));
				currentMinute+=5;
			}
		}while(currentMinute<60);
		return views;
	}
	
	private View addTaskView(Task task){
		TaskBackground background = colors.get((++colorIndex)%5);
		
		if(colorMap.containsKey(task)) background = colorMap.get(task);
		else colorMap.put(task, background);
			
		TaskView view = new TaskView(getContext(), content.getLayoutParams().height, hour, task, background);
		view.setOnDeleteListener(onDeleteTaskListener);
		view.setOnInitDragTaskListener(onInitDragTaskListener);
		view.setOnMoveListener(onMoveTaskListener);
		view.setOnRepeatTaskListener(onRepeatTaskListener);
		content.addView(view);
		return view;
	}
	
	private View addEmptyView(int hour, int minute){
		EmptyMinuteView emptyView = new EmptyMinuteView(getContext(), hour, minute);
		emptyView.setLayoutParams(new ViewGroup.LayoutParams(content.getLayoutParams().height, content.getLayoutParams().height));
		emptyView.setOnSelectMinuteListener(onSelectMinuteListener);
		content.addView(emptyView);
		return emptyView;
	}
}
