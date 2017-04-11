package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

import vos.NotaDebito;

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


}
