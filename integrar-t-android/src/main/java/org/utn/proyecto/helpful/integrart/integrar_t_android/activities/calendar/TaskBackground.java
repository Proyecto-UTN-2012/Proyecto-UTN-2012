package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;

public enum TaskBackground{
	PINK(R.drawable.pink_background_with_border),
	GREEN(R.drawable.green_background_with_border),
	ORANGE(R.drawable.orange_background_with_border),
	BLUE(R.drawable.blue_background_with_border),
	YELLOW(R.drawable.yellow_background_with_border);
	
	private final int type;
	
	private TaskBackground(int type){
		this.type = type;
	}
	
	public int getBackground(){
		return this.type;
	}	
}
