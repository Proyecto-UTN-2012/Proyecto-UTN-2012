package org.utn.proyecto.helpful.integrart.web.metrics;


public class MetricList {
	private Metric[] list;
	
	public MetricList(){}
	
	public MetricList(Metric[] list){
		this.list = list;
	}
	
	public void setList(Metric[] list){
		this.list = list;
	}
	
	public Metric[] getList(){
		return list;
	}
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof MetricList)) return false;
		MetricList l = (MetricList)o;
		if(this.list.length != l.list.length) return false;
		for(int i=0;i<list.length;i++){
			if(!list[i].equals(l.list[i])) return false;
		}
		return true;
	}
	
	@Override
	public int hashCode(){
		int code = 0;
		for(Metric m : list){
			code+= m.hashCode();
		}
		return code;
	}
	
	@Override
	public String toString(){
		return list.toString();
	}
}
