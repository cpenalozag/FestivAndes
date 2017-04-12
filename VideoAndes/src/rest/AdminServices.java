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
import vos.Funcion2;
import vos.InformeAsistencia;
import vos.NotaDebito;

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
		return Response.status(500).entity(nd).build();
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
		System.out.println("hola carlos");
		return Response.status(500).entity(informe).build();
		
	}
}
