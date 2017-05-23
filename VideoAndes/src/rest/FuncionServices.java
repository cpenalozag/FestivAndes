package rest;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.FestivAndesMaster;
import vos.FiltrosConsultaFunciones;
import vos.Funcion;
import vos.Funcion2;
import vos.FuncionBasica;
import vos.ReporteFuncion;

@Path("funciones")
public class FuncionServices {

	@Context
	private ServletContext context;

	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}


	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}
	
	@POST
	@Path("/filtros")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response darFuncionesFiltro(FiltrosConsultaFunciones filtro){
		
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		List<FuncionBasica> funciones; 
		try{
			funciones = tm.darFuncionesFiltros(filtro);
		}catch (Exception e) {
			// TODO: handle exception
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(funciones).build();
	}
	
//	@GET
//	@Path("/idiomas")
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response darFuncionesIdioma(@QueryParam("idioma")String idioma)
//	{
//		FestivAndesMaster tm = new FestivAndesMaster(getPath());
//		List<Funcion2> funciones; 
//		try{
//			funciones = tm.darFuncionesIdioma(idioma);
//		}catch (Exception e) {
//			// TODO: handle exception
//			return Response.status(500).entity(doErrorMessage(e)).build();
//		}
//		return Response.status(200).entity(funciones).build();
//	}
//	
//	@GET
//	@Path("/companias")
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response darFuncionesCompania(@QueryParam("compania")String compania)
//	{
//		FestivAndesMaster tm = new FestivAndesMaster(getPath());
//		List<Funcion2> funciones; 
//		try{
//			funciones = tm.darFuncionesCompania(compania);
//		}catch (Exception e) {
//			// TODO: handle exception
//			return Response.status(500).entity(doErrorMessage(e)).build();
//		}
//		return Response.status(200).entity(funciones).build();
//	}
//	
//	
//	@GET
//	@Path("/categorias")
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response darFuncionesCategoria(@QueryParam("categoria") String categoria)
//	{
//		FestivAndesMaster tm = new FestivAndesMaster(getPath());
//		List<Funcion2> funciones; 
//		try{
//			funciones = tm.darFuncionesCategoria(categoria);
//		}catch (Exception e) {
//			// TODO: handle exception
//			return Response.status(500).entity(doErrorMessage(e)).build();
//		}
//		return Response.status(200).entity(funciones).build();
//	}
//	
//	@GET
//	@Path("/traduccion")
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response darFuncionesTraducidas(@QueryParam("tradu") Boolean tradu)
//	{
//		FestivAndesMaster tm = new FestivAndesMaster(getPath());
//		List<Funcion2> funciones; 
//		try{
//			funciones = tm.darFuncionesTraducidas(tradu);
//		}catch (Exception e) {
//			// TODO: handle exception
//			return Response.status(500).entity(doErrorMessage(e)).build();
//		}
//		return Response.status(200).entity(funciones).build();
//	}
	
	@GET
	@Path("{id}/reporte/{idFun}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response generarReporte(@PathParam("id") Long espectaculo, @PathParam("idFun") Long funcion)
	{
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		List<ReporteFuncion> reportes; 
		try{
			reportes = tm.generarReporteFuncion(funcion, espectaculo);
		}catch (Exception e) {
			// TODO: handle exception
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(reportes).build();
	}
	
	
	

}
