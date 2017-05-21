package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vos.Espectaculo;
import vos.FiltrosConsultaFunciones;
import vos.Funcion;
import vos.Funcion2;
import vos.ReporteFuncion;
import vos.Sitio;

public class DAOFuncion {
	
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
	public DAOFuncion() {
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
	
	public List<Funcion2> darFuncionesFiltros(FiltrosConsultaFunciones filtro) throws SQLException, ParseException{
		List<Funcion2> funciones = new ArrayList<>();
		if(filtro.getIdioma() != null){
			funciones = darFuncionesIdioma(filtro.getIdioma());
		}else if (filtro.getCategoria() != null){
			funciones = darFuncionesCategoria(filtro.getCategoria());
		}else if(filtro.getNombreCompania() != null){
			funciones = darFuncionesCompania(filtro.getNombreCompania());
		}else if (filtro.getFechaInicial()!= null && filtro.getFechaFinal()!= null){
			funciones = darFuncionesFecha(filtro.getFechaInicial(), filtro.getFechaFinal());
		}
		
		return funciones;
		
	}

	public List<Funcion2> darFuncionesCompania(String nombreCompania) throws SQLException, ParseException
	{
		List<Funcion2> funciones = new ArrayList<Funcion2>();
		String sql = "SELECT * FROM("
				+ "(SELECT * FROM ("
				+ "(select IDESPECTACULO, NOMBRECOM, NOMBREESP from( "
				+ "(select * from("
				+ "( select * from COMPANIA_ESPECTACULO) NATURAL JOIN  "
				+ "(SELECT ID AS IDESPECTACULO, NOMBRE AS NOMBREESP FROM ESPECTACULO))) "
				+ " NATURAL JOIN"
				+ "(select ID AS IDCOMPANIA, NOMBRE AS NOMBRECOM from COMPANIAS))) NATURAL JOIN "
				+ "(SELECT * FROM FUNCION))) NATURAL JOIN"
				+ "(SELECT ID AS IDSITIO, NOMBRE AS NOMBRESITIO FROM SITIO))where NOMBRECOM ='"+ nombreCompania+"'";

		System.out.println("slq stm: "+ sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while(rs.next())
		{
			Funcion2 n = new Funcion2();
			Espectaculo esp = new Espectaculo();
			Sitio sit = new Sitio();
			SimpleDateFormat formato = new SimpleDateFormat("MM/dd/yy");
			Date fecha = new Date();
//			sit.setId(Long.parseLong(rs.getString("IDSITIO")));
//			sit.setNombre(rs.getString("NOMBRESITIO"));
//			esp.setId(Long.parseLong(rs.getString("IDESPECTACULO")));
//			esp.setNombre(rs.getString("NOMBREESP"));
			n.setId(Long.parseLong(rs.getString("ID")));
			n.setEspectaculo(rs.getString("NOMBREESP"));
			n.setEspectaculoId(Long.parseLong(rs.getString("IDESPECTACULO")));
			n.setSitio(rs.getString("NOMBRESITIO"));
			fecha = formato.parse(rs.getString("DIA"));
			n.setDia(fecha);
			n.setHora(Integer.parseInt(rs.getString("HORA")));
			
			String sql2 = "select IDUSUARIO FROM operador where IDFUNCION = '"+ rs.getString("ID")+ "' AND IDESPECTACULO ='" +rs.getString("IDESPECTACULO")+"'";
			System.out.println("slq stm: "+ sql2);
			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			ResultSet rs2 = prepStmt2.executeQuery();
			if(rs2.next()){
				n.setOperadorLogico(Long.parseLong(rs2.getString("IDUSUARIO")));
				}
			funciones.add(n);
		}
		return funciones;

	}

	public List<Funcion2> darFuncionesIdioma(String idioma) throws SQLException, ParseException
	{
		List<Funcion2> funciones = new ArrayList<Funcion2>();
		String sql = "SELECT ID,DIA,HORA,NOMBREESPC,IDIOMA,NOMBRELUGAR, IDSITIO, IDESPECTACULO FROM("
				+ "(SELECT * FROM("
				+ "(select * from FUNCION ) "
				+ "NATURAL JOIN"
				+ "(SELECT ID AS IDESPECTACULO, NOMBRE AS NOMBREESPC, IDIOMA FROM ESPECTACULO))) NATURAL JOIN"
				+ "(SELECT ID AS IDSITIO, NOMBRE AS NOMBRELUGAR FROM SITIO))where IDIOMA = '"+ idioma +"'";
		System.out.println("sql stm: " + sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while(rs.next())
		{
			Funcion2 n = new Funcion2();
			Espectaculo esp = new Espectaculo();
			Sitio sit = new Sitio();
			SimpleDateFormat formato = new SimpleDateFormat("MM/dd/yy");
			Date fecha = new Date();
//			sit.setId(Long.parseLong(rs.getString("IDSITIO")));
//			sit.setNombre(rs.getString("NOMBRELUGAR"));
//			esp.setId(Long.parseLong(rs.getString("IDESPECTACULO")));
//			esp.setNombre(rs.getString("NOMBREESPC"));
			n.setId(Long.parseLong(rs.getString("ID")));
			n.setEspectaculo(rs.getString("NOMBREESPC"));
			n.setSitio(rs.getString("NOMBRELUGAR"));
			fecha = formato.parse(rs.getString("DIA"));
			n.setDia(fecha);
			n.setHora(Integer.parseInt(rs.getString("HORA")));
			
			String sql2 = "select IDUSUARIO FROM operador where IDFUNCION = '"+ rs.getString("ID")+ "' AND IDESPECTACULO ='" +rs.getString("IDESPECTACULO")+"'";
			System.out.println("slq stm: "+ sql2);
			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			ResultSet rs2 = prepStmt2.executeQuery();
			if(rs2.next()){
				n.setOperadorLogico(Long.parseLong(rs2.getString("IDUSUARIO")));
				}
			funciones.add(n);
		}
		return funciones;
	}

	public List<Funcion2> darFuncionesFecha(String fechaInicial, String fechaFinal) throws SQLException, ParseException
	{
//		SimpleDateFormat formato = new SimpleDateFormat("dd/mm/yy");
//		String fechaString = formato.format(fecha);
		List<Funcion2> funciones = new ArrayList<Funcion2>();
//		System.out.println(fechaString);
		String sql ="SELECT ID,DIA,HORA,NOMBREESPC,IDIOMA,NOMBRELUGAR,IDSITIO,IDESPECTACULO FROM"
				+ "((SELECT * FROM((select * from FUNCION ) NATURAL JOIN(SELECT ID AS IDESPECTACULO, NOMBRE AS NOMBREESPC, "
				+ "IDIOMA FROM ESPECTACULO))) NATURAL JOIN(SELECT ID AS IDSITIO, NOMBRE AS NOMBRELUGAR FROM SITIO)) "
				+ "where DIA between TO_DATE('"+fechaInicial+"', 'dd/mm/yy') and TO_DATE('"+fechaFinal+"', 'dd/mm/yy')";

		System.out.println("sql stm: " + sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while(rs.next())
		{
			Funcion2 n = new Funcion2();
			Espectaculo esp = new Espectaculo();
			Sitio sit = new Sitio();
			Date fecha2 = new Date();
//			sit.setId(Long.parseLong(rs.getString("IDSITIO")));
//			sit.setNombre(rs.getString("NOMBRELUGAR"));
//			esp.setId(Long.parseLong(rs.getString("IDESPECTACULO")));
//			esp.setNombre(rs.getString("NOMBREESPC"));
			n.setId(Long.parseLong(rs.getString("ID")));
			n.setEspectaculo(rs.getString("NOMBREESPC"));
			n.setSitio(rs.getString("NOMBRELUGAR"));
//			fecha2 = formato.parse(rs.getString("DIA"));
			n.setDia(fecha2);
			n.setHora(Integer.parseInt(rs.getString("HORA")));
			String sql2 = "select IDUSUARIO FROM operador where IDFUNCION = '"+ rs.getString("ID")+ "' AND IDESPECTACULO ='" +rs.getString("IDESPECTACULO")+"'";
			System.out.println("slq stm: "+ sql2);
			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			ResultSet rs2 = prepStmt2.executeQuery();
			if(rs2.next()){
				n.setOperadorLogico(Long.parseLong(rs2.getString("IDUSUARIO")));
				}
			funciones.add(n);
		}
		return funciones;
	}
	public List<Funcion2> darFuncionesCategoria(String categoria) throws SQLException, ParseException
	{
		List<Funcion2> funciones = new ArrayList<Funcion2>();
		String sql = "SELECT * FROM("
				+ "SELECT* FROM("
				+ "SELECT * FROM("
				+ "SELECT * FROM("
				+ "(SELECT ID AS IDESPECTACULO, NOMBRE AS NOMBREESP FROM ESPECTACULO)"
				+ "NATURAL JOIN"
				+ "(SELECT * FROM ESPECTACULO_CATEGORIA))"
				+ "NATURAL JOIN (SELECT * FROM FUNCION)) NATURAL JOIN"
				+ "(SELECT ID AS IDSITIO, NOMBRE AS NOMBRESITIO FROM SITIO))NATURAL JOIN"
				+ "(SELECT ID AS IDCATEGORIA, TIPO AS NOMBRECATEGORIA FROM CATEGORIA))where NOMBRECATEGORIA ='"+categoria+"'";	
		
		System.out.println("sql stm: " + sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		while(rs.next())
		{
			Funcion2 n = new Funcion2();
			Espectaculo esp = new Espectaculo();
			Sitio sit = new Sitio();
			SimpleDateFormat formato = new SimpleDateFormat("MM/dd/yy");
			Date fecha = new Date();
//			sit.setId(Long.parseLong(rs.getString("IDSITIO")));
//			sit.setNombre(rs.getString("NOMBRESITIO"));
//			esp.setId(Long.parseLong(rs.getString("IDESPECTACULO")));
//			esp.setNombre(rs.getString("NOMBREESP"));
			n.setId(Long.parseLong(rs.getString("ID")));
			n.setEspectaculo(rs.getString("NOMBREESP"));
			n.setSitio(rs.getString("NOMBRESITIO"));
			fecha = formato.parse(rs.getString("DIA"));
			n.setDia(fecha);
			n.setHora(Integer.parseInt(rs.getString("HORA")));
			String sql2 = "select IDUSUARIO FROM operador where IDFUNCION = '"+ rs.getString("ID")+ "' AND IDESPECTACULO ='" +rs.getString("IDESPECTACULO")+"'";
			System.out.println("slq stm: "+ sql2);
			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			ResultSet rs2 = prepStmt2.executeQuery();
			if(rs2.next()){
				n.setOperadorLogico(Long.parseLong(rs2.getString("IDUSUARIO")));
				}
			funciones.add(n);
		}
		return funciones;
	}
	
	public List<Funcion2> darFuncionesTraducidas(Boolean tradu) throws SQLException, ParseException
	{
		String resp = "";
		if(tradu)
			resp = "t";
		else
			resp = "f";
		List<Funcion2> funciones = new ArrayList<Funcion2>();
		String sql = "SELECT ID, DIA, HORA, OTROIDIOMA, NOMBREESPC, NOMBRESITIO, IDSITIO, IDESPECTACULO FROM (SELECT * FROM ("
				+ "(SELECT * FROM FUNCION)"
				+ "NATURAL JOIN  (SELECT ID AS IDESPECTACULO, OTROIDIOMA, NOMBRE AS NOMBREESPC FROM ESPECTACULO))"
				+ "NATURAL JOIN"
				+ "(SELECT  ID AS IDSITIO, NOMBRE NOMBRESITIO FROM SITIO))where OTROIDIOMA ='"+ resp +"'";
		System.out.println("sql stm: " + sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		while(rs.next())
		{
			Funcion2 n = new Funcion2();
			Espectaculo esp = new Espectaculo();
			Sitio sit = new Sitio();
			SimpleDateFormat formato = new SimpleDateFormat("MM/dd/yy");
			Date fecha = new Date();
			sit.setId(Long.parseLong(rs.getString("IDSITIO")));
			sit.setNombre(rs.getString("NOMBRESITIO"));
			esp.setId(Long.parseLong(rs.getString("IDESPECTACULO")));
			esp.setNombre(rs.getString("NOMBREESPC"));
			n.setId(Long.parseLong(rs.getString("ID")));
			n.setEspectaculo(rs.getString("NOMBREESPC"));
			n.setSitio(rs.getString("NOMBRESITIO"));
			fecha = formato.parse(rs.getString("DIA"));
			n.setDia(fecha);
			n.setHora(Integer.parseInt(rs.getString("HORA")));
			String sql2 = "select IDUSUARIO FROM operador where IDFUNCION = '"+ rs.getString("ID")+ "' AND IDESPECTACULO ='" +rs.getString("IDESPECTACULO")+"'";
			System.out.println("slq stm: "+ sql2);
			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			ResultSet rs2 = prepStmt2.executeQuery();
			if(rs2.next()){
			n.setOperadorLogico(Long.parseLong(rs2.getString("IDUSUARIO")));
			}

			funciones.add(n);
		}
		return funciones;
	}
	
	public ArrayList<ReporteFuncion> generarReporte(Long idFuncion, Long idEspectaculo) throws SQLException{
		ArrayList<ReporteFuncion> reportes = new ArrayList<>();
		String sql = "SELECT NOMBRE AS LOCALIDAD,ESCLIENTE,COUNT(IDFUNCION) AS CANTIDAD,SUM(PRECIO) AS PRODUCIDO FROM BOLETA "
				+ "NATURAL JOIN BOLETA_DETALLE NATURAL JOIN BOLETA_SILLA bs "
				+ "INNER JOIN SILLA si ON bs.IDSILLA=si.ID AND bs.IDSITIO=si.IDSITIO "
				+ "INNER JOIN LOCALIDAD lo ON si.IDLOCALIDAD=lo.ID AND bs.IDSITIO=lo.IDSITIO "
				+ "INNER JOIN TIPOLOCALIDAD tl ON lo.ID=tl.ID "
				+ "WHERE IDFUNCION='1' AND IDESPECTACULO = '1' "
				+ "GROUP BY NOMBRE,ESCLIENTE";
		
		PreparedStatement ps = conn.prepareStatement(sql);
		recursos.add(ps);
		ResultSet rs = ps.executeQuery();

		while(rs.next()){
			ReporteFuncion reporte = new ReporteFuncion();
			reporte.setCantidadBoletas(Integer.parseInt(rs.getString("CANTIDAD")));
			reporte.setLocalidad(rs.getString("LOCALIDAD"));
			reporte.setProducido(Double.parseDouble(rs.getString("PRODUCIDO")));
			if (rs.getString("ESCLIENTE").equals("f")){
				reporte.setTipoComprador(ReporteFuncion.NO_USUARIO);
			}
			else{
				reporte.setTipoComprador(ReporteFuncion.USUARIO);
			}
			reportes.add(reporte);
		}
		return reportes;
	}
	
}
