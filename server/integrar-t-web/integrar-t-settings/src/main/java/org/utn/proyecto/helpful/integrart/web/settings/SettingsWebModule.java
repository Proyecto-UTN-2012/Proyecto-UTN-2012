package org.utn.proyecto.helpful.integrart.web.settings;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.utn.proyecto.helpful.integrart.core.fileresources.FileResourcePersister;
import org.utn.proyecto.helpful.integrart.core.fileresources.FileResourceService;
import org.utn.proyecto.helpful.integrart.core.percistence.PersisterService;
import org.utn.proyecto.helpful.integrart.web.settings.providers.FileResourcePersisterProvider;
import org.utn.proyecto.helpful.integrart.web.settings.providers.FileResourceServiceProvider;
import org.utn.proyecto.helpful.integrart.web.settings.providers.MongoDBProvider;
import org.utn.proyecto.helpful.integrart.web.settings.providers.UpdateImageResourceServiceProvider;
import org.utn.proyecto.helpful.integrart.web.settings.providers.UpdateSoundResourceServiceProvider;
import org.utn.proyecto.helpful.integrart.web.settings.rest.CalendarResource;
import org.utn.proyecto.helpful.integrart.web.settings.rest.CantaActivityResource;
import org.utn.proyecto.helpful.integrart.web.settings.rest.ImageFileUploadResource;
import org.utn.proyecto.helpful.integrart.web.settings.rest.PictogramResource;
import org.utn.proyecto.helpful.integrart.web.settings.rest.SignInResource;
import org.utn.proyecto.helpful.integrart.web.settings.rest.SoundFileUploadResource;
import org.utn.proyecto.helpful.integrart.web.settings.rest.TestFileUploadResource;
import org.utn.proyecto.helpful.integrart.web.settings.rest.UpdateRequestResource;
import org.utn.proyecto.helpful.integrart.web.settings.services.UpdateResourcesService;

import com.google.inject.name.Names;
import com.google.inject.servlet.ServletModule;

public class SettingsWebModule extends ServletModule {
	//private static final String SERVICE_ROOT = "/rest/*";

	@Override
	protected void configureServlets() {
		Properties properties = loadProperties("/configuration.properties");
		Names.bindProperties(binder(), properties);
		install(new ServletModule());
		
		//File Services
		this.bind(FileResourcePersister.class).toProvider(FileResourcePersisterProvider.class);
		this.bind(FileResourceService.class).toProvider(FileResourceServiceProvider.class);
		
		//PersisterService
		this.bind(PersisterService.class).toProvider(MongoDBProvider.class);
		
		//Update Resources Service
		this.bind(UpdateResourcesService.class).annotatedWith(Names.named("updateImageResourceService"))
		.toProvider(UpdateImageResourceServiceProvider.class);
		this.bind(UpdateResourcesService.class).annotatedWith(Names.named("updateSoundResourceService"))
		.toProvider(UpdateSoundResourceServiceProvider.class);
		
		//REST Resources
		this.bind(ImageFileUploadResource.class);
		this.bind(SoundFileUploadResource.class);
		this.bind(UpdateRequestResource.class);
		this.bind(TestFileUploadResource.class);
		this.bind(SignInResource.class);
		this.bind(PictogramResource.class);
		this.bind(CalendarResource.class);
		this.bind(CantaActivityResource.class);
		//this.bind(FileUploadResource.class);
		//filter(SERVICE_ROOT).through(PerformanceFilter.class);
	}
	
	private static Properties loadProperties(String name){
		Properties props = new Properties();
		InputStream is = new Object(){}
							.getClass()
							.getEnclosingClass()
							.getResourceAsStream(name);
		try{
			props.load(is);
		}catch(IOException e){
			throw new RuntimeException(e);
		} finally {
			if(is != null){
				try{
					is.close();
				}catch(IOException e){}
			}
		}
		return props;
	}
}
