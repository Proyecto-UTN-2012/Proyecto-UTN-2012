package org.utn.proyecto.helpful.integrart.web.settings.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.utn.proyecto.helpful.integrart.core.percistence.PersisterService;
import org.utn.proyecto.helpful.integrart.web.settings.domain.PictogramData;

import com.google.inject.Inject;

@Path("/updateData/pictogramActivity")
public class PictogramResource {
	private final PersisterService service;

	@Inject
	public PictogramResource(PersisterService service){
		this.service = service;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void updatePictogramActivity(PictogramData data){
		service.insert(data);
	}
	
	@GET
	@Path("/{user}/{device}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PictogramData> getUpdateData(
			@PathParam("user") final String userId, 
			@PathParam("device") final String device){
		PictogramData example = new PictogramData();
		example.setUser(userId);
		List<PictogramData> list = service.find(example, new String[]{"user"});
		CollectionUtils.filter(list, new Predicate() {
			public boolean evaluate(Object object) {
				PictogramData data = (PictogramData)object;
				return !data.consumedByDeviceName(device);
			}
		});
		CollectionUtils.forAllDo(list, new Closure() {
			public void execute(Object input) {
				PictogramData data = (PictogramData)input;
				data.addDevice(device);
			}
		});
		for(PictogramData data : list){
			service.update(data);
		}
		return list;
	}
}
