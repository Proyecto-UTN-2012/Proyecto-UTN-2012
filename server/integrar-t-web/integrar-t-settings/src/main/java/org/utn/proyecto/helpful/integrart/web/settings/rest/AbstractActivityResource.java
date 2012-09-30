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
import org.utn.proyecto.helpful.integrart.web.settings.domain.ActivityData;

import com.google.inject.Inject;

public abstract class AbstractActivityResource<T extends ActivityData> {
	private final PersisterService service;

	@Inject
	public AbstractActivityResource(PersisterService service){
		this.service = service;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void updatePictogramActivity(T data){
		service.insert(data);
	}
	
	@GET
	@Path("/{user}/{device}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<T> getUpdateData(
			@PathParam("user") final String userId, 
			@PathParam("device") final String device){
		T example = newDataInstance();
		example.setUser(userId);
		List<T> list = service.find(example, new String[]{"user"});
		CollectionUtils.filter(list, new Predicate() {
			public boolean evaluate(Object object) {
				@SuppressWarnings("unchecked")
				T data = (T)object;
				return !data.consumedByDeviceName(device);
			}
		});
		CollectionUtils.forAllDo(list, new Closure() {
			public void execute(Object input) {
				@SuppressWarnings("unchecked")
				T data = (T)input;
				data.addDevice(device);
			}
		});
		for(T data : list){
			service.update(data);
		}
		return list;
	}
	
	protected abstract T newDataInstance();
}
