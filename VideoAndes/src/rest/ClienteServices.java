package rest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import javax.ws.rs.core.Response;

import oracle.jdbc.proxy.annotation.Post;
import tm.FestivAndesMaster;
import vos.Categoria;
import vos.CategoriaCambio;
import vos.Cliente;
import vos.CompraBoleta;
import vos.NotaDebito;
import vos.Recibo;

@Path("clientes")

public class ClienteServices {


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
	@Path("{idCliente}/preferencias")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	
	public Response agregarPreferencia(@PathParam("idCliente") Long idCliente, Categoria categoria)
	{
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		Cliente n;
		try{
			
			n= tm.agregarPreferencia(idCliente, categoria.getTipo());
		}catch (Exception e) {
			// TODO: handle exception
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(n).build();
	}
	@PUT
	@Path("{idCliente}/preferencias")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response modificarPreferencia(@PathParam("idCliente") Long idCliente, CategoriaCambio cateCamb)
	{
		Cliente n;
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		try{
			n =tm.modificarPreferencia(idCliente, cateCamb.getCategoriaAct(), cateCamb.getCategoriaNueva());
		}catch (Exception e) {
			// TODO: handle exception
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(n).build();
	}
	
	@DELETE
	@Path("{idCliente}/preferencias")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response borrarPreferencia(@PathParam("idCliente") Long idCliente, Categoria categoria)
	{
		Cliente n;
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		try{
			n = tm.borrarPreferencia(idCliente, categoria.getTipo());
		} catch (Exception e) {
			// TODO: handle exception
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(n).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response darClientes()
	{
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		List<Cliente> clientes;
		try{
			clientes = tm.darClientes();
		}catch (Exception e) {
			// TODO: handle exception
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(500).entity(clientes).build();
	}
	
	@POST
	@Path("{idCliente}/comprar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	
	public Response registrarCompra(@PathParam("idCliente") Long idCliente, CompraBoleta cb)
	{
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		Recibo r = new Recibo();
		try{
			
			r= tm.generarCompra(idCliente, cb.getIdFuncion(), cb.getIdEspectaculo(), cb.getIdSilla(), cb.getIdSitio(), "f");
		}catch (Exception e) {
			// TODO: handle exception
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(r).build();
	}
	
	@POST
	@Path("/comprar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	
	public Response registrarCompraNoCliente( CompraBoleta cb)
	{
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		Recibo r = new Recibo();
		try{
			
			r= tm.generarCompra(0, cb.getIdFuncion(), cb.getIdEspectaculo(), cb.getIdSilla(), cb.getIdSitio(), "f");
		}catch (Exception e) {
			// TODO: handle exception
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(r).build();
	}
	@POST
	@Path("{idCliente}/compraMultiple")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registrarCompraMultiple(@PathParam("idCliente") Long idCliente, CompraBoleta[]cbs)
	{
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		ArrayList<Recibo> rs = new ArrayList<>();
		try{
			rs = tm.registrarCompraMultiple(idCliente,cbs);
		}catch (Exception e) {
			// TODO: handle exception
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(500).entity(rs).build();
	}
	
	@POST
	@Path("{idCliente}/abonar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registrarAbono(@PathParam("idCliente")Long idCliente, CompraBoleta[] cbs){
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		ArrayList<Recibo> rs = new ArrayList<>();
		try{
			rs = tm.registrarAbono(idCliente,cbs);
		}catch (Exception e) {
			// TODO: handle exception
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(500).entity(rs).build();
	}
	
	@POST
	@Path("{idCliente}/devolver/{idBoleta}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response devolverBoleta(@PathParam("idCliente")Long idCliente, @PathParam("idBoleta")Long idBoleta){
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		NotaDebito nd = new NotaDebito();
		try{
			nd = tm.devolverBoleta(idCliente, idBoleta);
		}catch (Exception e) {
			// TODO: handle exception
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(500).entity(nd).build();
	}
	
	@POST
	@Path("{idCliente}/devolverAbono")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response devolverAbono(@PathParam("idCliente")Long idCliente){
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		ArrayList<NotaDebito> nd = new ArrayList<>();
		try{
			nd = tm.devolverAbono(idCliente);
		}catch (Exception e) {
			// TODO: handle exception
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(500).entity(nd).build();
	}
	
}
