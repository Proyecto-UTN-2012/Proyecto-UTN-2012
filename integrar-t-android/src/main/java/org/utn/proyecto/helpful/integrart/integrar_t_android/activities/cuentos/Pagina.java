package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.cuentos;

import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;

public class Pagina {

	private String text;
	private Drawable image;
	private MediaPlayer sound;
	private boolean lastPage;

	public Pagina(String text, Drawable image, MediaPlayer sound, boolean lastPage) {
		this(text, image, sound);
		this.lastPage = lastPage;
	}

	public Pagina(String text, Drawable image, MediaPlayer sound) {

		this.text = text;
		this.image = image;
		this.sound = sound;

	}
	
	public Drawable getImage() {
		return image;
	}

	public String getText() {
		return text;
	}

	public MediaPlayer getSound() {
		return sound;
	}

	public boolean isLastPage() {
		return lastPage;
	}
}