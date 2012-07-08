package org.utn.proyecto.helpful.integrart.web.settings.domain;

public enum ResourceType {
	IMAGE(1);
	private final int resourceType;
	
	private ResourceType(int resourceType){
		this.resourceType = resourceType;
	}
	
	public int getResourceTypeId(){
		return this.resourceType;
	}
}
