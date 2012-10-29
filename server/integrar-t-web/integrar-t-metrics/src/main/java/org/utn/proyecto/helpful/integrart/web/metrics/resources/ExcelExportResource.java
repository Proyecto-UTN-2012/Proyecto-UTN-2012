package org.utn.proyecto.helpful.integrart.web.metrics.resources;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.utn.proyecto.helpful.integrart.web.metrics.utils.ExcelDocumentHelper;

import com.google.inject.Inject;

@Path("/report")
public class ExcelExportResource {
	private final ExcelDocumentHelper helper;
	
	@Inject
	public ExcelExportResource(ExcelDocumentHelper helper){
		this.helper = helper;
	}
	@GET
	public Response excel(){
		File file = null;
		try {
			file = File.createTempFile("report", "xls");
			OutputStream out = new FileOutputStream(file);
			Workbook excel = ExcelDocumentHelper.createDocument();
			Sheet sheet = excel.createSheet("Reporte");
			excel.write(out);
			out.close();
		
			InputStream in = new BufferedInputStream(new FileInputStream(file));
			return Response.ok(in, "application/vnd.ms-excel").header("Content-Disposition", "attachment; filename=resumen.xls").build();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}finally{
			if(file!=null){
				file.delete();
			}
		}
	}
}
