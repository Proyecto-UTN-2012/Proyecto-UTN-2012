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
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.text.format.DateFormat;
import android.util.Log;

import com.google.inject.Inject;

public class TaskNotificationService extends RoboIntentService {
	private static final String TAG = "TaskNotificationService";
	// Use a layout id for a unique identifier
    private static final int MOOD_NOTIFICATIONS = 1300043;
	
	@Inject
	private DataStorageService db;
	@Inject
	private CalendarDataLoader loader;
	
	@Inject
	private EventBus bus;
	
	@Inject
	private MetricsService metricsService;
	
	@Inject
	private User user;
	
	private boolean end;
	private boolean running;
	
	private MediaPlayer alarm;
	
	private NotificationManager manager;
	
	
	public TaskNotificationService(){
		super(TAG);
	}
	
	
	public void onCreate(){
		super.onCreate();
		if(this.running) return;
		manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		Intent intent = new Intent(this, TaskNotificationService.class);
		PendingIntent pintent = PendingIntent.getService(this, 0, intent, 0);
		AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
		alarm.setRepeating(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis(), 30*1000, pintent);
		Log.i(TAG, "OnCreate Service");
	}
	
	@Override
	public boolean stopService(Intent service){
		Log.i(TAG, "Stop Service");
		this.end = true;
		return super.stopService(service);
	}
	
	@Override
	public void onDestroy(){
		Log.i(TAG, "Destroy Service");
		this.end = true;
		super.onDestroy();
	}
	
	private void processTask(Task task){
		Log.i(TAG, "Process Task" + task.toString());
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
					Log.i(TAG, "Alarm Error");
					throw new RuntimeException(e);
				} 
			}
		}, 10000);

		Intent intent = new Intent(this, ShowTaskActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				intent, 0);
		
//		Notification notification = new Notification(R.drawable.icon_trans_icon, null, System.currentTimeMillis());
		Notification.Builder builder = new Notification.Builder(this)
			.setContentTitle(getString(R.string.newTaskMessage))
			.setContentText(task.getName())
			.setSmallIcon(R.drawable.icon_trans_icon)
			.setContentIntent(pendingIntent)
			.setAutoCancel(true);
//		notification.setLatestEventInfo(this, getString(R.string.newTaskMessage),
//                task.getName(), pendingIntent);
		Notification note = builder.build();
		note.ledARGB = 0xffff6000;
		note.flags = Notification.FLAG_AUTO_CANCEL /*| Notification.FLAG_ONGOING_EVENT*/ | Notification.FLAG_SHOW_LIGHTS;
		manager.notify(MOOD_NOTIFICATIONS, note);
//		startForeground(1335667, note);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		if(running) return;
		running = true;
		Log.i(TAG, "Start Service");
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
					Log.i(TAG, "Synchronize Error");
					throw new RuntimeException(e);
				}
			}
		}
		Log.i(TAG, "End Service");
	}


	private void processEndDay(List<Task> tasks) {
		Log.i(TAG, "Process end day");
		int completedTasksCount = CollectionUtils.countMatches(tasks, new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				Task task = (Task)object;
				return task.isCompleted();
			}
		});
		int uncompletedTasksCount = tasks.size() - completedTasksCount;
		Calendar date = Calendar.getInstance();
		String dateString = DateFormat.format("dd/MM/yyyy", date).toString();
		metricsService.sendMetrics(new Metric[]{
			new Metric(user, ActivityMetric.ORGANIZART, "Completadas", dateString, completedTasksCount),
			new Metric(user, ActivityMetric.ORGANIZART, "No Completadas", dateString, uncompletedTasksCount)
		});
		
	}

}
