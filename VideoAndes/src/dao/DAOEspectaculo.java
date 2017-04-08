package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.EspectaculoBasica;
import vos.FuncionBasica;
import vos.ReporteEspectaculo;
import vos.SitioBasica;

public class DAOEspectaculo {
	

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
	public DAOEspectaculo() {
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
	
	public FuncionBasica obtenerFuncion (Long idEs, Long idFun, Connection conn) throws SQLException {
		setConn(conn);
		FuncionBasica funcion = new FuncionBasica();
		String sql = "SELECT * FROM FUNCION WHERE ID ='"+idFun+"' AND IDESPECTACULO ='"+idEs+"'";
		PreparedStatement ps = conn.prepareStatement(sql);
		recursos.add(ps);
		ResultSet rs = ps.executeQuery();
		if (rs.next()){
			funcion.setDia(rs.getString("DIA"));
			funcion.setHora(Integer.parseInt(rs.getString("HORA")));
			funcion.setId(Long.parseLong(rs.getString("ID")));
			funcion.setEspectaculo(obtenerEspectaculo(idEs, conn));
			funcion.setSitio(obtenerSitio(Long.parseLong(rs.getString("IDSITIO")), conn));
		}
		return funcion;
	}

	public EspectaculoBasica obtenerEspectaculo(Long id, Connection conn) throws SQLException{
		setConn(conn);
		EspectaculoBasica esp = new EspectaculoBasica();
		String sql = "SELECT * FROM ESPECTACULO WHERE ID ='"+id+"'";
		PreparedStatement ps = conn.prepareStatement(sql);
		recursos.add(ps);
		ResultSet rs = ps.executeQuery();
		if (rs.next()){
			esp.setDescripcion(rs.getString("DESCRIPCION"));
			esp.setDuracion(Integer.parseInt(rs.getString("DURATION")));
			esp.setId(Long.parseLong(rs.getString("ID")));
			esp.setNombre(rs.getString("NOMBRE"));
			esp.setIdioma(rs.getString("IDIOMA"));
		}
		return esp;
	}
	
	public SitioBasica obtenerSitio(Long idSitio, Connection conn) throws SQLException{
		setConn(conn);
		SitioBasica sitio = new SitioBasica();
		String sql = "SELECT * FROM SITIO si "
				+ "INNER JOIN (SELECT ID, NOMBRE AS NOMTIPO FROM TIPOSITIO) ts ON si.TIPO = ts.ID WHERE si.ID = '"+idSitio+"'";
		PreparedStatement ps = conn.prepareStatement(sql);
		recursos.add(ps);
		ResultSet rs = ps.executeQuery();
		if (rs.next()){
			sitio.setId(idSitio);
			sitio.setCapacidad(Integer.parseInt(rs.getString("CAPACIDAD")));
			sitio.setNombre(rs.getString("NOMBRE"));
			sitio.setTipo(rs.getString("NOMTIPO"));
		}
		return sitio;
	}

	public ArrayList<ReporteEspectaculo> generarReporte(Long IDespectaculo) throws SQLException{
		ArrayList<ReporteEspectaculo> reportes = new ArrayList<>();
		String sql = "WITH Q AS("
				+ "SELECT NOMBRE, ESCLIENTE, IDFUNCION, IDSITIO, CAPACIDAD, COUNT(ID) AS CANTIDAD, SUM (PRECIO) AS PRODUCIDO "
				+ "FROM BOLETA NATURAL JOIN BOLETA_DETALLE NATURAL JOIN (SELECT ID, IDSITIO, IDSILLA FROM BOLETA_SILLA) "
				+ "NATURAL JOIN (SELECT ID AS IDSILLA, IDSITIO, IDLOCALIDAD FROM SILLA) "
				+ "NATURAL JOIN (SELECT ID AS IDLOCALIDAD, IDSITIO, PRECIO FROM LOCALIDAD) "
				+ "NATURAL JOIN (SELECT ID AS IDLOCALIDAD, NOMBRE FROM TIPOLOCALIDAD) "
				+ "NATURAL JOIN (SELECT ID AS IDSITIO, CAPACIDAD FROM SITIO) "
				+ "WHERE IDESPECTACULO = '"+IDespectaculo+"' "
				+ "GROUP BY IDFUNCION, IDSITIO, ESCLIENTE, NOMBRE, CAPACIDAD) "
				+ "SELECT NOMBRE, ESCLIENTE, IDFUNCION, IDSITIO, CANTIDAD, CANTIDAD/CAPACIDAD*100 AS PORCENTAJE, PRODUCIDO FROM Q";
		PreparedStatement ps = conn.prepareStatement(sql);
		recursos.add(ps);
		ResultSet rs = ps.executeQuery();

		while(rs.next()){
			ReporteEspectaculo reporte = new ReporteEspectaculo();
			reporte.setCantidadBoletas(Integer.parseInt(rs.getString("CANTIDAD")));
			reporte.setLocalidad(rs.getString("NOMBRE"));
			reporte.setProducido(Double.parseDouble(rs.getString("PRODUCIDO")));
			reporte.setSitio(obtenerSitio(Long.parseLong(rs.getString("IDSITIO")),conn));
			reporte.setPorcentajeOcupacion(Double.parseDouble(rs.getString("PORCENTAJE")));
			if (rs.getString("ESCLIENTE").equals("t")){
				reporte.setTipoComprador(ReporteEspectaculo.USUARIO);
			}
			else if (rs.getString("ESCLIENTE").equals("f")){
				reporte.setTipoComprador(ReporteEspectaculo.NO_USUARIO);
			}
			reportes.add(reporte);
		}
		return reportes;
	}
	
}
