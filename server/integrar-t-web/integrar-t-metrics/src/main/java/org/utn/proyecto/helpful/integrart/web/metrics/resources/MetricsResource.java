package org.utn.proyecto.helpful.integrart.web.metrics.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.utn.proyecto.helpful.integrart.core.percistence.PersisterService;
import org.utn.proyecto.helpful.integrart.web.metrics.Metric;

import com.google.inject.Inject;

@Path("/metrics")
public class MetricsResource {
	private PersisterService service;
	
	@Inject
	public MetricsResource(PersisterService service){
		this.service = service;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void metrics( Metric metric){
		service.insert(metric);
	}
}
