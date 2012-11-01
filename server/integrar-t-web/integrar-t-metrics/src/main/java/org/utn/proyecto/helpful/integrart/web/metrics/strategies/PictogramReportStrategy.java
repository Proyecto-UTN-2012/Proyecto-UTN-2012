package org.utn.proyecto.helpful.integrart.web.metrics.strategies;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
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
			addRow(i+1, sheet, metric.getUser().getUserName(), metric.getSubcategories()[0], metric.getValue(), true);
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
		String category = null;
		String userName = null;
		int count = 1;
		int index = 1;
		for(Metric metric : metrics){
			if(metric.getSubcategories()[0].equals(category) && metric.getUser().getUserName().equals(userName)){
				count++;
			}
			else{
				category = metric.getSubcategories()[0];
				userName = metric.getUser().getUserName();
				addRow(index, sheet, userName, category, count, false);
				count = 1;
				index++;
			}
		}
		
	}
	
	private void addRow(int index, Sheet sheet, String userName, String level, int value, boolean time){
		Row row = sheet.createRow(index);
		Cell cell = row.createCell(0);
		cell.setCellValue(userName);
		
		cell = row.createCell(1);
		cell.setCellValue(level);
		
		cell = row.createCell(2);
		if(time){
			CreationHelper createHelper = sheet.getWorkbook().getCreationHelper();
			CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
			cellStyle.setDataFormat(
					createHelper.createDataFormat().getFormat("h:mm:ss"));
			cell.setCellStyle(cellStyle);
			Calendar calendar = (Calendar)Calendar.getInstance().clone();
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, value/1000);
			cell.setCellValue(calendar.getTime());
			return;
		}
		cell.setCellValue(value);
	}

}
