package org.utn.proyecto.helpful.integrart.web.metrics.strategies;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.utn.proyecto.helpful.integrart.web.metrics.Metric;

public interface ActivityReportStrategy {
	public Workbook build(List<Metric> metrics);
}
