package org.utn.proyecto.helpful.integrart.integrar_t_android.services;

import java.io.InputStream;

import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.Resource;
import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.ResourceType;

public interface FileSystemService {
	
	public <T> Resource<T> getResource(String activityName, String resourceName);
	/**
	 * Obtiene un recurso especifico
	 * @param activityName
	 * @param packageName
	 * @param resourceName
	 * @return
	 */
	public <T> Resource<T> getResource(String activityName, String packageName, String resourceName);

	/**
	 * Obtiene todos los recursos de un package
	 * @param activityName
	 * @param packageName
	 * @return
	 */
	public Resource<?>[] getResources(String activityName, String packageName);
	
	/**
	 * Obtiene todos los recursos de un package de un tipo especifico
	 * @param activityName
	 * @param packageName
	 * @param type
	 * @return
	 */
	public Resource<?>[] getResources(String activityName, String packageName, ResourceType type);
	
	/**
	 * Obtiene todos los recursos
	 * @param activityName
	 * @return
	 */
	public Resource<?>[] getResources(String activityName);
	
	/**
	 * Obtiene todos los recursos de un tipo especifico
	 * @param activityName
	 * @param type
	 * @return
	 */
	public Resource<?>[] getResources(String activityName, ResourceType type);

	/**
	 * Devuelve los nombres de recursos dentro del paquete
	 * @param activityName
	 * @param packageName
	 * @return
	 */
	public String[] getResourcesNames(String activityName, String packageName);
	
	/**
	 * Devuelve los nombres de recursos dentro del paquete de un tipo determinado
	 * @param activityName
	 * @param packageName
	 * @return
	 */
	public String[] getResourcesNames(String activityName, String packageName, ResourceType type);
	
	/**
	 * Devuelve los nombres de recursos
	 * @param activityName
	 * @param packageName
	 * @return
	 */
	public String[] getResourcesNames(String activityName);
	
	/**
	 * Devuelve los nombres de recursos de un tipo determinado
	 * @param activityName
	 * @param packageName
	 * @return
	 */
	public String[] getResourcesNames(String activityName, ResourceType type);
	
	/**
	 * Agrega un package
	 * @param activityName
	 * @param packageName
	 */
	public void addPackage(String activityName, String packageName);
	public void addResource(String activityName, String packageName, Resource<InputStream> resource);
	public void addResource(String activityName, String packageName, String name, ResourceType type, InputStream io);
	public void addResource(String activityName, String packageName, String name, ResourceType type, byte[] bytes);
	public void addResource(String activityName, String packageName, String name, ResourceType type, String data);
	public void addResource(String activityName, Resource<InputStream> resource);
	public void addResource(String activityName, String name, ResourceType type, InputStream io);
	public void addResource(String activityName, String name, ResourceType type, byte[] bytes);
	public void addResource(String activityName, String name, ResourceType type, String data);

}
