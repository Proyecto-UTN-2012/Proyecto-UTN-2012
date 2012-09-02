package org.utn.proyecto.helpful.integrart.web.settings.domain;

public enum ResourceType {
	IMAGE(0),
	SOUND(1),
	VIDEO(2),
	STRING(3);
	private final int resourceType;
	private final static String[] extensions = new String[]{".png", ".mp3", "mpg", ""};
	
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
