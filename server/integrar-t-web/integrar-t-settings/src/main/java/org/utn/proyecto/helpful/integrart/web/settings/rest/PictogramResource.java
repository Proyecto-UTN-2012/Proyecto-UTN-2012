package org.utn.proyecto.helpful.integrart.web.settings.rest;

import javax.ws.rs.Path;

import org.utn.proyecto.helpful.integrart.core.percistence.PersisterService;
import org.utn.proyecto.helpful.integrart.web.settings.domain.PictogramData;

import com.google.inject.Inject;

@Path("/updateData/pictogramActivity")
public class PictogramResource extends AbstractActivityResource<PictogramData> {

	@Inject
	public PictogramResource(PersisterService service){
		super(service);
	}

	@Override
	protected PictogramData newDataInstance() {
		return new PictogramData();
	}
}
