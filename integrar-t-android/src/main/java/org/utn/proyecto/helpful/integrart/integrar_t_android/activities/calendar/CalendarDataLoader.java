package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.utn.proyecto.helpful.integrart.integrar_t_android.R;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar.Task.TaskData;
import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.User;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.DataStorageService;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.FileSystemService;

import com.google.inject.Inject;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.format.DateFormat;
import android.widget.Toast;

import roboguice.inject.ContextSingleton;

@ContextSingleton
public class CalendarDataLoader {
	private static final String ACTIVITY_NAME = "calendarActivity";
	private final DataStorageService db;
	private final User user;
	private final FileSystemService fileService;
	private final Context context;
	
	private List<TaskType> taskTypes;
	
	@Inject
	public CalendarDataLoader(Context context, DataStorageService db, User user, FileSystemService fileService){
		this.context = context;
		this.db = db;
		this.user = user;
		this.fileService = fileService;
		taskTypes = loadTaskTypes();
	}
	
	private List<TaskType> loadTaskTypes(){
		List<TaskType> taskTypes = new ArrayList<TaskType>();
		if(!(db.contain(OrganizarTUpdateService.getTaskTypesKey(user)))){
			Toast.makeText(context, context.getString(R.string.emptyTaskTypes), Toast.LENGTH_LONG).show();
			return taskTypes;
		}
		TaskTypeData[] taskData = db.get(OrganizarTUpdateService.getTaskTypesKey(user), TaskTypeData[].class);
		for(TaskTypeData data : taskData){
			Drawable pictogram = (Drawable) fileService.getResource(ACTIVITY_NAME, data.getName() + "_pictogram.png").getResource();
			Drawable large = (Drawable) fileService.getResource(ACTIVITY_NAME, data.getName() + "_large.png").getResource();
			Drawable small = (Drawable) fileService.getResource(ACTIVITY_NAME, data.getName() + "_small.png").getResource();
			taskTypes.add(new TaskType(data.getName(), pictogram, large, small));
		}
		return taskTypes;
	}
	
	public List<TaskType> getTaskTypes(){
		return taskTypes;
	}
	
	public List<Task> loadRepeatableTaskFromDay(Calendar date){
		int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);
		List<Task> loadedTasks = loadRepeatableTaskFromDayOfWeek(dayOfWeek);
		for(int i=Calendar.SUNDAY;i<=Calendar.SATURDAY;i++){
			if(dayOfWeek!=i){
				List<Task> buffer = loadRepeatableTaskFromDayOfWeek(i);
				for(Task task : loadedTasks){
					if(buffer.contains(task)) task.addRepeatDay(i);
				}
			}
			
		}
		return loadedTasks;
	}
	
	public List<Task> loadRepeatableTaskFromDayOfWeek(int dayOfWeek){
		List<Task> tasks = new ArrayList<Task>();
		if(db.contain(user.getUserName() + OrganizarActivity.ORGANIZAR_T_PACKAGE_WEEK_KEY + dayOfWeek)){
			TaskData[] taskArray = db.get(user.getUserName() + OrganizarActivity.ORGANIZAR_T_PACKAGE_WEEK_KEY + dayOfWeek, TaskData[].class);
			for(TaskData taskData : taskArray){
				for(TaskType type : taskTypes){
					if(type.getName().equals(taskData.getType())){
						Task task = new Task(type, taskData.buildCalendar(), taskData.getSize());
						task.addRepeatDay(dayOfWeek);
						tasks.add(task);
						break;
					}
				}
			}
		}	
		return tasks;
	}
	
	public List<Task> loadUnrepeatableTaskFromDay(Calendar date){
		String dateKey = DateFormat.format("yyyy.MMMM.dd", date).toString();
		List<Task> tasks = new ArrayList<Task>();
		if(db.contain(user.getUserName() + OrganizarActivity.ORGANIZAR_T_PACKAGE_KEY + dateKey)){
			TaskData[] taskArray = db.get(user.getUserName() + OrganizarActivity.ORGANIZAR_T_PACKAGE_KEY + dateKey, TaskData[].class);
			for(TaskData task : taskArray){
				for(TaskType type : taskTypes){
					if(type.getName().equals(task.getType())){
						Task _task = new Task(type, task.buildCalendar(), task.getSize());
						for(int i=Calendar.SUNDAY;i<=Calendar.SATURDAY;i++){
								List<Task> buffer = loadRepeatableTaskFromDayOfWeek(i);
								if(buffer.contains(_task)) _task.addRepeatDay(i);
							
						}
						tasks.add(_task);
						break;
					}
				}
			}
		}
		return tasks;
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	public void saveRepeatableTasks(List<Task> tasks){
		for(int i=Calendar.SUNDAY;i<=Calendar.SATURDAY;i++){
			final int dayOfWeek = i;
			List<Task> filteredTasks = new ArrayList<Task>(tasks);
			CollectionUtils.filter(filteredTasks, new Predicate() {
				@Override
				public boolean evaluate(Object object) {
					return ((Task)object).isRepeatAtDay(dayOfWeek);
				}
			});
			final List<Task> savedTasks = loadRepeatableTaskFromDayOfWeek(i);
			Collection<Task> disjunction = CollectionUtils.disjunction(savedTasks, tasks);
			Collection<Task> interjection = CollectionUtils.intersection(disjunction, savedTasks);
			Collection<Task> union = CollectionUtils.union(filteredTasks, interjection);
			TaskData[] tasksArray = new TaskData[union.size()];
			int j=0;
			for(Task task : union){
				tasksArray[j++] = task.getData();
			}
			db.put(user.getUserName() + OrganizarActivity.ORGANIZAR_T_PACKAGE_WEEK_KEY + dayOfWeek, tasksArray);			
		}
	}
	
	public void saveUnrepeatableTasks(Calendar date, List<Task> tasks){
		String dateKey = DateFormat.format("yyyy.MMMM.dd", date).toString();
		TaskData[] tasksArray = new TaskData[tasks.size()];
		for(int i=0;i<tasksArray.length;i++){
			tasksArray[i] = tasks.get(i).getData();
		}
		db.put(user.getUserName() + OrganizarActivity.ORGANIZAR_T_PACKAGE_KEY + dateKey, tasksArray);
	}
}