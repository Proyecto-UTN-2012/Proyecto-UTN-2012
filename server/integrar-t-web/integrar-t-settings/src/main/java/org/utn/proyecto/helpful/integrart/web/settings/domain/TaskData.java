package org.utn.proyecto.helpful.integrart.web.settings.domain;

import org.utn.proyecto.helpful.integrart.core.percistence.Entity;

@Entity("task")
public class TaskData extends ActivityData{
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
