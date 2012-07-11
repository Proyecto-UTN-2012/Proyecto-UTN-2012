package org.utn.proyecto.helpful.integrart.integrar_t_android.domain;

public enum ResourceType {
	IMAGE(0);
	private final int resourceType;
	private final static String[] extensions = new String[]{".png"};
	
	private ResourceType(int resourceType){
		this.resourceType = resourceType;
	}
	
	public int getResourceTypeId(){
		return this.resourceType;
	}
	
	public String getResourceExtension(){
		return extensions[getResourceTypeId()];
	}
}
