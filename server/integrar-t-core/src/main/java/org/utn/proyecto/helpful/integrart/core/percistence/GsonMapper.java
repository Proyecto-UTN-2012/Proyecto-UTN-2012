package org.utn.proyecto.helpful.integrart.core.percistence;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

public class GsonMapper implements JsonMapper {
	private final static Set<Class<?>> WRAPPER_TYPES = getWrapperTypes();
	private final JsonParser parser = new JsonParser();
	private final Gson deserializer = new Gson();
	
	public <T> String toJson(T object) {
		return getGson(object.getClass()).toJson(object);
	}

	public <T> T fromJson(String json, Class<T> clazz) {
		return getGson(clazz).fromJson(json, clazz);
	}

	@SuppressWarnings("unchecked")
	public <T> T fromJson(String json) {
		JsonElement element = parser.parse(json);
		return (T)parseElement(element);
		
	}
	
	@SuppressWarnings("unchecked")
	private <T> T parseElement(JsonElement element){
		if(element.isJsonPrimitive())
			return (T)parse(element.getAsJsonPrimitive());
		if(element.isJsonArray())
			return (T)parse(element.getAsJsonArray());
		return (T)parse(element.getAsJsonObject());
	}
	
	@SuppressWarnings("unchecked")
	private <T> T parse(JsonPrimitive element){
		if(element.isBoolean()) return (T) deserializer.fromJson(element, Boolean.class);
		if(element.isNumber()) return (T) element.getAsNumber();
		if(element.isString()) return (T) deserializer.fromJson(element, String.class);
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private <T> T parse(JsonArray element){
		//String className = cleanClassName(element.get(element.size()-1));
		return (T) deserializer.fromJson(element, Collection.class);
	}
	
	@SuppressWarnings("unchecked")
	private <T> T parse(JsonObject element){
		String className = cleanClassName(element.remove("class"));
		return (T)deserializeWithClassName(element, className);
		
	}
	
	private String cleanClassName(JsonElement element){
		return element.toString().replace("\"", "").replaceFirst("class ", "");
	}
	
	private <T> T deserializeWithClassName(JsonElement element, String className){
		try {
			@SuppressWarnings("unchecked")
			Class<T> clazz = (Class<T>) Class.forName(className);
			return deserializer.fromJson(element.toString(), clazz);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private <T> Gson getGson(Class<T> clazz){
		if(clazz.isPrimitive() || isWrapperType(clazz)) return new Gson();
		GsonBuilder builder = new GsonBuilder();
		MongoSerializer<T> serializer = new MongoSerializer<T>();
		builder.registerTypeAdapter(clazz, serializer);
		builder.registerTypeAdapter(clazz, serializer);
		return builder.create();
	}
	
	protected static boolean isWrapperType(Class<?> clazz){
        return WRAPPER_TYPES.contains(clazz);
    }

	
	private static HashSet<Class<?>> getWrapperTypes(){
        HashSet<Class<?>> ret = new HashSet<Class<?>>();
        ret.add(Boolean.class);
        ret.add(Character.class);
        ret.add(Byte.class);
        ret.add(Short.class);
        ret.add(Integer.class);
        ret.add(Long.class);
        ret.add(Float.class);
        ret.add(Double.class);
        ret.add(Void.class);
        ret.add(String.class);
        return ret;
    }  

}
