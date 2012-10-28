package org.utn.proyecto.helpful.integrart.integrar_t_android.metrics;

import org.utn.proyecto.helpful.integrart.integrar_t_android.services.ComunicationService;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.ComunicationService.ExternalResourceType;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class MetricsService {
	private static final String METRICS = "metrics";
	
	private final ComunicationService comunicationService;
	
	@Inject
	public MetricsService(ComunicationService comunicationService){
		this.comunicationService = comunicationService;
	}
	
	public void sendMetric(Metric metric){
		comunicationService.sendMessage(ExternalResourceType.METRICS, METRICS, new Gson().toJson(metric));
	}
}
