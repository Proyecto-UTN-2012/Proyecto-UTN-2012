package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar;

import java.util.Calendar;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.EventBus;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.graphics.Typeface;
import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.inject.Inject;

@ContentView(R.layout.show_task)
public class ShowTaskActivity extends RoboActivity {
	private Typeface font;
	@InjectView(R.id.name)
	private TextView nameText;
	
	@InjectView(R.id.image)
	private ImageView image;
	
	@InjectView(R.id.progress)
	private ImageView progress;
	
	@InjectView(R.id.action)
	private Button actionButton;

	@InjectView(R.id.list)
	private Button listButton;
	
	@InjectView(R.id.next)
	private Button nextButton;
	
	@Inject
	private CalendarDataLoader loader;
	
	@Inject
	private EventBus bus;
	
	private Task task;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		font = Typeface.createFromAsset(getAssets(), "fonts/WC_RoughTrad.ttf");
		task = loader.loadCurrentTask();
		nameText.setTypeface(font);
		nameText.setText(Html.fromHtml("<u>"+task.getName()+"</u>"));
		
		listButton.setTypeface(font);
		nextButton.setTypeface(font);
		
		image.setImageDrawable(task.getLargeImage());
		
		update();
	}
	
	private void update(){
		settingProgress();
		settingActionButton();
	}
	
	private void settingProgress(){
		int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		int minute = Calendar.getInstance().get(Calendar.MINUTE);
		if(task.isFinalized(hour, minute))
			((ClipDrawable)progress.getDrawable()).setLevel(10000);
		if(task.isInitialized(hour, minute) && !task.isFinalized(hour, minute)){
			int size = (task.getSize() - task.getRemainingMinutes(hour, minute))*10000/task.getSize();
			((ClipDrawable)progress.getDrawable()).setLevel(size);
		}
	}
	
	private void settingActionButton(){
		actionButton.setTypeface(font);
		if(task.getState()==TaskState.DEFAULT || task.getState()==TaskState.READY){
			actionButton.setText(R.string.activateTask);
			actionButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					task.active();
					bus.dispatch(new UpdateTaskEvent(v.getContext(), task));
					update();
				}
			});
		}
		else if(task.getState()==TaskState.ACTIVED || task.getState()==TaskState.EXCEEDED){
			actionButton.setText(R.string.finalizeTask);
			actionButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					task.terminate();
					bus.dispatch(new UpdateTaskEvent(v.getContext(), task));
					update();
				}
			});
		}
		else{
			actionButton.setVisibility(View.INVISIBLE);
		}
	}
}
