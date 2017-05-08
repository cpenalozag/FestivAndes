package rest;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.FestivAndesMaster;
import vos.Asistencia;
import vos.BoletaConsulta;
import vos.ClienteBueno;
import vos.FiltrosBoletas;
import vos.Funcion2;
import vos.InformeAsistencia;
import vos.NotaDebito;
import vos.UsuarioReporte;

@Path("administradores")
public class AdminServices {

	/**
	 * Atributo que usa la anotación @Context para tener el ServletContext de la conexión actual.
	 */
	@Context
	private ServletContext context;

	/**
	 * Método que retorna el path de la carpeta WEB-INF/ConnectionData en el deploy actual dentro del servidor.
	 * @return path de la carpeta WEB-INF/ConnectionData en el deploy actual.
	 */
	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}
	
	
	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}
	
	@POST
	@Path("{idUsuario}/cancelarFuncion")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response cancelarFuncion(@PathParam("idUsuario")Long idCliente, Funcion2 funcion){
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		ArrayList<NotaDebito> nd = new ArrayList<>();
		try{
			nd = tm.cancelarFuncion(funcion.getId(), funcion.getEspectaculoId());
		}catch (Exception e) {
			// TODO: handle exception
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(nd).build();
	}
	@GET
	@Path("{idUsuario}/informeAsistencia/{idCliente}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response darInformeAsistencia(@PathParam("idCliente")Long idCliente)
	{
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		InformeAsistencia informe = new InformeAsistencia();
		try{
			informe = tm.darInformeAsistencia(idCliente);
		}catch (Exception e) {
			// TODO: handle exception
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(informe).build();
		
	}
	
	@GET
	@Path("{idUsuario}/clientesBuenos/{numBoletas}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response darClientesBuenos(@PathParam("numBoletas")int numBoletas)
	{
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		ArrayList<ClienteBueno> clientes = new ArrayList<>();
		try{
			clientes = tm.clientesBuenos(numBoletas);
		}catch (Exception e) {
			// TODO: handle exception
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(clientes).build();
		
	}
	
	@POST
	@Path("{idUsuario}/consultaBoletas")
	@Produces(MediaType.APPLICATION_JSON)
	public Response darConsultaBoletas(FiltrosBoletas filtros)
	{
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		ArrayList<BoletaConsulta> consultas = new ArrayList<>();
		try{
			consultas = tm.consultaBoletas(filtros);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(System.out);
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(consultas).build();
		
	}
	
	@POST
	@Path("{idUsuario}/asistenciaCompania")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response asistenciaCOmpania(Asistencia asistencia){
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		ArrayList<UsuarioReporte> us = new ArrayList<>();
		try{
			us = tm.darConsultaAsistencia(asistencia.getNombreCompania(), asistencia.getFechaInicio(), asistencia.getFechaFin(), asistencia.getAsistencia(), asistencia.getOrderBy());
		}catch(Exception e ){
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(us).build();
	}
}
