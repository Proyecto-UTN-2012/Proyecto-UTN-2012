package org.utn.proyecto.helpful.integrart.core.percistence;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;

public class MongoSerializer<T extends Object> implements JsonSerializer<T> {
	private final Gson gson = new Gson();
	
	public JsonElement serialize(T object, Type arg1,
			JsonSerializationContext arg2) {
		@SuppressWarnings("unchecked")
		TypeAdapter<T> adapter = (TypeAdapter<T>) gson.getAdapter(object.getClass());
		JsonElement element = adapter.toJsonTree(object);
		element = addClass(element, object.getClass().toString());
		return element;
	}

	private JsonElement addClass(JsonElement json, String clazz){
		if(json.isJsonArray()){
//			JsonArray array = json.getAsJsonArray();
//			array.add(new JsonPrimitive(clazz));
//			return array;
			return json;
		}
		JsonObject object = json.getAsJsonObject();
		object.addProperty("class", clazz);
		return object;
	}

}
