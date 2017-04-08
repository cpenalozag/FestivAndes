package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;
import com.sun.xml.internal.ws.wsdl.OperationDispatcher;


import vos.Espectaculo;
import vos.EspectaculoPopular;
import vos.Funcion;
import vos.Funcion2;
import vos.OperadorLogistico;
import vos.RentabilidadCompanias;

public class DAOOperador {

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
	public DAOOperador() {
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

	public Funcion2 marcarComoRealizado(long idOperario, long  idFuncion, long idEsp, boolean isreali) throws Exception
	{
		String sql = "select * from operador where IDUSUARIO ='"+idOperario+"' and IDFUNCION = '"+idFuncion+"' and IDESPECTACULO = '"+idEsp+"'";
		System.out.println("slq stm: "+ sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		Funcion2 fun = new Funcion2();
		String str = "t";
		if(!isreali)
			str = "f";
		
		if(rs.next())
		{
			String sql2 = "update funcion set REALIZADA ='"+str+"' where ID ='"+idFuncion+"' and IDESPECTACULO = '"+idEsp+"'";
			System.out.println("slq stm: "+ sql2);
			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			prepStmt2.executeQuery();

			String sql3= "select * from("+
					"(select * from funcion)natural join"+
					"(select ID AS IDESPECTACULO, espectaculo.NOMBRE from espectaculo)) WHERE ID ='"+idFuncion+ "' AND IDESPECTACULO = '"+idEsp+"'"; 

			System.out.println("slq stm: "+ sql3);
			PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
			recursos.add(prepStmt3);
			ResultSet rs3 = prepStmt3.executeQuery();

			while (rs3.next())
			{

				Espectaculo es = new Espectaculo();
				es.setId(Long.parseLong(rs3.getString("IDESPECTACULO")));

				fun.setId(Long.parseLong(rs3.getString("ID")));
				fun.setEspectaculo(rs3.getString("NOMBRE"));
				fun.setDia(new Date (rs3.getString("DIA")));
				fun.setHora(Integer.parseInt(rs3.getString("HORA")));
				String sql4 = "SELECT NOMBRE FROM SITIO where ID = '"+rs3.getString("IDSITIO")+"'";
				System.out.println("slq stm: "+ sql4);
				PreparedStatement prepStmt4 = conn.prepareStatement(sql4);
				recursos.add(prepStmt4);
				ResultSet rs4 = prepStmt4.executeQuery();
				if(rs4.next()){
				fun.setSitio(rs4.getString("NOMBRE"));}
				boolean bool = false;
				if(rs3.getString("REALIZADA").equals("t"))
					bool = true;
				fun.setRealizada(bool);

			}

		}
		else
			throw new Exception("El usuario no maneja la funci�n dada");

		return fun;

	}

	public ArrayList<OperadorLogistico> darOperadores() throws SQLException
	{
		ArrayList<OperadorLogistico> operadores = new ArrayList<>();
		String sql = "select * from operador";
		String sqlAlt = "select * from((SELECT * FROM((select * from operador) "
				+ "NATURAL JOIN (SELECT ID AS IDUSUARIO, NOMBRE,CORREO, ROL FROM USUARIOS))) "
				+ "natural join (select ID AS IDESPECTACULO, NOMBRE AS NOMBREESP FROM ESPECTACULO))";
		PreparedStatement prepStmt = conn.prepareStatement(sqlAlt);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		while (rs.next())
		{
			OperadorLogistico operador = new OperadorLogistico();
			operador.setId(Long.parseLong(rs.getString("IDUSUARIO")));
			ArrayList<Funcion2> funciones = new ArrayList<Funcion2>();
			operador.setCorreo(rs.getString("CORREO"));
			operador.setNombre(rs.getString("NOMBRE"));
			
			Funcion2 fun = new Funcion2();
			fun.setId(Long.parseLong(rs.getString("IDFUNCION")));
			fun.setEspectaculo(rs.getString("NOMBREESP"));
			funciones.add(fun);
			operador.setFunciones(funciones);	
			operadores.add(operador);
		}
		return operadores;

	}

	public ArrayList<RentabilidadCompanias> generarReporte(String fechaIni, String fechaFin) throws SQLException{
		ArrayList<RentabilidadCompanias> reportes = new ArrayList<>();
		String sql = "WITH B AS (SELECT * FROM SITIO si INNER JOIN TIPOSITIO ti ON si.ID =ti.ID  "
				+ "INNER JOIN (SELECT ID AS IDFUNCION, IDESPECTACULO, IDSITIO AS ID, DIA FROM FUNCION) fun "
				+ "ON si.ID=fun.ID  NATURAL JOIN (SELECT ID AS IDBOLETA, IDFUNCION, IDESPECTACULO FROM BOLETA)  "
				+ "NATURAL JOIN (SELECT ID AS IDBOLETA, IDSITIO, IDSILLA FROM BOLETA_SILLA) NATURAL JOIN (SELECT ID AS IDSILLA, IDSITIO, IDLOCALIDAD FROM SILLA) NATURAL JOIN (SELECT ID AS IDLOCALIDAD, IDSITIO, PRECIO FROM LOCALIDAD))  , BONO1 AS(SELECT DIA,IDSITIO,TIPO, IDCOMPANIA, IDESPECTACULO, IDFUNCION, COUNT (IDFUNCION) AS CANTFUNCION, CAPACIDAD, SUM (PRECIO) AS VENDIDO, CATEGORIA FROM B NATURAL JOIN (SELECT ID AS IDESPECTACULO, TIPO AS CATEGORIA FROM CATEGORIA)  NATURAL JOIN COMPANIA_ESPECTACULO GROUP BY  DIA, IDSITIO,TIPO, IDCOMPANIA, IDESPECTACULO, IDFUNCION, CAPACIDAD, CATEGORIA) , FIN AS (SELECT IDCOMPANIA, DIA, IDSITIO, TIPO, IDESPECTACULO, CATEGORIA, IDFUNCION, CANTFUNCION, CANTFUNCION/CAPACIDAD, VENDIDO FROM BONO1 WHERE DIA BETWEEN TO_DATE('"+fechaIni+"', 'mm/dd/yy') AND TO_DATE('"+fechaFin+"', 'mm/dd/yy') ORDER BY VENDIDO DESC) SELECT * FROM FIN LEFT JOIN (SELECT ID AS IDESPECTACULO, NOMBRE FROM ESPECTACULO) esp ON FIN.IDESPECTACULO=ESP.IDESPECTACULO";
		
		PreparedStatement ps = conn.prepareStatement(sql);
		recursos.add(ps);
		ResultSet rs = ps.executeQuery();

		while(rs.next()){
			RentabilidadCompanias reporte = new RentabilidadCompanias();
			reporte.setIdCompania(Integer.parseInt(rs.getString("IDCOMPANIA")));
			reporte.setCantidadBoletas(Integer.parseInt(rs.getString("CANTFUNCION")));
			reporte.setCategoria(rs.getString("CATEGORIA"));
			reporte.setFecha(rs.getString("DIA"));
			reporte.setIdEspectaculo(Integer.parseInt(rs.getString("IDESPECTACULO")));
			reporte.setIdFuncion(Integer.parseInt(rs.getString("IDFUNCION")));
			reporte.setIdsitio(Integer.parseInt(rs.getString("IDSITIO")));
			reporte.setProporcionAsistencia(Double.parseDouble(rs.getString("CANTFUNCION/CAPACIDAD")));
			reporte.setTipositio(Integer.parseInt(rs.getString("TIPO")));
			reporte.setTotalVendido(Double.parseDouble(rs.getString("VENDIDO")));
			reportes.add(reporte);
			
		}
		return reportes;
	}
	
	public EspectaculoPopular espectaculoMasPopular(String fechaInicio, String fechaFin) throws SQLException{
		EspectaculoPopular espectaculo = new EspectaculoPopular();
		String sql = "WITH BONO2 AS (SELECT * FROM SITIO si INNER JOIN TIPOSITIO ti ON si.ID =ti.ID "
				+ "INNER JOIN (SELECT ID AS IDFUNCION, IDESPECTACULO, IDSITIO AS ID, DIA FROM FUNCION) fun ON si.ID=fun.ID "
				+ "NATURAL JOIN (SELECT ID AS IDBOLETA, IDFUNCION, IDESPECTACULO FROM BOLETA) "
				+ "NATURAL JOIN (SELECT ID AS IDBOLETA, IDSITIO, IDSILLA FROM BOLETA_SILLA) "
				+ "NATURAL JOIN (SELECT ID AS IDSILLA, IDSITIO, IDLOCALIDAD FROM SILLA) "
				+ "NATURAL JOIN (SELECT ID AS IDLOCALIDAD, IDSITIO, PRECIO FROM LOCALIDAD))"
				+ " , XYZ AS (SELECT IDESPECTACULO, DIA, COUNT(IDESPECTACULO) AS CANTIDAD "
				+ "FROM BONO2 GROUP BY IDESPECTACULO, DIA) SELECT * FROM XYZ "
				+ "INNER JOIN ESPECTACULO ON XYZ.IDESPECTACULO = ESPECTACULO.ID "
				+ "WHERE CANTIDAD = (SELECT MAX(CANTIDAD)FROM XYZ) AND DIA BETWEEN TO_DATE('"+fechaInicio+"', 'mm/dd/yy') AND TO_DATE('"+fechaFin+"', 'mm/dd/yy')"
				+ "ORDER BY CANTIDAD DESC";
		
		PreparedStatement ps = conn.prepareStatement(sql);
		recursos.add(ps);
		ResultSet rs = ps.executeQuery();

		if(rs.next()){
			espectaculo.setCantidadAsistentes(Integer.parseInt(rs.getString("CANTIDAD")));
			espectaculo.setCostoRealizacion(rs.getString("COSTOREALIZACION"));
			espectaculo.setDescripcion(rs.getString("DESCRIPCION"));
			espectaculo.setDuracion(Integer.parseInt(rs.getString("DURATION")));
			espectaculo.setFecha(rs.getString("DIA"));
			espectaculo.setId(Integer.parseInt(rs.getString("IDESPECTACULO")));
			espectaculo.setIdioma(rs.getString("IDIOMA"));
			espectaculo.setNombre(rs.getString("NOMBRE"));
			espectaculo.setIdOrganizador(Integer.parseInt(rs.getString("IDORGANIZADOR")));
			espectaculo.setOtroIdioma(false);
			if (rs.getString("OTROIDIOMA").equals("t"))espectaculo.setOtroIdioma(true);
			
		}
		return espectaculo;
	}
	
}
