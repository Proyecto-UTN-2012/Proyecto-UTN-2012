package org.utn.proyecto.helpful.integrart.web.settings.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.utn.proyecto.helpful.integrart.web.settings.domain.ActivityResource;
import org.utn.proyecto.helpful.integrart.web.settings.services.UpdateResourcesService;

import com.google.inject.Inject;
import com.google.inject.name.Named;

@Path("/updateRequest")
public class UpdateRequestResource {
	private final UpdateResourcesService service;
	
	@Inject
	public UpdateRequestResource(@Named("updateImageResourceService") UpdateResourcesService service){
		this.service = service;
	}
	
	@GET
	@Path("/{userId}/{device}/{activity}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ActivityResource> getUpdates( 
			@PathParam("userId") final String userId,
			@PathParam("device") final String device,
			@PathParam("activity") final String activity){
		List<ActivityResource> list = service.getResources(userId, activity);
		CollectionUtils.filter(list, new Predicate() {
			public boolean evaluate(Object object) {
				ActivityResource resource = (ActivityResource)object;
				return !resource.consumedByDeviceName(device);
			}
		});
		CollectionUtils.forAllDo(list, new Closure() {
			public void execute(Object input) {
				ActivityResource resource = (ActivityResource)input;
				resource.addDevice(device);
			}
		});
		service.updateResources(list);
		return list;
	}
}
