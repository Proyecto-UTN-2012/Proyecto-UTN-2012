package org.utn.proyecto.helpful.integrart.integrar_t_android.events;

import android.content.Context;

public class ChangeColorsEvent extends Event<int[]> {
	public ChangeColorsEvent(Context context, int[] colors){
		super(context, colors);
	}
}
