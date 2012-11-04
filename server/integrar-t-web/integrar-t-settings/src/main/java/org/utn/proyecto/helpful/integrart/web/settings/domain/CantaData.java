package org.utn.proyecto.helpful.integrart.web.settings.domain;

import org.codehaus.jackson.annotate.JsonProperty;
import org.utn.proyecto.helpful.integrart.core.percistence.Entity;

@Entity("canta_con_cali")
public class CantaData extends ActivityData {
	@JsonProperty("_id")
	private String id;
	
	public CantaData(){
		super();
	}
	
	public CantaData(String user, String name){
		super(user, name);
	}
	
	@Override
	public boolean equals(Object o){
		return (o instanceof CantaData) && super.equals(o);
	}
}
