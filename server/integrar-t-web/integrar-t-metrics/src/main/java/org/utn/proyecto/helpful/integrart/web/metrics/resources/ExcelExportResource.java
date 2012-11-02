package org.utn.proyecto.helpful.integrart.web.metrics.resources;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.apache.poi.ss.usermodel.Workbook;
import org.utn.proyecto.helpful.integrart.core.percistence.PersisterService;
import org.utn.proyecto.helpful.integrart.web.metrics.ActivityMetric;
import org.utn.proyecto.helpful.integrart.web.metrics.Metric;
import org.utn.proyecto.helpful.integrart.web.metrics.strategies.ActivityReportStrategy;
import org.utn.proyecto.helpful.integrart.web.metrics.strategies.HandPlayReportStrategy;
import org.utn.proyecto.helpful.integrart.web.metrics.strategies.PictogramReportStrategy;

import com.google.inject.Inject;

@Path("/report")
public class ExcelExportResource {
	private final PersisterService db;
	private static final Map<String, ActivityReportStrategy> strategies = new HashMap<String, ActivityReportStrategy>();
	private static final Map<String, ActivityMetric> types = new HashMap<String, ActivityMetric>();
	{
		strategies.put("hcd", new PictogramReportStrategy());
		strategies.put("jcm", new HandPlayReportStrategy());
		
		types.put("hcd", ActivityMetric.HABLA_CON_DIBUJO);
		types.put("jcm", ActivityMetric.JUGANDO_CON_LA_MANO);
	}
	
	@Inject
	public ExcelExportResource(PersisterService db){
		this.db = db;
	}
	@GET
	@Path("/{activity}")
	public Response excel(@PathParam("activity") String activity){
		File file = null;
		try {
			file = File.createTempFile("report", "xls");
			OutputStream out = new FileOutputStream(file);
			ActivityReportStrategy strategy = strategies.get(activity);
			List<Metric> metrics = findMetrics(activity);
			Workbook excel = strategy.build(metrics);
			excel.write(out);
			out.close();
			InputStream in = new BufferedInputStream(new FileInputStream(file));
			return Response.ok(in, "application/vnd.ms-excel").header("Content-Disposition", "attachment; filename="+activity+".xls").build();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}finally{
			if(file!=null){
				file.delete();
			}
		}
	}
	
	private List<Metric> findMetrics(String activity){
		Metric example = new Metric();
		example.setActivity(types.get(activity));
		return db.find(example, new String[]{"activity"});
	}
}
