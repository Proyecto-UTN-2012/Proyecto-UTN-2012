package org.utn.proyecto.helpful.integrart.web.settings.providers;

import org.utn.proyecto.helpful.integrart.core.fileresources.FileResourceService;
import org.utn.proyecto.helpful.integrart.core.percistence.PersisterService;
import org.utn.proyecto.helpful.integrart.web.settings.domain.ResourceType;

import com.google.inject.Inject;

public class UpdateSoundResourceServiceProvider extends
		UpdateResourceServiceProvier {

	@Inject
	public UpdateSoundResourceServiceProvider(FileResourceService fileService,
			PersisterService persisterService) {
		super(fileService, persisterService);
	}

	@Override
	public ResourceType getType() {
		return ResourceType.SOUND;
	}

}
