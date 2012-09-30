package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar;

import android.graphics.drawable.Drawable;

public class TaskType {
	private final String name;
	private final Drawable pictogram;
	private final Drawable largeImage;
	private final Drawable smallImage;
	
	public TaskType(String name, Drawable pictogram, Drawable largeImage, Drawable smallImage){
		this.name = name;
		this.pictogram = pictogram;
		this.largeImage = largeImage;
		this.smallImage = smallImage;
	}

	public String getName() {
		return name;
	}

	public Drawable getPictogram() {
		return pictogram;
	}

	public Drawable getLargeImage() {
		return largeImage;
	}

	public Drawable getSmallImage() {
		return smallImage;
	}
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof TaskType)) return false;
		TaskType t = (TaskType)o;
		return this.name.equals(t.name);
	}
	
	@Override
	public int hashCode(){
		return this.name.hashCode();
	}
	
	@Override
	public String toString(){
		return this.getName();
	}
}
