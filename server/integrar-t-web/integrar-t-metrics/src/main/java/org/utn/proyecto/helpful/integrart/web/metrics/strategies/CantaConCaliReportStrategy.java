package org.utn.proyecto.helpful.integrart.web.metrics.strategies;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.utn.proyecto.helpful.integrart.web.metrics.Metric;
import org.utn.proyecto.helpful.integrart.web.metrics.utils.ExcelDocumentHelper;

public class CantaConCaliReportStrategy extends ActivityReportStrategy {

	public Workbook build(List<Metric> metrics) {
		Workbook excel = ExcelDocumentHelper.createDocument();
		Sheet sheet = excel.createSheet("Canta con Cali");
		Row row = sheet.createRow(0);
		ExcelDocumentHelper.createTitles(row, new String[]{"Usuario", "Escuchó", "Cantidad"});
		int index = 0;
		Map<String, List<Metric>> map = groupByUser(metrics);
		for(String userName : map.keySet()){
			index = addMetrics(index, sheet, userName, map.get(userName));
		}
		
		return excel;
	}

	private int addMetrics(int index, Sheet sheet, String userName,List<Metric> metrics){
		List<Metric> no = new ArrayList<Metric>(metrics);
		List<Metric> yes = new ArrayList<Metric>(metrics);
		CollectionUtils.filter(no, new Predicate() {
			public boolean evaluate(Object object) {
				Metric metric = (Metric)object;
				return "canta_con_cali_no".equals(metric.getSubcategories()[0]);
			}
		});

		CollectionUtils.filter(yes, new Predicate() {
			public boolean evaluate(Object object) {
				Metric metric = (Metric)object;
				return "canta_con_cali_si".equals(metric.getSubcategories()[0]);
			}
		});
		
		Row row = sheet.createRow(++index);
		ExcelDocumentHelper.addCell(row, 0, userName);
		ExcelDocumentHelper.addCell(row, 1, "No");
		ExcelDocumentHelper.addCell(row, 2, no.size());

		row = sheet.createRow(++index);
		ExcelDocumentHelper.addCell(row, 0, userName);
		ExcelDocumentHelper.addCell(row, 1, "Si");
		ExcelDocumentHelper.addCell(row, 2, yes.size());
		return index;
	}

}
