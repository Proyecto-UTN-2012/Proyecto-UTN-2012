package org.utn.proyecto.helpful.integrart.core.percistence;

import java.util.List;

public interface PersisterService {
	public <T> T insert(T object);
	public <T> List<T> find(Class<T> clazz);
	<T> List<T> find(T example,String[] properties);
	public <T> T update(T object);
}
