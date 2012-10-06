package org.utn.proyecto.helpful.integrart.integrar_t_android.services;


public interface DataStorageService {
	public <T> T get(String key, Class<T> clazz);
	public <T> void put(String key, T value);
	public boolean contain(String key);
	public void delete(String key);
}
