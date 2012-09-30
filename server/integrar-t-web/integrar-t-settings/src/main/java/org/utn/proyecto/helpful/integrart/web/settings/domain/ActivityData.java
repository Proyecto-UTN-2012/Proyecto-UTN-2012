package org.utn.proyecto.helpful.integrart.web.settings.domain;

import java.util.ArrayList;
import java.util.Collection;

import org.codehaus.jackson.annotate.JsonProperty;

public class ActivityData {
	@JsonProperty("_id")
	protected String id;
	protected  String user;
	protected  String name;
	protected  Collection<String> devices;
	
	public ActivityData(){
		devices = new ArrayList<String>();
	}
	
	public ActivityData(String user, String name){
		this.user = user;
		this.name = name;
		this.devices = new ArrayList<String>();
	}
	
	public boolean consumedByDeviceName(String device){
		return devices.contains(device);
	}
	
	public void addDevice(String device){
		this.devices.add(device);
	}
	
	public String getUser() {
		return user;
	}
	public String getName() {
		return name;
	}
	public Collection<String> getDevices() {
		return devices;
	}
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof ActivityData)) return false;
		ActivityData p = (ActivityData)o;
		return this.user.equals(p.user) && this.name.equals(p.name);
	}
	
	@Override
	public int hashCode(){
		return this.user.hashCode() * this.name.hashCode();
	}
	
	@Override
	public String toString(){
		return "{user: " + this.user + ", name: " + this.name + "}";
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setName(String name) {
		this.name = name;
	}
}
