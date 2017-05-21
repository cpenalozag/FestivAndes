package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

import vos.BoletaConsulta;
import vos.ClienteBueno;
import vos.FiltrosBoletas;
import vos.NotaDebito;
import vos.UsuarioReporte;

public class DAOAdministrador {
	

	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;
	
	private DAOCliente daoCli = new DAOCliente();

	/**
	 * Método constructor que crea DAOVFestivAndes
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOAdministrador() {
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
	
	
	public String cancelarCompania(String nombre) throws SQLException{
		String respuesta =" se cancelo la companiassssss";
		String sql = "select id from companias where nombre = '"+nombre+"'";
		PreparedStatement ps = conn.prepareStatement(sql);
		System.out.println("sql stm: " + sql);
		recursos.add(ps);
		ResultSet rs = ps.executeQuery();
		String idCompania = rs.getString("ID");
		
		String sql2 = "select * from (funcion natural join compania_espectaculo)where idcompania = '"+idCompania+"'";
		PreparedStatement ps2 = conn.prepareStatement(sql2);
		System.out.println("sql2 stm: " + sql2);
		recursos.add(ps2);
		ResultSet rs2 = ps2.executeQuery();
		while(rs2.next()){
			String idEspectaculo = rs2.getString("IDESPECTACULO");
			String sql3 = "update boleta set cancelada = 't' where idespectaculo = '"+idEspectaculo+"'";
			PreparedStatement ps3 = conn.prepareStatement(sql3);
			System.out.println("sql2 stm: " + sql3);
			recursos.add(ps3);
			ps3.executeQuery();
		}
		return respuesta;
		
	}
	public ArrayList<NotaDebito> cancelarFuncion(Long idFuncion, Long idEspectaculo) throws Exception{
		ArrayList<NotaDebito> devoluciones = new ArrayList<>();
		String  sql = "select * from ((select * from (select * from boleta)"
				+ "natural join(select * from boleta_detalle))"
				+ "natural join(select * from boleta_silla))where IDFUNCION ='"+idFuncion+"' and IDESPECTACULO ='"+ idEspectaculo+"' and IDCLIENTE IS NOT NULL";
		PreparedStatement ps = conn.prepareStatement(sql);
		System.out.println("sql stm: " + sql);
		recursos.add(ps);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			
			Long idboleta = Long.parseLong(rs.getString("ID"));
			Long idCliente = Long.parseLong(rs.getString("IDCLIENTE"));
			daoCli.setConn(conn);
			devoluciones.add(daoCli.devolverBoleta(idboleta,idCliente));
		}
		String sql2 = "update funcion set cancelada = 't' where ID = 1 AND IDESPECTACULO =1";
		PreparedStatement ps2 = conn.prepareStatement(sql2);
		System.out.println("sql stm: " + sql2);
		recursos.add(ps2);

		return devoluciones;	
	}

	public ArrayList<ClienteBueno> clientesBuenos(int n) throws Exception{
		ArrayList<ClienteBueno> clientes = new ArrayList<>();
		String  sql = "WITH SIRVEN AS (SELECT DISTINCT IDCLIENTE "
				+ "FROM (BOLETA_SILLA NATURAL JOIN (SELECT ID AS IDSILLA, IDSITIO, IDLOCALIDAD FROM SILLA WHERE IDLOCALIDAD=1) "
				+ "NATURAL JOIN BOLETA_DETALLE) GROUP BY IDCLIENTE "
				+ "MINUS "
				+ "SELECT DISTINCT IDCLIENTE "
				+ "FROM (BOLETA_SILLA "
				+ "NATURAL JOIN (SELECT ID AS IDSILLA, IDSITIO, IDLOCALIDAD "
				+ "FROM SILLA WHERE IDLOCALIDAD<>1) "
				+ "NATURAL JOIN BOLETA_DETALLE) ) "
				+ "SELECT COUNT(ID), ID, NOMBRE, CORREO FROM USUARIOS "
				+ "NATURAL JOIN (SELECT IDCLIENTE AS ID FROM SIRVEN) "
				+ "NATURAL JOIN (SELECT ID AS IDBOLETA, IDCLIENTE AS ID FROM BOLETA_DETALLE) "
				+ "GROUP BY ID, NOMBRE, CORREO HAVING COUNT(ID)>="+n;
		PreparedStatement ps = conn.prepareStatement(sql);
		System.out.println("sql stm: " + sql);
		recursos.add(ps);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			ClienteBueno cliente = new ClienteBueno();
			int numeroBoletas = Integer.parseInt(rs.getString("COUNT(ID)"));
			Long idCliente = Long.parseLong(rs.getString("ID"));
			String nombre = rs.getString("NOMBRE");
			String correo = rs.getString("CORREO");
			cliente.setBoletasCompradas(numeroBoletas);
			cliente.setCorreo(correo);
			cliente.setId(idCliente);
			cliente.setNombre(nombre);
			clientes.add(cliente);
		}
		return clientes;	
	}
	
	public ArrayList<BoletaConsulta> consultarCompraBoletas(FiltrosBoletas filtros) throws Exception{
		ArrayList<BoletaConsulta> consultas = new ArrayList<>();
		String  sql = "SELECT NOMBRE, IDSITIO, DIA,COUNT(ID) AS NUMVENDIDAS, COUNT(CASE WHEN ESCLIENTE = 't' THEN 1 END) AS NUMCLIENTES "
				+ "FROM (SELECT ID AS IDFUNCION, IDESPECTACULO, DIA,TO_CHAR(TO_DATE(DIA), 'FmDay') AS DIASEMANA, HORA "
				+ "FROM FUNCION WHERE TO_CHAR(TO_DATE(DIA), 'FmDay')='"+filtros.getDiaSemana()+"' "
				+ "AND DIA BETWEEN TO_DATE('"+filtros.getFechaInicial()+"') AND TO_DATE ('"+filtros.getFechaFinal()+"') "
				+ "AND HORA BETWEEN "+filtros.getHoraInicial()+" AND "+filtros.getHoraFinal()+") NATURAL JOIN (SELECT ID, IDESPECTACULO, IDFUNCION "
				+ "FROM BOLETA) NATURAL JOIN BOLETA_SILLA "
				+ "NATURAL JOIN (SELECT ID AS IDSILLA, IDSITIO, IDLOCALIDAD "
				+ "FROM SILLA WHERE IDLOCALIDAD = "+filtros.getTipoLocalidad()+") "
				+ "NATURAL JOIN (SELECT ID AS IDESPECTACULO, NOMBRE FROM ESPECTACULO) "
				+ "NATURAL JOIN (SELECT * FROM ELEMENTOEXTRA_ESPECTACULO WHERE IDELEMENTOEXTRA="+filtros.getIdElementoExtra()+") "
				+ "NATURAL JOIN (SELECT ID, ESCLIENTE FROM BOLETA_DETALLE) GROUP BY NOMBRE,IDSITIO,DIA";
		PreparedStatement ps = conn.prepareStatement(sql);
		System.out.println("sql stm: " + sql);
		recursos.add(ps);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			BoletaConsulta consulta = new BoletaConsulta();
			
			String nombre = rs.getString("NOMBRE");
			int idSitio = rs.getInt("IDSITIO");
			String dia = rs.getString("DIA");
			int numVendidas = rs.getInt("NUMVENDIDAS");
			int numClientes = rs.getInt("NUMCLIENTES");
			consulta.setEspectaculo(nombre);
			consulta.setDia(dia);
			consulta.setIdSitio(idSitio);
			consulta.setUsuariosRegistrados(numClientes);
			consulta.setBoletasVendidas(numVendidas);
			
			consultas.add(consulta);
		}
		return consultas;	
	}
	
	public ArrayList<UsuarioReporte> darConsultaAsistencia(String nombreCompania, String fechaInicio, String fechaFin, String asistencia, String orderBy) throws SQLException{
		ArrayList<UsuarioReporte> reporte = new ArrayList<>();
		String sql = "select * from((select * from(select * from(SELECT * FROM(SELECT*FROM(select * from boleta)natural join(select * from boleta_detalle)) "
				+ "NATURAL JOIN(select ID AS IDFUNCION, IDESPECTACULO, DIA from funcion))NATURAL JOIN(SELECT ID AS IDCLIENTE, CORREO, nombre as nombrecli FROM USUARIOS))"
				+ "natural join(select * from COMPANIA_ESPECTACULO))natural join(select ID AS IDCOMPANIA, NOMBRE as NOMBRECOMPA from companias))"
				+ "where DIA BETWEEN TO_DATE('"+fechaInicio+"', 'dd/mm/yy') AND TO_DATE('"+fechaFin+"', 'dd/mm/yy')and asistencia = '"+asistencia+"' and NOMBRECOMPA = '"+nombreCompania+"'order by "+ orderBy;
		PreparedStatement ps = conn.prepareStatement(sql);
		System.out.println("sql stm: "+ sql);
		recursos.add(ps);
		ResultSet rs = ps.executeQuery();

		while(rs.next()){
			UsuarioReporte usuario = new UsuarioReporte();
			Long idCliente = Long.parseLong(rs.getString("IDCLIENTE"));
			String nombre = rs.getString("NOMBRECLI");
			String correo = rs.getString("CORREO");
			System.out.println("hola");

			if(reporte.size()==0){
				usuario.setId(idCliente);
				usuario.setNombre(nombre);
				usuario.setCorreo(correo);
				reporte.add(usuario);
			}
			else{	
				for(int i = 0; i<reporte.size(); i++){
					if( idCliente == reporte.get(i).getId()){
						break;
					}
					else{
						usuario.setId(idCliente);
						usuario.setNombre(nombre);
						usuario.setCorreo(correo);
						reporte.add(usuario);
					}
				}
			}
		}
		return reporte;

	}

}
