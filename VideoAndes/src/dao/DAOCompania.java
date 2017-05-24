package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import vos.RentabilidadCompania;

public class DAOCompania {

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
	public DAOCompania() {
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


	public ArrayList<RentabilidadCompania> generarReporte(String nombreCompania,String fechaIni, String fechaFin) throws SQLException{
		ArrayList<RentabilidadCompania> reportes = new ArrayList<RentabilidadCompania>();

		String sql2 = "select id from companias where nombre = '"+nombreCompania+"'";
		PreparedStatement ps = conn.prepareStatement(sql2);
		recursos.add(ps);
		ResultSet rs = ps.executeQuery();
		if (rs.next()){
			int idCompania = Integer.parseInt(rs.getString("ID"));


			String sql = "WITH B AS (SELECT * FROM SITIO si INNER JOIN TIPOSITIO ti ON si.ID =ti.ID "
					+ "INNER JOIN (SELECT ID AS IDFUNCION, IDESPECTACULO, IDSITIO AS ID, DIA FROM FUNCION) fun ON si.ID=fun.ID "
					+ "NATURAL JOIN (SELECT ID AS IDBOLETA, IDFUNCION, IDESPECTACULO FROM BOLETA WHERE ASISTENCIA ='t') "
					+ "NATURAL JOIN (SELECT ID AS IDBOLETA, IDSITIO, IDSILLA FROM BOLETA_SILLA) "
					+ "NATURAL JOIN (SELECT ID AS IDSILLA, IDSITIO, IDLOCALIDAD FROM SILLA) "
					+ "NATURAL JOIN (SELECT ID AS IDLOCALIDAD, IDSITIO, PRECIO FROM LOCALIDAD)) , "
					+ "BONO1 AS(SELECT DIA,IDSITIO,TIPO, IDCOMPANIA, IDESPECTACULO, IDFUNCION, COUNT (IDFUNCION) AS CANTFUNCION, CAPACIDAD, SUM (PRECIO) AS VENDIDO, CATEGORIA "
					+ "FROM B NATURAL JOIN (SELECT ID AS IDESPECTACULO, TIPO AS CATEGORIA FROM CATEGORIA) "
					+ "NATURAL JOIN COMPANIA_ESPECTACULO GROUP BY  DIA, IDSITIO,TIPO, IDCOMPANIA, IDESPECTACULO, IDFUNCION, CAPACIDAD, CATEGORIA)"
					+ " , FIN AS (SELECT DIA, IDSITIO, TIPO, IDESPECTACULO, CATEGORIA, IDFUNCION, CANTFUNCION, CANTFUNCION/CAPACIDAD, VENDIDO "
					+ "FROM BONO1 WHERE IDCOMPANIA ='"+idCompania+"' AND DIA BETWEEN TO_DATE('"+fechaIni+"', 'mm/dd/yy') "
					+ "AND TO_DATE('"+fechaFin+"', 'mm/dd/yy') "
					+ "ORDER BY VENDIDO DESC) "
					+ "SELECT * FROM FIN "
					+ "LEFT JOIN (SELECT ID AS IDESPECTACULO, NOMBRE FROM ESPECTACULO) esp "
					+ "ON FIN.IDESPECTACULO=ESP.IDESPECTACULO";
			System.out.println("sql stm: " + sql);
			PreparedStatement ps1 = conn.prepareStatement(sql);

			recursos.add(ps1);
			ResultSet rs1 = ps1.executeQuery();
			while(rs1.next()){
				RentabilidadCompania reporte = new RentabilidadCompania();
				reporte.setCantidadBoletas(Integer.parseInt(rs1.getString("CANTFUNCION")));
				reporte.setCategoria(rs1.getString("CATEGORIA"));
				reporte.setFecha(rs1.getString("DIA"));
				reporte.setIdEspectaculo(Integer.parseInt(rs1.getString("IDESPECTACULO")));
				reporte.setIdFuncion(Integer.parseInt(rs1.getString("IDFUNCION")));
				reporte.setIdsitio(Integer.parseInt(rs1.getString("IDSITIO")));
				reporte.setProporcionAsistencia(Double.parseDouble(rs1.getString("CANTFUNCION/CAPACIDAD")));
				reporte.setTipositio(Integer.parseInt(rs1.getString("TIPO")));
				reporte.setTotalVendido(Double.parseDouble(rs1.getString("VENDIDO")));
				reportes.add(reporte);
			}
		}
		return reportes;
	}


}
