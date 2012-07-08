package org.utn.proyecto.helpful.integrart.core.percistence;

public interface JsonMapper {
	public <T> String toJson(T object);
	public <T> T fromJson(String json, Class<T> clazz);
	public <T> T fromJson(String json);
}
