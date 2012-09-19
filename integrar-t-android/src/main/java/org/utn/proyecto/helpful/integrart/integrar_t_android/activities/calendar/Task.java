package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar;

import java.util.Calendar;

import android.graphics.drawable.Drawable;

public class Task {
	private final TaskType type;
	private Calendar date;
	private int size;
	
	public Task(TaskType type, Calendar date){
		this(type, date, 5);
	}
	
	public Task(TaskType type, Calendar date, int size){
		this.type = type;
		this.date = date;
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
		int myHour = date.get(Calendar.HOUR_OF_DAY);
		return myHour == hour;
	}
	
	public boolean finalizeAtHour(int hour){
		int myHour = date.get(Calendar.HOUR_OF_DAY);
		int hours = (date.get(Calendar.MINUTE) + size)/60;
		return hour == (myHour + hours);
	}
	
	public boolean inHour(int hour){
		int myHour = date.get(Calendar.HOUR_OF_DAY);
		int hours = (date.get(Calendar.MINUTE) + size)/60;
		return hour>=myHour && hour<=(myHour + hours);
	}
	
	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
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
}
