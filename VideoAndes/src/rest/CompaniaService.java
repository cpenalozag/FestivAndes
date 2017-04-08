package rest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.FestivAndesMaster;
import vos.Cliente;
import vos.EspectaculoPopular;
import vos.Funcion;
import vos.Funcion2;
import vos.OperadorLogistico;
import vos.RangoFechas;
import vos.RentabilidadCompania;
import vos.RentabilidadCompanias;

@Path("companias")
public class CompaniaService {

	@Context
	private ServletContext context;

	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}


	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}
	
	@Path("/{idCompania}/rentabilidad")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response rentabilidadesCompania(@PathParam("idCompania") long idCompania, RangoFechas rango)
	{
		ArrayList<RentabilidadCompania> rentabilidad = new ArrayList<>();
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		try{
			
			rentabilidad = tm.generarReporteRentabilidadCompania(idCompania, rango);
		}catch (Exception e) {
			// TODO: handle exception
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(rentabilidad).build();
	}
}
