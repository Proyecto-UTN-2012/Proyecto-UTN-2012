package org.utn.proyecto.helpful.integrart.web.metrics.strategies;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.utn.proyecto.helpful.integrart.web.metrics.Metric;
import org.utn.proyecto.helpful.integrart.web.metrics.utils.ExcelDocumentHelper;

public class ComoLoUsoReportStrategy extends ActivityReportStrategy {

	public Workbook build(List<Metric> metrics) {
		Workbook excel = ExcelDocumentHelper.createDocument();
		Sheet sheet = excel.createSheet("Metricas");
		Map<String, List<Metric>> map = groupByUser(metrics);
		Row row = sheet.createRow(0);
		ExcelDocumentHelper.createTitles(row, new String[]{"Usuario", "Video", "Cantidad"});
		
		int index = 0;
		for(String userName : map.keySet()){
			Map<String, List<Metric>> categories = groupByCategory(map.get(userName));
			for(String category : categories.keySet()){
				addRow(sheet, ++index, userName, category, categories.get(category).size());
			}
		}
		return excel;
	}

	private void addRow(Sheet sheet, int index, String userName,
			String category, int value) {
		Row row = sheet.createRow(index);
		ExcelDocumentHelper.addCell(row, 0, userName);
		ExcelDocumentHelper.addCell(row, 1, category);
		ExcelDocumentHelper.addCell(row, 2, value);
		
	}

}
