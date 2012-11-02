package org.utn.proyecto.helpful.integrart.web.metrics.strategies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.utn.proyecto.helpful.integrart.web.metrics.Metric;
import org.utn.proyecto.helpful.integrart.web.metrics.utils.ExcelDocumentHelper;

public class PictogramReportStrategy implements ActivityReportStrategy {

	public Workbook build(List<Metric> metrics) {
		Workbook excel = ExcelDocumentHelper.createDocument();
		Sheet phraseSheet = excel.createSheet("Frases");
		Sheet timeSheet = excel.createSheet("Tiempos");
		
		buildPhrases(phraseSheet, metrics);
		buildTimes(timeSheet, metrics);
		
		return excel;
	}
	
	private void buildTimes(Sheet sheet, List<Metric> metrics) {
		Row titles = sheet.createRow(0);
		ExcelDocumentHelper.createTitles(titles, new String[]{"Usuario", "Nivel", "Frase","Tiempo"});
		Collections.sort(metrics, new Comparator<Metric>() {
			public int compare(Metric o1, Metric o2) {
				if(o1.getUser().getUserName().equals(o2.getUser().getUserName()))
					return o1.getSubcategories()[0].compareTo(o2.getSubcategories()[0]);
				return o1.getUser().getUserName().compareTo(o2.getUser().getUserName());
			}
		});
		for(int i=0;i<metrics.size();i++){
			Metric metric = metrics.get(i);
			addRow(i+1, sheet, metric.getUser().getUserName(), metric.getSubcategories()[0], metric.getValue(), metric.getSubcategories()[1]);
		}
	}

	private void buildPhrases(Sheet sheet, List<Metric> metrics){
		Row titles = sheet.createRow(0);
		ExcelDocumentHelper.createTitles(titles, new String[]{"Usuario", "Nivel", "Cantidad"});
		Collections.sort(metrics, new Comparator<Metric>() {
			public int compare(Metric o1, Metric o2) {
				if(o1.getUser().getUserName().equals(o2.getUser().getUserName()))
					return o1.getSubcategories()[0].compareTo(o2.getSubcategories()[0]);
				return o1.getUser().getUserName().compareTo(o2.getUser().getUserName());
			}
		});
		//int count = 1;
		int index = 0;
		Map<String, List<Metric>> map = new HashMap<String, List<Metric>>();
		for(Metric metric : metrics){
			List<Metric> list = map.get(metric.getSubcategories()[0] + metric.getUser().getUserName());
			if(list==null){
				list = new ArrayList<Metric>();
				map.put(metric.getSubcategories()[0] + metric.getUser().getUserName(), list);
			}
			list.add(metric);
		}
		for(String key : map.keySet()){
			Metric template = map.get(key).get(0);
			addRow(++index, sheet, template.getUser().getUserName(), template.getSubcategories()[0], map.get(key).size(), null);	
		}
		
	}
	
	private void addRow(int index, Sheet sheet, String userName, String level, int value, String phrase){
		Row row = sheet.createRow(index);
		ExcelDocumentHelper.addCell(row, 0, userName);
		ExcelDocumentHelper.addCell(row, 1, level);
		if(phrase!=null){		
			ExcelDocumentHelper.addCell(row, 2, phrase);
			ExcelDocumentHelper.addCellTime(sheet.getWorkbook(), row, 3, value);
		}
		else{
			ExcelDocumentHelper.addCell(row, 2, value);	
		}
	}

}
