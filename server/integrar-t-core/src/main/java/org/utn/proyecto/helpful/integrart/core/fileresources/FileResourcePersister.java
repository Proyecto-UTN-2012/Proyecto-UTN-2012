package org.utn.proyecto.helpful.integrart.core.fileresources;

public interface FileResourcePersister {
	public void save(byte[] bytes, String path, String name);
	public void save(byte[] bytes, String path);
}
