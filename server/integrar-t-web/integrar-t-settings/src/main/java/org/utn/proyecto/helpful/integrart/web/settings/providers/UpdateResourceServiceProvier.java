package org.utn.proyecto.helpful.integrart.web.settings.providers;

import org.utn.proyecto.helpful.integrart.core.fileresources.FileResourceService;
import org.utn.proyecto.helpful.integrart.core.percistence.PersisterService;
import org.utn.proyecto.helpful.integrart.web.settings.domain.ResourceType;
import org.utn.proyecto.helpful.integrart.web.settings.services.UpdateResourcesService;

import com.google.inject.Provider;

public abstract class UpdateResourceServiceProvier implements Provider<UpdateResourcesService> {
	private final FileResourceService fileService;
	private final PersisterService persisterService;
	
	public UpdateResourceServiceProvier(FileResourceService fileService, PersisterService persisterService){
		this.fileService = fileService;
		this.persisterService = persisterService;
	}
	public UpdateResourcesService get() {
		return new UpdateResourcesService(fileService, persisterService, getType());
	}
	
	abstract public ResourceType getType();

}
