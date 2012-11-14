package org.utn.proyecto.helpful.integrart.web.metrics.strategies;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.utn.proyecto.helpful.integrart.web.metrics.Metric;
import org.utn.proyecto.helpful.integrart.web.metrics.utils.ExcelDocumentHelper;

public class HandPlayReportStrategy extends ActivityReportStrategy {

	public Workbook build(List<Metric> metrics) {
		Workbook excel = ExcelDocumentHelper.createDocument();
		Sheet sheet = excel.createSheet("Tiempos");
		buildTimes(sheet, metrics);
		return excel;
	}

	private void buildTimes(Sheet sheet, List<Metric> metrics) {
		Row titles = sheet.createRow(0);
		ExcelDocumentHelper.createTitles(titles, new String[]{"Usuario", "Nivel", "Tiempo"});
		Collections.sort(metrics, new Comparator<Metric>() {
			public int compare(Metric o1, Metric o2) {
				if(o1.getUser().getUserName().equals(o2.getUser().getUserName()))
					return o1.getSubcategories()[0].compareTo(o2.getSubcategories()[0]);
				return o1.getUser().getUserName().compareTo(o2.getUser().getUserName());
			}
		});
		for(int i=0;i<metrics.size();i++){
			Metric metric = metrics.get(i);
			addRow(i+1, sheet, metric.getUser().getUserName(), metric.getSubcategories()[0], metric.getValue());
		}
	}
	
	private void addRow(int index, Sheet sheet, String userName, String level, int value){
		Row row = sheet.createRow(index);
		ExcelDocumentHelper.addCell(row, 0, userName);
		ExcelDocumentHelper.addCell(row, 1, level);
		ExcelDocumentHelper.addCellTime(sheet.getWorkbook(), row, 2, value);	
	}

}
