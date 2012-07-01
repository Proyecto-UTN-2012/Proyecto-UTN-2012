package org.utn.proyecto.helpful.integrart.web.metrics;

import com.google.inject.servlet.ServletModule;

public class MetricsWebModule extends ServletModule {
	@Override
	protected void configureServlets() {
		install(new ServletModule());
		//filter(SERVICE_ROOT).through(PerformanceFilter.class);
	}
}
