package org.utn.proyecto.helpful.integrart.web.settings;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.utn.proyecto.helpful.integrart.core.fileresources.FileResourcePersister;
import org.utn.proyecto.helpful.integrart.core.fileresources.FileResourceService;
import org.utn.proyecto.helpful.integrart.web.settings.services.FileResourcePersisterProvider;
import org.utn.proyecto.helpful.integrart.web.settings.services.FileResourceServiceProvider;
import org.utn.proyecto.helpful.integrart.web.settings.services.TestFileUploadService;

import com.google.inject.name.Names;
import com.google.inject.servlet.ServletModule;

public class SettingsWebModule extends ServletModule {
	//private static final String SERVICE_ROOT = "/rest/*";

	@Override
	protected void configureServlets() {
		Properties properties = loadProperties("/configuration.properties");
		Names.bindProperties(binder(), properties);
		install(new ServletModule());
		this.bind(FileResourcePersister.class).toProvider(FileResourcePersisterProvider.class);
		this.bind(FileResourceService.class).toProvider(FileResourceServiceProvider.class);
		this.bind(TestFileUploadService.class);
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
