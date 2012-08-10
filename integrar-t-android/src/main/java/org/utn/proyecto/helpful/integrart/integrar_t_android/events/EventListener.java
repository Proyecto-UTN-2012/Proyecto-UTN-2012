package org.utn.proyecto.helpful.integrart.integrar_t_android.events;

public interface EventListener<T> {
	public void onEvent(Event<T> event);
}
