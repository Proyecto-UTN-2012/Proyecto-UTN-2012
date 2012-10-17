package org.utn.proyecto.helpful.integrart.web.settings.domain;

import org.codehaus.jackson.annotate.JsonProperty;
import org.utn.proyecto.helpful.integrart.core.percistence.Entity;

@Entity("pictogram")
public class PictogramData extends ActivityData{
	@JsonProperty("_id")
	private String id;
	
	private  int[] levels;
	
	public PictogramData(){
		super();
	}
	
	public PictogramData(String user, String name, int[] levels){
		super(user, name);
		this.levels = levels;
	}

	public int[] getLevels() {
		return levels;
	}
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof PictogramData)) return false;
		PictogramData p = (PictogramData)o;
		if(!(super.equals(o)) || this.levels.length != p.levels.length) return false;
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

	public void setLevels(int[] levels) {
		this.levels = levels;
	}
}
