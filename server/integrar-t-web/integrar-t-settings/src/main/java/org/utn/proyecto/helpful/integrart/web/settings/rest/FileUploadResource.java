package org.utn.proyecto.helpful.integrart.web.settings.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.utn.proyecto.helpful.integrart.web.settings.services.FileUploadForm;
import org.utn.proyecto.helpful.integrart.web.settings.services.UpdateResourcesService;

public abstract class FileUploadResource {
	private final UpdateResourcesService service;
	
	public FileUploadResource(UpdateResourcesService service){
		this.service = service;
	}
	
	@POST
	@Path("/{userId}/{activity}/{section}/{name}")
	@Consumes("multipart/form-data")
	public void uploadTestFile(@MultipartForm FileUploadForm form, 
			@PathParam("userId") String userId, @PathParam("activity") String activity,
			@PathParam("section") String section, @PathParam("name") String name){
		if(name!=null){
			name = name.replace(" ", "_");
			service.addResource(form, userId, activity, section, name);
		}else
			service.addResource(form, userId, activity, section);
			
		//return Response.ok().build();
	}
}
