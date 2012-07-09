package org.utn.proyecto.helpful.integrart.web.settings.rest;

import javax.ws.rs.Path;

import org.utn.proyecto.helpful.integrart.web.settings.services.UpdateResourcesService;

import com.google.inject.Inject;
import com.google.inject.name.Named;

@Path("/imageUpload")
public class ImageFileUploadResource extends FileUploadResource {

	@Inject
	public ImageFileUploadResource(@Named("updateImageResourceService") UpdateResourcesService service) {
		super(service);
	}

}
