package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.cantaconcali;

import android.media.MediaPlayer;

public class Cancion {
	private final MediaPlayer sonido;
	private final String nombre;
	public Cancion (String nombre, MediaPlayer sonido){
		this.nombre = nombre;
		this.sonido = sonido;
	}
	public MediaPlayer getSonido() {
		return sonido;
	}
	public String getNombre() {
		return nombre;
	}
	

}
