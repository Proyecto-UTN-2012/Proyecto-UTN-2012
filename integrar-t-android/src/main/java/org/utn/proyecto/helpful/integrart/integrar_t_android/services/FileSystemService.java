package org.utn.proyecto.helpful.integrart.integrar_t_android.services;

import java.io.InputStream;

import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.Resource;
import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.ResourceType;

public interface FileSystemService {
	/**
	 * Obtiene un recurso especifico
	 * @param userName
	 * @param activityName
	 * @param packageName
	 * @param resourceName
	 * @return
	 */
	public <T> Resource<T> getResource(String userName, String activityName, String packageName, String resourceName);

	/**
	 * Obtiene todos los recursos de un package
	 * @param userName
	 * @param activityName
	 * @param packageName
	 * @return
	 */
	public Resource<?>[] getResources(String userName, String activityName, String packageName);
	
	/**
	 * Obtiene todos los recursos de un package de un tipo especifico
	 * @param userName
	 * @param activityName
	 * @param packageName
	 * @param type
	 * @return
	 */
	public Resource<?>[] getResources(String userName, String activityName, String packageName, ResourceType type);
	
	/**
	 * Obtiene todos los recursos
	 * @param userName
	 * @param activityName
	 * @return
	 */
	public Resource<?>[] getResources(String userName, String activityName);
	
	/**
	 * Obtiene todos los recursos de un tipo especifico
	 * @param userName
	 * @param activityName
	 * @param type
	 * @return
	 */
	public Resource<?>[] getResources(String userName, String activityName, ResourceType type);

	/**
	 * Devuelve los nombres de recursos dentro del paquete
	 * @param userName
	 * @param activityName
	 * @param packageName
	 * @return
	 */
	public String[] getResourcesNames(String userName, String activityName, String packageName);
	
	/**
	 * Devuelve los nombres de recursos dentro del paquete de un tipo determinado
	 * @param userName
	 * @param activityName
	 * @param packageName
	 * @return
	 */
	public String[] getResourcesNames(String userName, String activityName, String packageName, ResourceType type);
	
	/**
	 * Devuelve los nombres de recursos
	 * @param userName
	 * @param activityName
	 * @param packageName
	 * @return
	 */
	public String[] getResourcesNames(String userName, String activityName);
	
	/**
	 * Devuelve los nombres de recursos de un tipo determinado
	 * @param userName
	 * @param activityName
	 * @param packageName
	 * @return
	 */
	public String[] getResourcesNames(String userName, String activityName, ResourceType type);
	
	/**
	 * Agrega un package
	 * @param userName
	 * @param activityName
	 * @param packageName
	 */
	public void addPackage(String userName, String activityName, String packageName);
	public void addResource(String userName, String activityName, String packageName, Resource<InputStream> resource);
	public void addResource(String userName, String activityName, String packageName, String name, ResourceType type, InputStream io);
	public void addResource(String userName, String activityName, String packageName, String name, ResourceType type, byte[] bytes);
	public void addResource(String userName, String activityName, String packageName, String name, ResourceType type, String data);
	public void addResource(String userName, String activityName, Resource<InputStream> resource);
	public void addResource(String userName, String activityName, String name, ResourceType type, InputStream io);
	public void addResource(String userName, String activityName, String name, ResourceType type, byte[] bytes);
	public void addResource(String userName, String activityName, String name, ResourceType type, String data);

}
