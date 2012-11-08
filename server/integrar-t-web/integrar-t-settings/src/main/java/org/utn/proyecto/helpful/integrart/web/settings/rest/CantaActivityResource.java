package org.utn.proyecto.helpful.integrart.web.settings.rest;

import javax.ws.rs.Path;

import org.utn.proyecto.helpful.integrart.core.percistence.PersisterService;
import org.utn.proyecto.helpful.integrart.web.settings.domain.CantaData;

import com.google.inject.Inject;

@Path("/updateData/cantaActivity")
public class CantaActivityResource extends AbstractActivityResource<CantaData> {

	@Inject
	public CantaActivityResource(PersisterService service) {
		super(service);
	}

	@Override
	protected CantaData newDataInstance() {
		return new CantaData();
	}

}
