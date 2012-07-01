package org.utn.proyecto.helpful.integrart.web.settings.services;

import org.utn.proyecto.helpful.integrart.core.fileresources.FileResourcePersister;
import org.utn.proyecto.helpful.integrart.core.fileresources.LocalDriveFileResourcePersister;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

public class FileResourcePersisterProvider implements Provider<FileResourcePersister> {
	private final String basePath;
	
	@Inject
	public FileResourcePersisterProvider(@Named("static.path") String basePath){
		this.basePath = basePath;
	}
	
	public FileResourcePersister get() {
		return new LocalDriveFileResourcePersister(basePath);
	}

}
