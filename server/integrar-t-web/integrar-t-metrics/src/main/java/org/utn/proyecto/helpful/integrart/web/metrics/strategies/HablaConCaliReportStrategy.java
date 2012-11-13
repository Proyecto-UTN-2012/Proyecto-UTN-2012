package org.utn.proyecto.helpful.integrart.web.metrics.strategies;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.utn.proyecto.helpful.integrart.web.metrics.Metric;
import org.utn.proyecto.helpful.integrart.web.metrics.utils.ExcelDocumentHelper;

public class HablaConCaliReportStrategy extends ActivityReportStrategy {

	@Override
	public Workbook build(List<Metric> metrics) {
		Workbook excel = ExcelDocumentHelper.createDocument();
		Sheet sheet = excel.createSheet("Metricas");
		Row row = sheet.createRow(0);
		ExcelDocumentHelper.createTitles(row, new String[]{"Usuario", "Gritos"});
		int index = 0;
		Map<String, List<Metric>> map = groupByUser(metrics);
		for(String userName : map.keySet()){
			addRow(sheet, ++index, userName, map.get(userName).size());
		}
		
		return excel;
	}
	private void addRow(Sheet sheet, int index, String userName, int value) {
		Row row = sheet.createRow(index);
		ExcelDocumentHelper.addCell(row, 0, userName);
		ExcelDocumentHelper.addCell(row, 1, value);
		
	}
}
