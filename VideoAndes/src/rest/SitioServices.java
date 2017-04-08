package rest;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import tm.FestivAndesMaster;
import vos.Sitio;
import vos.Sitio2;

@Path("sitios")
public class SitioServices {
	

	@Context
	private ServletContext context;

	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}


	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}
	
	@GET
	@Path("{nombre}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response darFuncionesIdioma(@PathParam("nombre")String nombre)
	{
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		Sitio2 sitio; 
		try{
			sitio = tm.darSitio(nombre);
		}catch (Exception e) {
			// TODO: handle exception
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(sitio).build();
	}

}
