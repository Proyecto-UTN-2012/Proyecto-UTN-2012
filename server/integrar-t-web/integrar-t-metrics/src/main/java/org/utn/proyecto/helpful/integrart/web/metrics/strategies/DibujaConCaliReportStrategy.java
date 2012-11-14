package org.utn.proyecto.helpful.integrart.web.metrics.strategies;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.utn.proyecto.helpful.integrart.web.metrics.Metric;
import org.utn.proyecto.helpful.integrart.web.metrics.utils.ExcelDocumentHelper;

public class DibujaConCaliReportStrategy extends ActivityReportStrategy {

	@Override
	public Workbook build(List<Metric> metrics) {
		Workbook excel = ExcelDocumentHelper.createDocument();
		Sheet selectedSheet = excel.createSheet("Dibujos seleccionados");
		Sheet completedSheet = excel.createSheet("Dibujos completados");
		Sheet uncompletedSheet = excel.createSheet("Dibujos incompletos");
		Map<String, List<Metric>> map = groupByCategory(metrics);
		buildSheet(selectedSheet, map.get("dibuja_con_cali"));
		buildSheet(completedSheet, map.get("completo"));
		buildSheet(uncompletedSheet, map.get("incompleto"));
		
		return excel;
	}

	private void buildSheet(Sheet sheet,
			List<Metric> metrics) {
		Row row = sheet.createRow(0);
		ExcelDocumentHelper.createTitles(row, new String[]{"Usuario", "Dibujo", "Cantidad"});
		Map<String, List<Metric>> map = groupByUser(metrics);
		int index = 0;
		for(String userName : map.keySet()){
			Map<String, List<Metric>> categories = groupBySubcategory(0, map.get(userName));
			for(String category : categories.keySet()){
				addRow(sheet, ++index, userName, category, categories.get(category).size());
			}
		}
	}

	private void addRow(Sheet sheet, int index, String userName,
			String category, int value) {
		Row row = sheet.createRow(index);
		ExcelDocumentHelper.addCell(row, 0, userName);
		ExcelDocumentHelper.addCell(row, 1, category);
		ExcelDocumentHelper.addCell(row, 2, value);
		
	}
}
