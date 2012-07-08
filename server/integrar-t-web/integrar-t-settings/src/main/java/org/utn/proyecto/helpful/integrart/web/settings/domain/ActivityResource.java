package org.utn.proyecto.helpful.integrart.web.settings.domain;

import java.util.ArrayList;
import java.util.Collection;

import org.codehaus.jackson.annotate.JsonProperty;
import org.utn.proyecto.helpful.integrart.core.percistence.Entity;

@Entity("ActivityResource")
public class ActivityResource {
	@JsonProperty("_id")
	private String id;
	private String userId;
	private Collection<String> devices;
	private String activityName;
	private String activitySection;
	private String resourceName;
	private ResourceType resourceType;
	
	public ActivityResource(String userId, String activityName, String activitySection, String resourceName, ResourceType resourceType){
		this.userId = userId;
		this.activityName = activityName;
		this.activitySection = activitySection;
		this.resourceName = resourceName;
		this.resourceType = resourceType;
		this.devices = new ArrayList<String>();
	}
	
	public ActivityResource(String userId, String activityName, String resourceName, ResourceType resourceType){
		this(userId, activityName, null, resourceName, resourceType);
	}
	
	public ActivityResource(String userId, String activityName, String resourceName){
		this(userId, activityName, null, resourceName, ResourceType.IMAGE);
	}
	
	public String getPath(){
		String section = activitySection == null ? "/" : "/" + activitySection + "/"; 
		return activityName + section + resourceName;
	}

	public boolean consumedByDeviceName(String deviceName){
		return devices.contains(deviceName);
	}
	
	@Override
	public String toString(){
		return "{_id:"+id+", userId:"+userId+", activityName:"+activityName+", activitySection:"+activitySection+
				", resourceName:"+resourceName+", resourceType:"+resourceType.name()+"}";
	}
	
	
	@Override
	public boolean equals(Object o){
		if(o instanceof ActivityResource) return false;
		ActivityResource a = (ActivityResource)o;
		boolean equalSection = (this.activitySection == null && a.activitySection==null)
				|| (this.activitySection != null && a.activitySection != null && this.activitySection.equals(a.activitySection));
		return a.userId.equals(this.userId) && a.activityName.equals(this.activityName) && equalSection
				&& this.resourceName.equals(a.resourceName) && this.resourceType.equals(a.resourceType) && this.devices.equals(a.devices);
	}
	
	@Override
	public int hashCode(){
		return userId.hashCode() + activityName.hashCode() + 
				+ resourceName.hashCode() + resourceType.hashCode() + devices.hashCode();
	}
}
