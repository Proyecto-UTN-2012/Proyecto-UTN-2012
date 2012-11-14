package org.utn.proyecto.helpful.integrart.web.metrics.strategies;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.utn.proyecto.helpful.integrart.web.metrics.Metric;
import org.utn.proyecto.helpful.integrart.web.metrics.utils.ExcelDocumentHelper;

public class OrganizarTReportStrategy extends ActivityReportStrategy {

	public Workbook build(List<Metric> metrics) {
		Workbook excel = ExcelDocumentHelper.createDocument();
		Sheet initSheet = excel.createSheet("Diferencia de inicio");
		Sheet durationSheet = excel.createSheet("Duración");
		Sheet realDurationSheet = excel.createSheet("Duración real");
		Sheet diferenceSheet = excel.createSheet("Diferencia");
		Sheet completedSheet = excel.createSheet("Completadas");
		Sheet notCompletedSheet = excel.createSheet("No Completadas");
		
		buildinit(initSheet, new ArrayList<Metric>(metrics));
		buildDurations(durationSheet, new ArrayList<Metric>(metrics));
		buildRealDurations(realDurationSheet, new ArrayList<Metric>(metrics));
		buildDiference(diferenceSheet, new ArrayList<Metric>(metrics));
		buildCompleted(completedSheet, new ArrayList<Metric>(metrics));
		buildNotCompleted(notCompletedSheet, new ArrayList<Metric>(metrics));
		
		return excel;
	}

	private void buildinit(Sheet sheet, List<Metric> metrics) {
		Row titles = sheet.createRow(0);
		ExcelDocumentHelper.createTitles(titles, new String[]{"Usuario", "Tarea", "Diferencia en minutos"});
		CollectionUtils.filter(metrics, new Predicate() {
			public boolean evaluate(Object object) {
				Metric metric = (Metric)object;
				return "diferencia de inicio".equals(metric.getCategory());
			}
		});
		for(int i=0;i<metrics.size();i++){
			addRow(i+1, sheet, metrics.get(i).getUser().getUserName(), metrics.get(i).getSubcategories()[0], metrics.get(i).getValue()/(1000*60));
		}
	}

	private void buildDurations(Sheet sheet, List<Metric> metrics) {
		Row titles = sheet.createRow(0);
		ExcelDocumentHelper.createTitles(titles, new String[]{"Usuario", "Tarea", "Duración en minutos"});
		CollectionUtils.filter(metrics, new Predicate() {
			public boolean evaluate(Object object) {
				Metric metric = (Metric)object;
				return "duracion".equals(metric.getCategory());
			}
		});
		for(int i=0;i<metrics.size();i++){
			addRow(i+1, sheet, metrics.get(i).getUser().getUserName(), metrics.get(i).getSubcategories()[0], metrics.get(i).getValue()/(1000*60));
		}
	}

	private void buildRealDurations(Sheet sheet, List<Metric> metrics) {
		Row titles = sheet.createRow(0);
		ExcelDocumentHelper.createTitles(titles, new String[]{"Usuario", "Tarea", "Duración real en minutos"});
		CollectionUtils.filter(metrics, new Predicate() {
			public boolean evaluate(Object object) {
				Metric metric = (Metric)object;
				return "duracion real".equals(metric.getCategory());
			}
		});
		for(int i=0;i<metrics.size();i++){
			addRow(i+1, sheet, metrics.get(i).getUser().getUserName(), metrics.get(i).getSubcategories()[0], metrics.get(i).getValue()/(1000*60));
		}
	}

	private void buildDiference(Sheet sheet, List<Metric> metrics) {
		Row titles = sheet.createRow(0);
		ExcelDocumentHelper.createTitles(titles, new String[]{"Usuario", "Tarea", "Diferencia en minutos"});
		CollectionUtils.filter(metrics, new Predicate() {
			public boolean evaluate(Object object) {
				Metric metric = (Metric)object;
				return "diferencia".equals(metric.getCategory());
			}
		});
		for(int i=0;i<metrics.size();i++){
			addRow(i+1, sheet, metrics.get(i).getUser().getUserName(), metrics.get(i).getSubcategories()[0], metrics.get(i).getValue()/(1000*60));
		}
	}
	private void buildCompleted(Sheet sheet, List<Metric> metrics) {
		Row titles = sheet.createRow(0);
		ExcelDocumentHelper.createTitles(titles, new String[]{"Usuario", "Día", "Tareas"});
		CollectionUtils.filter(metrics, new Predicate() {
			public boolean evaluate(Object object) {
				Metric metric = (Metric)object;
				return "Completadas".equals(metric.getCategory());
			}
		});
		for(int i=0;i<metrics.size();i++){
			addRow(i+1, sheet, metrics.get(i).getUser().getUserName(), metrics.get(i).getSubcategories()[0], metrics.get(i).getValue());
		}
	}
	private void buildNotCompleted(Sheet sheet, List<Metric> metrics) {
		Row titles = sheet.createRow(0);
		ExcelDocumentHelper.createTitles(titles, new String[]{"Usuario", "Día", "Tareas"});
		CollectionUtils.filter(metrics, new Predicate() {
			public boolean evaluate(Object object) {
				Metric metric = (Metric)object;
				return "No Completadas".equals(metric.getCategory());
			}
		});
		for(int i=0;i<metrics.size();i++){
			addRow(i+1, sheet, metrics.get(i).getUser().getUserName(), metrics.get(i).getSubcategories()[0], metrics.get(i).getValue());
		}
	}
	
	private void addRow(int index, Sheet sheet, String userName, String task, int value){
		Row row = sheet.createRow(index);
		ExcelDocumentHelper.addCell(row, 0, userName);
		ExcelDocumentHelper.addCell(row, 1, task);
		ExcelDocumentHelper.addCell(row, 2, value);
	}

}
