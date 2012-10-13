package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar;

import java.util.Calendar;
import java.util.List;

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
		nameText.setTypeface(font);
		
		listButton.setTypeface(font);
		listButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				bus.dispatch(new LaunchOrganizarEvent(v.getContext()));
				finish();
			}
		});
	}
	
	@Override
	protected void onResume(){
		task = loader.loadCurrentTask();
		nameText.setText(Html.fromHtml("<u>"+task.getName()+"</u>"));
		image.setImageDrawable(task.getLargeImage());
		update();
		super.onResume();
	}
	
	private void update(){
		settingProgress();
		settingActionButton();
		settingNextButton();
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
	
	private void settingNextButton(){
		nextButton.setTypeface(font);
		if(task.isCompleted()){
			nextButton.setVisibility(View.VISIBLE);
			nextButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					bus.dispatch(new LaunchTaskTransitionEvent(v.getContext()));
				}
			});			
		}
		else{
			nextButton.setVisibility(View.INVISIBLE);
		}
	}
	
	private void settingActionButton(){
		actionButton.setTypeface(font);
		actionButton.setVisibility(View.VISIBLE);
		if(task.getState()==TaskState.DEFAULT || task.getState()==TaskState.READY){
			actionButton.setText(R.string.activateTask);
			actionButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					task.active();
					updateTask();
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
					updateTask();
					update();
				}
			});
		}
		else{
			actionButton.setVisibility(View.INVISIBLE);
		}
	}
	
	private void updateTask(){
		Calendar date = (Calendar)Calendar.getInstance().clone();
		List<Task> tasks = loader.loadTodayTasks(date);
		int index = tasks.indexOf(task);
		tasks.remove(task);
		tasks.add(index, task);
		loader.saveUnrepeatableTasks(date, tasks);
	}
}
