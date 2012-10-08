package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar;

import org.utn.proyecto.helpful.integrart.integrar_t_android.events.Event;

import android.content.Context;

public class UpdateTaskEvent extends Event<Task> {
	public UpdateTaskEvent(Context context, Task data){
		super(context, data);
	}
}
