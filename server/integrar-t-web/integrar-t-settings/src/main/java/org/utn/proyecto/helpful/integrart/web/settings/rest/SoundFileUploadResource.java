package org.utn.proyecto.helpful.integrart.web.settings.rest;

import javax.ws.rs.Path;

import org.utn.proyecto.helpful.integrart.web.settings.services.UpdateResourcesService;

import com.google.inject.Inject;
import com.google.inject.name.Named;

@Path("/soundUpload")
public class SoundFileUploadResource extends FileUploadResource {

	@Inject
	public SoundFileUploadResource(@Named("updateSoundResourceService") UpdateResourcesService service) {
		super(service);
	}

}
