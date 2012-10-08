package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;

public enum TaskState {
	DEFAULT(0),
	READY(1),
	ACTIVED(2),
	EXCEEDED(3),
	BAD_COMPLETE(4),
	COMPLETED(5);
	
	private final int state;
	private final static int[] image = new int[]{
		R.drawable.no_entry, 
		R.drawable.led_warning_animation, 
		R.drawable.green_led, 
		R.drawable.led_error_animation, 
		R.drawable.ok_bad, 
		R.drawable.ok};
	
	private TaskState(int state){
		this.state = state;
	}
	
	public int getImageId(){
		return image[state];
	}
	
	public boolean isAnimated(){
		return state == 1 || state == 3;
	}
}
