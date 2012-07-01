package org.utn.proyecto.helpful.integrart.web.settings.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.utn.proyecto.helpful.integrart.core.fileresources.FileResourceService;

import com.google.inject.Inject;

@Path("/fileUpload")
public class TestFileUploadService {
	private final FileResourceService service;
	
	@Inject
	public TestFileUploadService(FileResourceService service){
		this.service = service;
	}
	
	@POST
	@Path("/{path}")
	@Consumes("multipart/form-data")
	public Response uploadTestFile(@MultipartForm FileUploadForm form, @PathParam("path") String path){
		//String path = "test";
		String name = "test.png";
		service.uploadFile(form, path, name);
		return Response.status(200).entity("uploadFile is called, Uploaded file: " + path + "/" + name).build();
	}
}
