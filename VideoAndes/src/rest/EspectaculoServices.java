package rest;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.FestivAndesMaster;
import vos.ReporteEspectaculo;

@Path("espectaculos")
public class EspectaculoServices 
{
	@Context
	private ServletContext context;

	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}


	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}
	
	@GET
	@Path("{id}/reporte")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response generarReporte(@PathParam("id") Long espectaculo)
	{
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		List<ReporteEspectaculo> reportes; 
		try{
			reportes = tm.generarReporteEspectaculo(espectaculo);
		}catch (Exception e) {
			// TODO: handle exception
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(reportes).build();
	}
	
}
