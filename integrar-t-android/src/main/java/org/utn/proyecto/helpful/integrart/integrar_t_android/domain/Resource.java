package org.utn.proyecto.helpful.integrart.integrar_t_android.domain;

public class Resource<T> {
	private String name;
	private ResourceType type;
	private T resource;
	
	public Resource(){}
	
	public Resource(String name, ResourceType type){
		this(name,type,null);
	}
	
	public Resource(String name, ResourceType type, T resource){
		this.name = name;
		this.resource = resource;
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	public ResourceType getType() {
		return type;
	}
	public T getResource() {
		return resource;
	}
	
	public void setResource(T resource){
		this.resource = resource;
	}
	
	public Resource<T> copy(){
		Resource<T> r = new Resource<T>(name, type, resource);
		return r;
	}
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof Resource<?>)) return false;
		Resource<?> r = (Resource<?>)o;
		return this.name.equals(r.name) && this.type == r.type;
	}
	
	@Override
	public int hashCode(){
		return this.name.hashCode() * this.type.hashCode() -1;
	}
	
	@Override
	public String toString(){
		return this.type.name() + ": " + this.name;
	}
}
