package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar;

import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.utn.proyecto.helpful.integrart.integrar_t_android.R;
import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.User;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.EventBus;
import org.utn.proyecto.helpful.integrart.integrar_t_android.metrics.ActivityMetric;
import org.utn.proyecto.helpful.integrart.integrar_t_android.metrics.Metric;
import org.utn.proyecto.helpful.integrart.integrar_t_android.metrics.MetricsService;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.DataStorageService;

import roboguice.service.RoboIntentService;
import android.content.Intent;
import android.media.MediaPlayer;

import com.google.inject.Inject;

public class TaskNotificationService extends RoboIntentService {
	public static final String TASK_NOTIFICATION_STARTED = ".taskNotificationsStartedv6";
	
	@Inject
	private DataStorageService db;
	@Inject
	private CalendarDataLoader loader;
	
	@Inject
	private EventBus bus;
	
	@Inject
	private MetricsService metricsService;
	
	private User user;
	private boolean end;
	private boolean running;
	
	private MediaPlayer alarm;
	
	
	public TaskNotificationService(){
		super("TaskNotificationService");
	}
	
	
	public void onCreate(){
		super.onCreate();
		this.user = db.get("currentUser", User.class);
	}
	
	@Override
	public boolean stopService(Intent service){
		this.end = true;
		db.delete(user.getUserName() + TASK_NOTIFICATION_STARTED);
		return super.stopService(service);
	}
	
	@Override
	public void onDestroy(){
		this.end = true;
		db.delete(user.getUserName() + TASK_NOTIFICATION_STARTED);
		super.onDestroy();
	}
	
	private void processTask(Task task){
		loader.saveCurrentTask(task);
		if(alarm==null)	alarm = MediaPlayer.create(this, R.raw.hangout_ringtone);
		alarm.setLooping(false);
		alarm.start();
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				alarm.stop();
				try {
					alarm.prepare();
				} catch (Exception e) {
					throw new RuntimeException(e);
				} 
			}
		}, 10000);
		Intent newIntent = new Intent(this, ShowTaskActivity.class);
		newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(newIntent);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		if(running) return;
		running = true;
		//if(db.contain(user.getUserName() + TASK_NOTIFICATION_STARTED)) return;
		db.put(user.getUserName() + TASK_NOTIFICATION_STARTED, true);
		while(!end){
			Calendar date = Calendar.getInstance();
			List<Task> tasks = loader.loadTodayTasks(date);
			int hour = date.get(Calendar.HOUR_OF_DAY);
			int minutes = date.get(Calendar.MINUTE);
			int seconds = date.get(Calendar.SECOND);
			int delay = (60-seconds)*1000 + (4 - minutes%5)*60000;
			if(hour==23 && minutes >= 55){
				processEndDay(tasks);
			}
			for(Task task : tasks){
				int time = hour*60 + minutes;
				int initTime = task.getHour()*60 + task.getMinute();
				int endTime = task.getHour()*60 + task.getMinute() + task.getSize();
				if((time == initTime || time == endTime) && !task.isCompleted()){
					processTask(task);
					break;
				}
			}
			//Toast.makeText(this, "Corriendo el Servicio at " + hour + "h " + minutes + "m", Toast.LENGTH_LONG).show();
			synchronized (this) {
				try{
					wait(delay);						
				}catch(Exception e){
					throw new RuntimeException(e);
				}
			}
		}
	}


	private void processEndDay(List<Task> tasks) {
		int completedTasksCount = CollectionUtils.countMatches(tasks, new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				Task task = (Task)object;
				return task.isCompleted();
			}
		});
		int uncompletedTasksCount = tasks.size() - completedTasksCount;
		metricsService.sendMetrics(new Metric[]{
			new Metric(user, ActivityMetric.ORGANIZART, "Completadas", completedTasksCount),
			new Metric(user, ActivityMetric.ORGANIZART, "No Completadas", uncompletedTasksCount)
		});
		
	}

}
