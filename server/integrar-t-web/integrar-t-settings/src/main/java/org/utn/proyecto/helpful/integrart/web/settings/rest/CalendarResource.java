package org.utn.proyecto.helpful.integrart.web.settings.rest;

import javax.ws.rs.Path;

import org.utn.proyecto.helpful.integrart.core.percistence.PersisterService;
import org.utn.proyecto.helpful.integrart.web.settings.domain.TaskData;

import com.google.inject.Inject;

@Path("/updateData/calendarActivity")
public class CalendarResource extends AbstractActivityResource<TaskData>{

	@Inject
	public CalendarResource(PersisterService service){
		super(service);
	}

	@Override
	protected TaskData newDataInstance() {
		return new TaskData();
	}
}
