package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import android.graphics.drawable.Drawable;

public class Task implements Comparable<Task>{
	private final TaskType type;
	private final int year;
	private final int month;
	private final int day;
	private final int hour;
	private final int minute;
	private int size;
	private Set<Integer> repeatDays = new HashSet<Integer>();
	
	public Task(TaskType type, Calendar date){
		this(type, date, 5);
	}
	
	public Task(TaskType type, Calendar date, int size){
		this.type = type;
		this.year = date.get(Calendar.YEAR);
		this.month = date.get(Calendar.MONTH);
		this.day = date.get(Calendar.DAY_OF_MONTH);
		this.hour = date.get(Calendar.HOUR_OF_DAY);
		this.minute = date.get(Calendar.MINUTE);
		this.size = size;
	}
	
	public String getName(){
		return type.getName();
	}
	
	public Drawable getPictogram(){
		return type.getPictogram();
	}
	
	public Drawable getLargeImage(){
		return type.getLargeImage();
	}
	
	public Drawable getSmallImage(){
		return type.getSmallImage();
	}
	
	public boolean initAtHour(int hour){
		int myHour = this.hour;
		return myHour == hour;
	}
	
	public boolean finalizeAtHour(int hour){
		int myHour = this.hour;
		int hours = (this.minute + size)/60;
		return hour == (myHour + hours);
	}
	
	public boolean inHour(int hour){
		int myHour = this.hour;
		int hours = (this.minute + size)/60;
		return hour>=myHour && hour<=(myHour + hours);
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public TaskType getType() {
		return type;
	}
	
	public int getHour(){
		return hour;
	}
	
	public int getMinute(){
		return minute;
	}
	
	public int getYear(){
		return year;
	}
	
	public int getMonth(){
		return month;
	}

	public int getDay(){
		return day;
	}
	
	public boolean isRepeatable(){
		return !repeatDays.isEmpty();
	}
	
	public TaskData getData(){
		return new TaskData(this);
	}
	
	public void addRepeatDay(int dayOfWeek){
		repeatDays.add(dayOfWeek);
	}
	
	public void removeRepeatDay(int dayOfWeek){
		repeatDays.remove(dayOfWeek);
	}
	
	public void clearRepeat(){
		repeatDays.clear();
	}
	
	public Set<Integer> getRepeatDays(){
		return new HashSet<Integer>(repeatDays);
	}
	
	public boolean isRepeatAtDay(int dayOfWeek){
		return repeatDays.contains(dayOfWeek);
	}
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof Task)) return false;
		Task t = (Task)o;
		return this.type.equals(t.type) 
				&& this.hour == t.hour
				&& this.minute == t.minute;
	}
	
	public int hashCode(){
		return this.type.hashCode() - this.hour + this.minute;
	}
	
	@Override
	public String toString(){
		return getName() + " at " + hour + "h " + minute + "m";
	}
	
	public class TaskData{
		private final String type;
		private final int year;
		private final int month;
		private final int day;
		private final int hour;
		private final int minute;
		private final int size;
		private final boolean repeatable;
		
		private TaskData(Task task){
			this.type = task.getName();
			this.year = task.getYear();
			this.month = task.getMonth();
			this.day = task.getDay();
			this.hour = task.getHour();
			this.minute = task.getMinute();
			this.size = task.getSize();
			this.repeatable = task.isRepeatable();
		}

		public int getSize() {
			return size;
		}

		public String getType() {
			return type;
		}

		public int getYear() {
			return year;
		}

		public int getMonth() {
			return month;
		}

		public int getDay() {
			return day;
		}

		public int getHour() {
			return hour;
		}

		public int getMinute() {
			return minute;
		}
		
		public boolean getRepeatable(){
			return repeatable;
		}
		
		public Calendar buildCalendar(){
			Calendar date = (Calendar) Calendar.getInstance().clone();
			date.set(Calendar.YEAR, year);
			date.set(Calendar.MONTH, month);
			date.set(Calendar.DAY_OF_MONTH, day);
			date.set(Calendar.HOUR_OF_DAY, hour);
			date.set(Calendar.MINUTE, minute);
			return date;
		}
	}

	@Override
	public int compareTo(Task another) {
		if(this.hour != another.hour)
			return this.hour - another.hour;
		return this.minute - another.minute;
	}
}
