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
	private  int[] levels;
	private  Collection<String> devices;
	
	public PictogramData(){
		devices = new ArrayList<String>();
	}
	
	public PictogramData(String user, String name, int[] levels){
		this.user = user;
		this.name = name;
		this.levels = levels;
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
	public int[] getLevels() {
		return levels;
	}
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof PictogramData)) return false;
		PictogramData p = (PictogramData)o;
		if(!(this.user.equals(p.user) && this.name.equals(p.name)) || this.levels.length != p.levels.length) return false;
		for(int i = 0;i<levels.length;i++){
			if(levels[i] != p.levels[i]) return false;
		}
		return true;
	}
	
	@Override
	public int hashCode(){
		return this.user.hashCode() * this.name.hashCode() - this.levels.hashCode();
	}
	
	@Override
	public String toString(){
		return "{user: " + this.user + ", name: " + this.name + ", levels: " + this.levels + "}";
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLevels(int[] levels) {
		this.levels = levels;
	}
}
