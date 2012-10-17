package org.utn.proyecto.helpful.integrart.web.settings.domain;

import org.codehaus.jackson.annotate.JsonProperty;
import org.utn.proyecto.helpful.integrart.core.percistence.Entity;

@Entity("task")
public class TaskData extends ActivityData{
	@JsonProperty("_id")
	private String id;
	
	public TaskData(){
		super();
	}
	
	public TaskData(String user, String name){
		super(user, name);
	}
	
	@Override
	public boolean equals(Object o){
		return (o instanceof TaskData) && super.equals(o);
	}
}
