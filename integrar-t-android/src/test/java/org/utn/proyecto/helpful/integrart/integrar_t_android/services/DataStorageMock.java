package org.utn.proyecto.helpful.integrart.integrar_t_android.services;

import java.util.HashMap;
import java.util.Map;

import org.utn.proyecto.helpful.integrart.integrar_t_android.services.DataStorageService;

public class DataStorageMock implements DataStorageService {
	private Map<String, Object> map = new HashMap<String, Object>();
	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(String key, Class<T> clazz) {
		return (T)map.get(key);
	}

	@Override
	public <T> void put(String key, T value) {
		map.put(key, value);

	}

	@Override
	public boolean contain(String key) {
		return map.containsKey(key);
	}

}
