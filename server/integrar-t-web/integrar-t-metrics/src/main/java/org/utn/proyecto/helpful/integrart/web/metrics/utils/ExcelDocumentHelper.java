package org.utn.proyecto.helpful.integrart.web.metrics.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelDocumentHelper {
	
	public static Workbook createDocument(){
		Workbook document = new HSSFWorkbook();
		return document;
	}
}
