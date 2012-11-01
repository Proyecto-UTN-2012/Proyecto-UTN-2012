package org.utn.proyecto.helpful.integrart.web.metrics.utils;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelDocumentHelper {
	
	public static Workbook createDocument(){
		Workbook document = new HSSFWorkbook();
		return document;
	}
	
	public static Cell[] createTitles(Row row, String[] titles){
		Workbook wb = row.getSheet().getWorkbook();
		Cell[] cells = new Cell[titles.length];
		CellStyle style = wb.createCellStyle();
		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		Font font = wb.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(font);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		for(int i=0;i<titles.length;i++){
			Cell cell = row.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(titles[i]);
			cells[i] = cell;
		}
		return cells;
	}
}
