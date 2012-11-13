package org.utn.proyecto.helpful.integrart.web.metrics.strategies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.utn.proyecto.helpful.integrart.web.metrics.Metric;

public abstract class ActivityReportStrategy {
	abstract public Workbook build(List<Metric> metrics);
	
	protected Map<String, List<Metric>> groupByUser(List<Metric> metrics) {
		Map<String, List<Metric>> map = new HashMap<String, List<Metric>>();
		for(Metric metric : metrics){
			List<Metric> list = map.get(metric.getUser().getUserName());
			if(list==null){
				list = new ArrayList<Metric>();
				map.put(metric.getUser().getUserName(), list);
			}
			list.add(metric);
		}
		return map;
	}
	
	protected Map<String, List<Metric>> groupByCategory(List<Metric> metrics){
		Map<String, List<Metric>> map = new HashMap<String, List<Metric>>();
		for(Metric metric : metrics){
			List<Metric> list = map.get(metric.getCategory());
			if(list==null){
				list = new ArrayList<Metric>();
				map.put(metric.getCategory(), list);
			}
			list.add(metric);
		}
		return map;
	}

	protected Map<String, List<Metric>> groupBySubcategory(int index, List<Metric> metrics){
		Map<String, List<Metric>> map = new HashMap<String, List<Metric>>();
		for(Metric metric : metrics){
			List<Metric> list = map.get(metric.getSubcategories()[index]);
			if(list==null){
				list = new ArrayList<Metric>();
				map.put(metric.getSubcategories()[index], list);
			}
			list.add(metric);
		}
		return map;
	}
}
