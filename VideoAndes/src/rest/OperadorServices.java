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

@Path("operadores")
public class OperadorServices {

	@Context
	private ServletContext context;

	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}


	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response darOperadores()
	{
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		List<OperadorLogistico> operadores;
		try{
			operadores = tm.darOperadores();
		}catch (Exception e) {
			// TODO: handle exception
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(operadores).build();	
	}
	
	@Path("/{idUsuario}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response marcarComoRealizado(@PathParam("idUsuario") Long IdUsuario, Funcion2 pfuncion)
	{
	
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		Funcion2 fun = new Funcion2();
		String f = "t";
		try{
			
			fun = tm.marcarComoRealizado(IdUsuario, pfuncion.getId(),pfuncion.getEspectaculoId(), pfuncion.isRealizada());
		}catch (Exception e) {
			// TODO: handle exception
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(fun).build();
	}
	
	@Path("/{idUsuario}/rentabilidades")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response rentabilidadesCompanias(@PathParam("idUsuario") long idUsuario, RangoFechas rango)
	{
		ArrayList<RentabilidadCompanias> rentabilidades = new ArrayList<>();
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		try{
			
			rentabilidades = tm.generarReporteRentabilidadTodasCompanias(rango.getFechaInicial(), rango.getFechaFinal());
		}catch (Exception e) {
			// TODO: handle exception
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(rentabilidades).build();
	}
	
	@Path("/{idUsuario}/espectaculoPopular")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response marcarComoRealizado(@PathParam("idUsuario") long idUsuario, RangoFechas rango)
	{
		EspectaculoPopular popu = new EspectaculoPopular();
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		try{
			
			popu = tm.darEspectaculoPopular(rango.getFechaInicial(), rango.getFechaFinal());
		}catch (Exception e) {
			// TODO: handle exception
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(popu).build();
	}
	
}
