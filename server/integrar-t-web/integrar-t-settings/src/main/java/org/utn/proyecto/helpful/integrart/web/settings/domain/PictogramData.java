package org.utn.proyecto.helpful.integrart.web.settings.domain;

import java.util.ArrayList;
import java.util.Collection;

import org.codehaus.jackson.annotate.JsonProperty;
import org.utn.proyecto.helpful.integrart.core.percistence.Entity;

@Entity("pictogram")
public class PictogramData {
	@JsonProperty("_id")
	private String id;
	private  String user;
	private  String name;
	private  int level;
	private  Collection<String> devices;
	
	public PictogramData(){
		devices = new ArrayList<String>();
	}
	
	public PictogramData(String user, String name, int level){
		this.user = user;
		this.name = name;
		this.level = level;
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
	public int getLevel() {
		return level;
	}
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof PictogramData)) return false;
		PictogramData p = (PictogramData)o;
		return this.user.equals(p.user) && this.name.equals(p.name) && this.level == p.level;
	}
	
	@Override
	public int hashCode(){
		return this.user.hashCode() * this.name.hashCode() - this.level;
	}
	
	@Override
	public String toString(){
		return "{user: " + this.user + ", name: " + this.name + ", level: " + this.level + "}";
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}
