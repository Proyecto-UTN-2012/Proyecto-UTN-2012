package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;
import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.User;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.EventBus;
import org.utn.proyecto.helpful.integrart.integrar_t_android.metrics.ActivityMetric;
import org.utn.proyecto.helpful.integrart.integrar_t_android.metrics.Metric;
import org.utn.proyecto.helpful.integrart.integrar_t_android.metrics.MetricsService;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.DataStorageService;
import org.utn.proyecto.helpful.integrart.integrar_t_android.utils.GiftCount;
import org.utn.proyecto.helpful.integrart.integrar_t_android.utils.GiftPopup;

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
	private static final String TASK_DURATION_METRIC = "duracion";
	private static final String TASK_REAL_DURATION_METRIC = "duracion real";
	private static final String TASK_END_DIFERENCE_METRIC = "diferencia";	
	private static final String TASK_INIT_DIFERENCE_METRIC = "diferencia de inicio";	
	
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
	
	@Inject
	private MetricsService metrics;
	
	@Inject
	private User user;
	
	@Inject
	private DataStorageService db;
	
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
					boolean res = task.active();
					task.setInitTime(new Date().getTime());
					long estimatedInit = task.getData().buildCalendar().getTime().getTime();
					metrics.sendMetric(new Metric(user, ActivityMetric.ORGANIZART, TASK_INIT_DIFERENCE_METRIC, task.getName(), (int)(task.getInitTime() - estimatedInit)));
					updateTask();
					update();
					showGifts(res);
				}
			});
		}
		else if(task.getState()==TaskState.ACTIVED || task.getState()==TaskState.EXCEEDED){
			actionButton.setText(R.string.finalizeTask);
			actionButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					boolean res = task.terminate();
					sendFinalizeMetrics();
					updateTask();
					update();
					showGifts(res);
				}
			});
		}
		else{
			actionButton.setVisibility(View.INVISIBLE);
		}
	}
	

	private void showGifts(boolean flag) {
		user.addGifts(flag ? 3 : 1);
		db.put("currentUser", user);
		new GiftPopup(this, user.getGifts(), flag ? GiftCount.TREE : GiftCount.ONE).show();
	}
	
	private void sendFinalizeMetrics() {
		int duration = (int)(new Date().getTime() - task.getInitTime());
		int estimateDuration = task.getSize()*60*1000;
		Metric[] metrics = new Metric[]{
				new Metric(user, ActivityMetric.ORGANIZART, TASK_DURATION_METRIC, task.getName(), estimateDuration),
				new Metric(user, ActivityMetric.ORGANIZART, TASK_END_DIFERENCE_METRIC, task.getName(),duration - estimateDuration),
				new Metric(user, ActivityMetric.ORGANIZART, TASK_REAL_DURATION_METRIC, task.getName(),duration)};
		this.metrics.sendMetrics(metrics);
		
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
