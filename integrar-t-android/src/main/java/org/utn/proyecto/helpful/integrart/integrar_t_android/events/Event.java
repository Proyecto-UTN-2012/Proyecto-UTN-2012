package org.utn.proyecto.helpful.integrart.integrar_t_android.events;

import android.content.Context;

public class Event<T> {
	private final Context context;
	private final T data;
	
	public Event(){
		this(null);
	}
	
	public Event(Context context){
		this(context, null);
	}
	
	public Event(Context context, T data){
		this.context = context;
		this.data = data;
	}

	public Context getContext() {
		return context;
	}

	public T getData() {
		return data;
	}
	
	
}
