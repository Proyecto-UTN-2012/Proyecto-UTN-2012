package org.utn.proyecto.helpful.integrart.core.percistence;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class GsonMongoMapper extends GsonMapper implements MongoMapper {
	private final ObjectMapper mapper = new ObjectMapper();
	
	public <T> DBObject toDbObject(T value) {
		BasicDBObject dbObject = new BasicDBObject();
		
		@SuppressWarnings("unchecked")
		Map<String, Object> map = mapper.convertValue(value,HashMap.class);
		String id = (String)map.remove("id");
		if(id != null){
			map.put("_id", id);
		}
		dbObject.putAll(map);
		return dbObject;
	}

	public <T> T fromDbObject(DBObject object, Class<T> clazz) {
		Collection<Field> fields = getJsonPropertyFields(clazz);
		for(Field field : fields){
			String propertyName = field.getAnnotation(JsonProperty.class).value();
			Object propertyValue = object.removeField(propertyName);
			object.put(field.getName(), propertyValue);
		}
		return fromJson(object.toString(), clazz);
	}

	public <T> List<T> fromDbObject(List<DBObject> list,final Class<T> clazz) {
		List<T> newList = new ArrayList<T>();
		for(DBObject dbObject : list){
			newList.add(fromDbObject(dbObject, clazz));
		}
		return newList;
	}
	
	private <T> Collection<Field> getJsonPropertyFields(Class<T> clazz){
		Field[] fields = clazz.getDeclaredFields();
		Collection<Field> list = new ArrayList<Field>();
		for(Field field : fields){
			JsonProperty annotation = field.getAnnotation(JsonProperty.class);
			if(annotation!=null) list.add(field);
		}
		return list;
	}

}
