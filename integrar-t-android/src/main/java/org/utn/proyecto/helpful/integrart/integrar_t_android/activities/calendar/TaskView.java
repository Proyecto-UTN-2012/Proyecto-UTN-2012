package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

@SuppressLint({ "ViewConstructor", "ViewConstructor" })
public class TaskView extends RelativeLayout {
	private final Task task;
	
	private OnDeleteTaskListener deleteListener;
	private OnInitDragTaskListener dragListener;
	
	private ImageView dragImage;
	private ImageView deleteImage;
	
	private int hour;
	
	public TaskView(Context context, int minSize, int hour, Task currentTask, TaskBackground background) {
		super(context);
		this.task = currentTask;
		this.hour = hour;
		this.setBackgroundDrawable(context.getResources().getDrawable(background.getBackground()));
		this.setLayoutParams(new ViewGroup.LayoutParams(minSize*getTaskSize()/5, minSize));
		this.setPadding(5, 5, 5, 5);
		RelativeLayout.LayoutParams params;
		
		ImageView image = new ImageView(context);
		image.setId(1000);
		params = new RelativeLayout.LayoutParams(minSize - 10, minSize -10); 
		params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		image.setImageDrawable(task.getPictogram());
		this.addView(image, params);
		
		if(task.getSize()>10){
			TextView text = new TextView(context);
			params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.RIGHT_OF, image.getId());
			text.setText(task.getName());
			text.setTextAppearance(context, android.R.style.TextAppearance_DeviceDefault_Small);
			text.setTextColor(0xff333333);
			text.setGravity(Gravity.CENTER);
			this.addView(text, params);
		}
		
		dragImage = new ImageView(context);
		dragImage.setId(1001);
		params = new RelativeLayout.LayoutParams(minSize/2 - 15, minSize -10);
		params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		dragImage.setImageResource(R.drawable.drag);
		dragImage.setVisibility(View.INVISIBLE);
		dragImage.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
//				return detector.onTouchEvent(event);
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					Log.i("TaskView MotionEvent", "DOWN");
					if(dragListener!=null){
						dragListener.onInitDragTask(task);
						return true;
					}					
				}
				return false;
			}
		});
		this.addView(dragImage, params);

		deleteImage = new ImageView(context);
		params = new RelativeLayout.LayoutParams(minSize/2 - 8, minSize/2 -8);
		params.setMargins(0, 0, 5, 0);
		params.addRule(RelativeLayout.LEFT_OF, dragImage.getId());
		params.addRule(RelativeLayout.ALIGN_TOP);
		deleteImage.setImageResource(R.drawable.trash_can_medium);
		deleteImage.setVisibility(View.INVISIBLE);
		deleteImage.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(deleteListener!=null){
					deleteListener.onDeleteTask(task);
					return true;
				}
				return false;
			}
		});
		this.addView(deleteImage, params);
		
		this.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!showControls()) return;
				dragImage.setVisibility(View.VISIBLE);
				deleteImage.setVisibility(View.VISIBLE);
			}
		});
	}
	
	private boolean showControls(){
		return hour == task.getHour() + (task.getMinute() + task.getSize() - 1)/60;
	}
	
	public int getTaskSize(){
		if(hour == task.getHour()){
			return (task.getSize() > (60 - task.getMinute()))
					? 60 - task.getMinute()
					: task.getSize();
		}
		return ((task.getSize() - (hour - task.getHour())*60) > (60 - task.getMinute()))
				? 60
				: task.getSize() + task.getMinute() - (hour - task.getHour())*60;
	}
	
	public void setOnDeleteListener(OnDeleteTaskListener listener){
		this.deleteListener = listener;
	}
	
	public void setOnInitDragTaskListener(OnInitDragTaskListener listener){
		this.dragListener = listener;
	}
	
	public interface OnDeleteTaskListener{
		public void onDeleteTask(Task task);
	}
	
	public interface OnInitDragTaskListener{
		public void onInitDragTask(Task task);
	}
}