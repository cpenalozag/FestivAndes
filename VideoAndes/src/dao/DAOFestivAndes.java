/**-------------------------------------------------------------------
 * $Id$
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación
 *
 * Materia: Sistemas Transaccionales
 * Ejercicio: VideoAndes
 * Autor: Juan Felipe García - jf.garcia268@uniandes.edu.co
 * -------------------------------------------------------------------
 */
package dao;


import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import vos.*;

/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicación
 * @author Juan
 */
public class DAOFestivAndes {


	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Método constructor que crea DAOVFestivAndes
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOFestivAndes() {
		recursos = new ArrayList<Object>();
	}

	/**
	 * Método que cierra todos los recursos que estan en el arreglo de recursos
	 * <b>post: </b> Todos los recurso del arreglo de recursos han sido cerrados
	 */
	public void cerrarRecursos() {
		for(Object ob : recursos){
			if(ob instanceof PreparedStatement)
				try {
					((PreparedStatement) ob).close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		}
	}

	/**
	 * Método que inicializa la connection del DAO a la base de datos con la conexión que entra como parámetro.
	 * @param con  - connection a la base de datos
	 */
	public void setConn(Connection con){
		this.conn = con;
	}
	
	
	public Sitio2 darSitio(String nombre) throws SQLException, ParseException
	{
		Sitio2 busqueda = new Sitio2();
		String sql = "select * from((SELECT ID,NOMBRE,CAPACIDAD,TIPO AS IDTIPO FROM SITIO) "
				+ "NATURAL JOIN(SELECT ID AS IDTIPO, NOMBRE AS NOMTIPO FROM TIPOSITIO)) WHERE NOMBRE ='"+ nombre +"'";
		System.out.println("sql stm: " + sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		while (rs.next())
		{
			
			TipoSitio tipo = new TipoSitio();
			tipo.setId(Long.parseLong(rs.getString("IDTIPO")));
			tipo.setTipo(rs.getString("NOMTIPO"));
			busqueda.setId(Long.parseLong(rs.getString("ID")));
			busqueda.setNombre(rs.getString("NOMBRE"));
			busqueda.setCapacidad(Integer.parseInt(rs.getString("CAPACIDAD")));
			busqueda.setTipo(tipo);
			agregarLocalidadSitio(busqueda);
			agregarFuncion(busqueda);
		}

		return busqueda;
		
	}
	
	public void agregarLocalidadSitio(Sitio2 sit) throws SQLException
	{
		String idString = String.valueOf(sit.getId());
		String sql = "SELECT * FROM (select * from((select * from LOCALIDAD)natural join(select ID AS IDSITIO, NOMBRE AS NOMBRESITIO from SITIO))"
				+ "NATURAL JOIN(SELECT *FROM TIPOLOCALIDAD)) WHERE IDSITIO ='"+idString+"'";
		System.out.println("sql stm: " + sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		while (rs.next())
		{
			ArrayList<Localidad> localidades = new ArrayList<>();
			TipoLocalidad tipo = new TipoLocalidad();
			Localidad localidad = new Localidad();
			tipo.setId(Long.parseLong(rs.getString("ID")));
			tipo.setTipo(rs.getString("NOMBRE"));
			localidad.setId(Long.parseLong(rs.getString("ID")));
			localidad.setTipo(tipo);
			localidad.setSitio(rs.getString("NOMBRESITIO"));
			
			String numerada = rs.getString("NUMERADA");
			boolean boolnumerada = true;
			if(!numerada.equals("t"))
				boolnumerada = false;
			localidad.setNumerada(boolnumerada);
			localidad.setPrecio(Double.parseDouble(rs.getString("PRECIO")));
			localidad.setCapacidad(Integer.parseInt(rs.getString("CAPACIDAD")));
			System.err.println("hola perro "+ localidad.getId() + ", "+localidad.getCapacidad() + ", "+localidad.getTipo().getTipo());
			System.err.println("soy null??? "+ sit.getId());
			
//			if(sit.getLocalidades().isEmpty())
//			{
//				localidades.add(localidad);
//				sit.setLocalidades(localidades);
//			}
//			else
//			{
//				for(Localidad c: sit.getLocalidades()){
//					localidades.add(c);
//					localidades.add(localidad);
//				}
//			}
			sit.addLocalidad(localidad);			
		}
	}
	
	
	public void agregarFuncion(Sitio2 sit ) throws SQLException, ParseException
	{
		String idString = String.valueOf(sit.getId());
		String sql = "Select * from((select * from funcion)"
				+ "natural join"
				+ "(select ID AS IDESPECTACULO, NOMBRE AS NOMBREESP from espectaculo)) where IDSITIO='"+idString+"'";
		System.out.println("sql stm: " + sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		while(rs.next())
		{
			SimpleDateFormat formato = new SimpleDateFormat("MM/dd/yy");
			Date fecha = new Date();
			Funcion2 fun = new Funcion2();
			fecha = formato.parse(rs.getString("DIA"));
			Espectaculo esp = new  Espectaculo();
			esp.setId(Long.parseLong(rs.getString("IDESPECTACULO")));
			esp.setNombre(rs.getString("NOMBREESP"));
			fun.setDia(fecha);
			fun.setHora(Integer.parseInt(rs.getString("HORA")));
			fun.setId(Long.parseLong(rs.getString("ID")));
			fun.setSitio(sit.getNombre());
			fun.setEspectaculo(rs.getString("NOMBREESP"));
			
			sit.addFuncion(fun);
		}

	}
	
	
	public void agregarBoleteria(Sitio sit) throws SQLException, ParseException
	{
		String idString = String.valueOf(sit.getId());
		String sql = "SELECT * FROM BOLETA NATURAL JOIN BOLETA_SILLA "
				+ "NATURAL JOIN (SELECT ID AS IDFUNCION, IDESPECTACULO, DIA, HORA FROM FUNCION) "
				+ "NATURAL JOIN (SELECT ID AS IDESPECTACULO, NOMBRE, DURATION, IDIOMA, DESCRIPCION, COSTOREALIZACION, OTROIDIOMA, IDORGANIZADOR FROM ESPECTACULO) "
				+ " WHERE IDSITIO = '"+idString+"'";
		
		System.out.println("sql stm: " + sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		Iterator<Funcion> itr = sit.getFunciones().iterator();
		
		
		
		while(rs.next())
		{
			Boleta boleta = new Boleta();
			SimpleDateFormat formato = new SimpleDateFormat("MM/dd/yy");
			Date fecha = new Date();
			fecha = formato.parse(rs.getString("DIA"));
			Funcion fun = new Funcion();
			Espectaculo esp = new  Espectaculo();
			esp.setId(Long.parseLong(rs.getString("IDESPECTACULO")));
			esp.setNombre(rs.getString("NOMBRE"));
			fun.setDia(fecha);
			fun.setHora(Integer.parseInt(rs.getString("HORA")));
			fun.setId(Long.parseLong(rs.getString("ID")));
			fun.setSitio(sit);
			fun.setEspectaculo(esp);
			Localidad loca = new Localidad();
			loca.setId(Long.parseLong(rs.getString("IDLOCALIDAD")));
			Silla silla = new Silla();
			silla.setId(Long.parseLong(rs.getString("IDSILLA")));
			silla.setFila(rs.getString("FILA"));
			silla.setNumero(rs.getString("NUMERO"));
			boleta.setEspectaculo(esp);
			boleta.setFuncion(fun);
			boleta.setSilla(silla);
			boleta.setId(Long.parseLong(rs.getString("ID")));
			
		}
		
	}
	
}
