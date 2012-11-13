package org.utn.proyecto.helpful.integrart.integrar_t_android.metrics;

import java.util.List;

public class MetricList {
	private List<Metric> list;
	
	public MetricList(){}
	
	public MetricList(List<Metric> list){
		this.list = list;
	}
	
	public void setList(List<Metric> list){
		this.list = list;
	}
	
	public List<Metric> getList(){
		return list;
	}
}
