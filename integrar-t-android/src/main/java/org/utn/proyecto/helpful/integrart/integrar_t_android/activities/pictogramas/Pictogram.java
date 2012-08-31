package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.pictogramas;

import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;

public class Pictogram {
	private int level;
	private Drawable image;
	private String name;
	private MediaPlayer sound;
	private boolean ready;
	
	public Pictogram(String name, int level, Drawable image, MediaPlayer sound){
		this.name = name;
		this.level = level;
		this.image = image;
		this.sound = sound;
	}

	public Drawable getImage() {
		return image;
	}

	public String getName() {
		return name;
	}

	public MediaPlayer getSound() {
		return sound;
	}
	
	public int getLevel(){
		return level;
	}
	
	public boolean isReady(){
		return ready;
	}
	
	public void setReady(){
		this.ready = true;
	}
	
	public void release(){
		sound.release();
		sound = null;
	}
}
