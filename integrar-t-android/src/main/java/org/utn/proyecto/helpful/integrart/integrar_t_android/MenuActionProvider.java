package org.utn.proyecto.helpful.integrart.integrar_t_android;

import java.lang.reflect.Constructor;
import java.util.Map;

import org.utn.proyecto.helpful.integrart.integrar_t_android.events.EventBus;

import android.content.Context;

public abstract class MenuActionProvider {
	protected final Context context;
	protected final EventBus bus;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static MenuActionProvider newInstance(Class<? extends MenuActionProvider> clazz, Context context, EventBus bus, Map<String, Object> params){
		int paramCount = params==null ? 2 : 3;
		Constructor[] constructors = clazz.getConstructors();
		for(Constructor<? extends MenuActionProvider> constructor: constructors){
			if(constructor.getParameterTypes().length==paramCount){
				try {
					return constructor.newInstance(context, bus, params);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
		throw new RuntimeException("Esta clase no se puede instanciar con este metodo");
	}
	
	public static MenuActionProvider newInstance(Class<? extends MenuActionProvider> clazz, Context context, EventBus bus){
		return newInstance(clazz, context, bus, null);
	}
	
	protected MenuActionProvider(Context context, EventBus bus){
		this.context = context;
		this.bus = bus;
	}
	
	protected MenuActionProvider(Context context, EventBus bus, Map<String, Object> params){
		this(context, bus);
	}
	
	public abstract boolean execute();
	
	
}
