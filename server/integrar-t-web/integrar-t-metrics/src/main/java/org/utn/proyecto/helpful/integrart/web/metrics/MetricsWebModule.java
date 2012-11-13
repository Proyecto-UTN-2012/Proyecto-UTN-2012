package org.utn.proyecto.helpful.integrart.web.metrics;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.utn.proyecto.helpful.integrart.core.percistence.PersisterService;
import org.utn.proyecto.helpful.integrart.web.metrics.providers.MongoDBProvider;
import org.utn.proyecto.helpful.integrart.web.metrics.resources.ExcelExportResource;
import org.utn.proyecto.helpful.integrart.web.metrics.resources.MetricsListResource;
import org.utn.proyecto.helpful.integrart.web.metrics.resources.MetricsResource;
import org.utn.proyecto.helpful.integrart.web.metrics.utils.ExcelDocumentHelper;

import com.google.inject.name.Names;
import com.google.inject.servlet.ServletModule;

public class MetricsWebModule extends ServletModule {
	@Override
	protected void configureServlets() {
		Properties properties = loadProperties("/configuration.properties");
		Names.bindProperties(binder(), properties);
		install(new ServletModule());
		
		//Services
		this.bind(PersisterService.class).toProvider(MongoDBProvider.class);
		this.bind(ExcelDocumentHelper.class);
		
		//Resources
		this.bind(MetricsResource.class);
		this.bind(MetricsListResource.class);
		this.bind(ExcelExportResource.class);
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
