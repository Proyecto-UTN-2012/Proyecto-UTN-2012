package org.utn.proyecto.helpful.integrart.integrar_t_android.metrics;

import java.util.ArrayList;
import java.util.List;

import org.utn.proyecto.helpful.integrart.integrar_t_android.services.ComunicationService;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.ComunicationService.ExternalResourceType;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class MetricsService {
	private static final String METRICS = "metrics";
	private static final String METRICS_ARRAY = "metricList";
	
	private final ComunicationService comunicationService;
	
	@Inject
	public MetricsService(ComunicationService comunicationService){
		this.comunicationService = comunicationService;
	}
	
	public void sendMetric(Metric metric){
		comunicationService.sendMessage(ExternalResourceType.METRICS, METRICS, new Gson().toJson(metric));
	}

	public void sendMetrics(final Metric[] metrics){
		List<Metric> list = new ArrayList<Metric>();
		for(Metric metric : metrics){
			list.add(metric);
		}
		if(list.isEmpty()) return;
		//comunicationService.sendMessage(ExternalResourceType.METRICS, METRICS, new Gson().toJson(list.get(0)), new OnArrive(list));
		comunicationService.sendMessage(ExternalResourceType.METRICS, METRICS_ARRAY, new Gson().toJson(new MetricList(list)));
	}
	
	class OnArrive implements ComunicationService.OnArriveEmpty{
		private List<Metric> list;
		
		public OnArrive(List<Metric> list){
			this.list = list;
		}
		@Override
		public void onArrive() {
			list.remove(0);
			if(!list.isEmpty()){
				comunicationService.sendMessage(ExternalResourceType.METRICS, METRICS, new Gson().toJson(list.get(0)), new OnArrive(list));
			}
		}
		
	}
}
