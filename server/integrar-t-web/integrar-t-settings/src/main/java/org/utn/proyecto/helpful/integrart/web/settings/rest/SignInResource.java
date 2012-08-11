package org.utn.proyecto.helpful.integrart.web.settings.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.utn.proyecto.helpful.integrart.core.percistence.PersisterService;
import org.utn.proyecto.helpful.integrart.web.settings.domain.User;

import com.google.inject.Inject;

@Path("/signIn")
public class SignInResource {
	private PersisterService service;
	
	@Inject
	public SignInResource(PersisterService service){
		this.service = service;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void signIn( User user){
		service.insert(user);
	}
}
