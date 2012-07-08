package org.utn.proyecto.helpful.integrart.core.percistence;

import java.util.List;

import com.mongodb.DBObject;

interface MongoMapper {
	public <T> DBObject toDbObject(T value);
	public <T> T fromDbObject(DBObject object, final Class<T> clazz);
	public <T> List<T> fromDbObject(List<DBObject> list, final Class<T> clazz);
}
