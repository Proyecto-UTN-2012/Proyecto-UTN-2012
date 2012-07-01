package org.utn.proyecto.helpful.integrart.web.settings.services;

import org.utn.proyecto.helpful.integrart.core.fileresources.DefaultFileResourceService;
import org.utn.proyecto.helpful.integrart.core.fileresources.FileResourcePersister;
import org.utn.proyecto.helpful.integrart.core.fileresources.FileResourceService;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class FileResourceServiceProvider implements Provider<FileResourceService> {
	private final FileResourcePersister persister;
	
	@Inject
	public FileResourceServiceProvider(FileResourcePersister persister){
		this.persister = persister;
	}

	public FileResourceService get() {
		return new DefaultFileResourceService(persister);
	}
}
