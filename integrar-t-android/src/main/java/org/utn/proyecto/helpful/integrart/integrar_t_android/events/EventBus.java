package org.utn.proyecto.helpful.integrart.integrar_t_android.events;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import roboguice.inject.ContextSingleton;

@ContextSingleton
public class EventBus {
	@SuppressWarnings("rawtypes")
	private Map<Class<? extends Event>, Collection<EventListener>> map = new HashMap<Class<? extends Event>, Collection<EventListener>>();
	
	@SuppressWarnings("rawtypes")
	public void addEventListener(Class<? extends Event> eventType, EventListener listener){
		Collection<EventListener> collection = map.get(eventType);
		if(collection == null) collection = new ArrayList<EventListener>();
		collection.add(listener);
		map.put(eventType, collection);
	}
	
	@SuppressWarnings("rawtypes")
	public void removeEventListener(Class<? extends Event> eventType, EventListener listener){
		Collection<EventListener> collection = map.get(eventType);
		if(collection == null) return;
		collection.remove(listener);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> void dispatch(Event<?> event){
		Collection<EventListener> listeners = map.get(event.getClass());
		if(listeners == null) return;
		for(EventListener listener : listeners){
			listener.onEvent(event);
		}
	}
}
