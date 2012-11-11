package org.utn.proyecto.helpful.integrart.web.metrics.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.utn.proyecto.helpful.integrart.core.percistence.PersisterService;
import org.utn.proyecto.helpful.integrart.web.metrics.Metric;
import org.utn.proyecto.helpful.integrart.web.metrics.MetricList;

import com.google.inject.Inject;

@Path("/metricList")
public class MetricsListResource {
	private PersisterService service;
	
	@Inject
	public MetricsListResource(PersisterService service){
		this.service = service;
	}
	

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void metricList(MetricList metricList){
		for(Metric metric : metricList.getList()){
			service.insert(metric);
		}
	}
}
