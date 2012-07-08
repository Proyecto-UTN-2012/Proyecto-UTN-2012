package org.utn.proyecto.helpful.integrart.web.settings.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.utn.proyecto.helpful.integrart.core.fileresources.FileResourceService;
import org.utn.proyecto.helpful.integrart.web.settings.services.FileUploadForm;

import com.google.inject.Inject;

@Path("/imageUpload")
public class FileUploadResource {
	private final FileResourceService service;
	
	@Inject
	public FileUploadResource(FileResourceService service){
		this.service = service;
	}
	
	@POST
	@Path("/{userId}/{activity}/{section}")
	@Consumes("multipart/form-data")
	public Response uploadTestFile(@MultipartForm FileUploadForm form, 
			@PathParam("userId") String userId, @PathParam("activity") String activity,
			@PathParam("section") String section){
		//service.uploadFile(form, path, name);
		return Response.ok().build();
	}
}
