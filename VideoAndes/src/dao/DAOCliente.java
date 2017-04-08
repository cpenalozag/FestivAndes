package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sun.security.ntlm.Client;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import vos.Categoria;
import vos.Cliente;
import vos.CompraBoleta;
import vos.Recibo;
import vos.Silla;

public class DAOCliente {


	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	private DAOEspectaculo de = new DAOEspectaculo();

	/**
	 * Método constructor que crea DAOVFestivAndes
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOCliente() {
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


	public Cliente agregarPreferencia(Long idCliente, String tipoCate) throws Exception
	{
		String sql = "select * from cliente where id = '"+idCliente+"'";
		System.out.println("sql stm: " + sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		if(rs.next())
		{
			String sql2 = "select * from CATEGORIA where tipo ='"+tipoCate+"'";
			System.out.println("sql stm: " + sql2);
			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			ResultSet rs2 = prepStmt2.executeQuery();

			if(rs2.next()){

				String sql3 = "insert into PREFERENCIA(IDCLIENTE,IDCATEGORIA) values ('"+idCliente+"','"+rs2.getString("ID")+"')";
				System.out.println("sql stm: " + sql3);
				PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
				recursos.add(prepStmt3);
				prepStmt3.executeQuery();

			}
			else
				throw new Exception("La categoria no se encuentra en la base de datos");
		}
		else
			throw new Exception("El cliente no existe");

		return darClientes(idCliente);
	}


	public Cliente modificarPreferencia(Long idCliente, String catAnt, String catNueva) throws Exception
	{
		String sql = "select * from cliente where id = '"+idCliente+"'";
		System.out.println("sql stm: " + sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		if(rs.next())
		{
			String sql2 = "select * from CATEGORIA where tipo ='"+catAnt+"'";
			System.out.println("sql stm: " + sql2);
			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			ResultSet rs2 = prepStmt2.executeQuery();

			String sql3 = "select * from CATEGORIA where tipo ='"+catNueva+"'";
			System.out.println("sql stm: " + sql3);
			PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
			recursos.add(prepStmt3);
			ResultSet rs3 = prepStmt3.executeQuery();
			if(rs2.next() && rs3.next())
			{
				String sql4 ="DELETE FROM PREFERENCIA WHERE IDCATEGORIA ='"+catAnt+"'AND IDCLIENTE ='"+idCliente+"'";
				System.out.println("sql stm: " + sql4);
				PreparedStatement prepStmt4 = conn.prepareStatement(sql4);
				recursos.add(prepStmt4);
				prepStmt4.executeQuery();	

				String sql5= "INSERT INTO PREFERENCIA(IDCLIENTE,IDCATEGORIA) VALUES = ('"+idCliente+"','"+ catNueva+"')";
				System.out.println("sql stm: " + sql5);
				PreparedStatement prepStmt5 = conn.prepareStatement(sql5);
				recursos.add(prepStmt5);
				prepStmt5.executeQuery();

				//						"update PREFERENCIA set IDCATEGORIA ='"+rs3.getString("ID")+"'"+
				//						"where (IDCLIENTE = '"+ idCliente+"'and IDCATEGORIA = '"+rs2.getString("ID")+"')";

			}
			else
				throw new Exception("alguna de las dos categorias no existe en la base de datos");

		}
		else
			throw new Exception("No existe el cliente");

		return darClientes(idCliente);

	}

	public Cliente borrarPreferencia(Long idCliente, String categoria) throws Exception
	{
		String sql = "select * from cliente where id = '"+idCliente+"'";
		System.out.println("sql stm: " + sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		if(rs.next())
		{
			String sql2 = "select * from CATEGORIA where tipo ='"+categoria+"'";
			System.out.println("sql stm: " + sql2);
			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			recursos.add(prepStmt2);
			ResultSet rs2 = prepStmt2.executeQuery();
			if(rs2.next())
			{
				String sql3 = "delete from PREFERENCIA where IDCLIENTE = '"+idCliente+"' and IDCATEGORIA = '"+rs2.getString("ID")+"'";
				System.out.println("sql stm: " + sql3);
				PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
				recursos.add(prepStmt3);
				prepStmt3.executeQuery();
			}
			else
				throw new Exception("no existe la categoria seleccionada");
		}
		else 
			throw new Exception("No existe el cliente");

		return darClientes(idCliente);
	}

	public List<Cliente> darClientes() throws SQLException
	{
		List<Cliente> clientes = new ArrayList<>(); 
		String sql= "SELECT * from("+
				"(select* from cliente) natural join"+
				"(select * from usuarios))";



		System.out.println("sql stm: " + sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		while(rs.next())
		{
			Cliente n = new Cliente();
			n.setId(Long.parseLong(rs.getString("ID")));
			n.setEdad(Integer.parseInt(rs.getString("EDAD")));
			n.setNombre(rs.getString("NOMBRE"));
			n.setContrasena(rs.getString("CONTRASENA"));
			n.setCorreo(rs.getString("CORREO"));
			agregarCategorias(n);
			clientes.add(n);
		}
		return clientes;
	}

	public Cliente darClientes(Long id ) throws SQLException
	{
		Cliente n = new Cliente();
		String sql= "SELECT * from("+
				"(select* from cliente) natural join"+
				"(select * from usuarios)) where ID = '"+id+"'";

		System.out.println("sql stm: " + sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		while(rs.next())
		{

			n.setId(Long.parseLong(rs.getString("ID")));
			n.setEdad(Integer.parseInt(rs.getString("EDAD")));
			n.setNombre(rs.getString("NOMBRE"));
			n.setContrasena(rs.getString("CONTRASENA"));
			n.setCorreo(rs.getString("CORREO"));
			agregarCategorias(n);

		}
		return n;
	}
	public void agregarCategorias(Cliente cli) throws SQLException
	{
		String sql = "SELECT * FROM("+
				"Select* from("+        
				"(select ID AS IDCATEGORIA, TIPO from categoria)"+
				"natural join (select * from preferencia))natural join"+ 
				"(SELECT  ID AS IDCLIENTE, EDAD, NOMBRE,CORREO,CONTRASENA from("+
				"(select* from cliente) natural join"+
				"(select * from usuarios))))WHERE IDCLIENTE ='"+cli.getId()+"'";
		System.out.println("sql stm: " + sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		while (rs.next())
		{
			Categoria cat = new Categoria();
			cat.setId(Long.parseLong(rs.getString("IDCATEGORIA")));
			cat.setTipo(rs.getString("TIPO"));
			cli.addCategoria(cat);
		}

	}
	private int darNumeroBoleta() throws SQLException{
		int resp = 1000000;
		String sql = "SELECT MAX(ID) FROM BOLETA";
		PreparedStatement ps = conn.prepareStatement(sql);
		recursos.add(ps);
		ResultSet rs = ps.executeQuery();
		if (rs.next()){
			resp = Integer.parseInt(rs.getString("MAX(ID)"));
		}
		return (resp+1);
	}

	private boolean sillaDisponible(long idEspectaculo, long idFuncion, long idSilla, long idLocalidad) throws SQLException{
		String sql4 = "WITH ABC AS (SELECT ID,IDSITIO,IDLOCALIDAD FROM SILLA "
				+ "NATURAL JOIN (SELECT DISTINCT IDSITIO FROM BOLETA "
				+ "NATURAL JOIN BOLETA_SILLA WHERE IDESPECTACULO = '"+idEspectaculo+"' AND IDFUNCION = '"+idFuncion+"') "
				+ "MINUS "
				+ "SELECT IDSILLA AS ID, IDSITIO, IDLOCALIDAD FROM BOLETA "
				+ "NATURAL JOIN BOLETA_SILLA "
				+ "NATURAL JOIN (SELECT ID AS IDSILLA, IDSITIO, IDLOCALIDAD FROM SILLA) "
				+ "WHERE IDESPECTACULO = '"+idEspectaculo+"' AND IDFUNCION = '"+idFuncion+"') "
				+ "SELECT * FROM ABC NATURAL JOIN SILLA WHERE IDLOCALIDAD='"+ idLocalidad +"'";
		PreparedStatement ps4 = conn.prepareStatement(sql4);
		recursos.add(ps4);
		ResultSet rs4 = ps4.executeQuery();
		return rs4.next();
	}

	public Silla obtenerSilla(long idSilla, long idFuncion, long idEspectaculo, long idSitio) throws SQLException{
		Silla si = new Silla();
		String sql = "SELECT * FROM SILLA WHERE ID = '"+idSilla+"' AND IDSITIO = '"+idSitio+"'";
		PreparedStatement ps = conn.prepareStatement(sql);
		recursos.add(ps);
		ResultSet rs = ps.executeQuery();
		if (rs.next()){
			si.setFila(rs.getString("FILA"));
			si.setNumero(rs.getString("NUMERO"));
			si.setId(idSilla);
			si.setLocalidad(Long.parseLong(rs.getString("IDLOCALIDAD")));
			si.setIdSitio(idSitio);
		}
		return si;
	}

	public Recibo registrarCompraBoleta(long idCliente, long idFuncion, long idEspectaculo, long idSilla, long idSitio) throws Exception{
		Silla silla = obtenerSilla(idSilla, idFuncion, idEspectaculo, idSitio);
		String sql = "SELECT * FROM BOLETA NATURAL JOIN BOLETA_SILLA "
				+ "NATURAL JOIN (SELECT ID AS IDSILLA, IDSITIO, IDLOCALIDAD FROM SILLA) "
				+ "NATURAL JOIN LOCALIDAD  "
				+ "WHERE IDFUNCION = '"+idFuncion+"' AND IDESPECTACULO = '"+idEspectaculo+"' AND IDLOCALIDAD = '"+silla.getLocalidad()+"'";
		PreparedStatement ps = conn.prepareStatement(sql);
		recursos.add(ps);
		ResultSet rs = ps.executeQuery();
		Recibo recibo = new Recibo();
		if (rs.next()){
			recibo.setPrecio(Double.parseDouble(rs.getString("PRECIO")));
			recibo.setFuncion(de.obtenerFuncion(idEspectaculo, idFuncion, conn));
			recibo.setSilla(silla);
			recibo.setSitio(de.obtenerSitio(silla.getIdSitio(), conn));
			boolean numerada = rs.getString("NUMERADA").equals("t");
			if (numerada){
				String sql2 = "WITH ABC AS (SELECT ID,IDSITIO,IDLOCALIDAD "
						+ "FROM SILLA NATURAL JOIN (SELECT DISTINCT IDSITIO "
						+ "FROM BOLETA NATURAL JOIN BOLETA_SILLA "
						+ "WHERE IDESPECTACULO = '"+idEspectaculo+"' AND IDFUNCION = '"+idFuncion+"') "
						+ "MINUS "
						+ "SELECT IDSILLA AS ID, IDSITIO, IDLOCALIDAD FROM BOLETA NATURAL JOIN BOLETA_SILLA "
						+ "NATURAL JOIN (SELECT ID AS IDSILLA, IDSITIO, IDLOCALIDAD FROM SILLA) "
						+ "WHERE IDESPECTACULO = '"+idEspectaculo+"' AND IDFUNCION = '"+idFuncion+"') "
						+ "SELECT * FROM ABC NATURAL JOIN SILLA "
						+ "WHERE IDLOCALIDAD='"+silla.getLocalidad()+"' AND ID='"+silla.getId()+"' AND IDSITIO = '"+silla.getIdSitio()+"'";
				PreparedStatement ps2 = conn.prepareStatement(sql2);
				recursos.add(ps2);
				ResultSet rs2 = ps2.executeQuery();
				if (rs2.next()){
					int numBoleta = darNumeroBoleta();
					String insert1 = "Insert into BOLETA_SILLA (ID,IDSITIO,IDSILLA) values ('"+numBoleta+"','"+silla.getIdSitio()+"','"+silla.getId()+"')";
					String insert2 = "Insert into BOLETA (ID,IDFUNCION,IDESPECTACULO) values ('"+numBoleta+"','"+idFuncion+"','"+idEspectaculo+"')";
					String insert3 ="";
					if (idCliente!=0){
						insert3 = "Insert into BOLETA_DETALLE (ID,IDCLIENTE,ESCLIENTE) values ('"+numBoleta+"','"+idCliente+"','t')";
					}
					else{
						insert3 = "Insert into BOLETA_DETALLE (ID,IDCLIENTE,ESCLIENTE) values ('"+numBoleta+"','','t')";
					}
					PreparedStatement i1 = conn.prepareStatement(insert1);
					PreparedStatement i2 = conn.prepareStatement(insert2);
					PreparedStatement i3 = conn.prepareStatement(insert3);
					recursos.add(i1);
					recursos.add(i2);
					recursos.add(i3);
					i1.executeQuery();
					i2.executeQuery();
					i3.executeQuery();
				}
				else{
					throw new Exception("La silla no está disponible.");
				}
			}
			else if (!numerada){
				int capacidad = Integer.parseInt(rs.getString("CAPACIDAD"));
				String sql3 = "SELECT COUNT(*) AS CANTIDAD FROM BOLETA "
						+ "NATURAL JOIN BOLETA_SILLA NATURAL JOIN (SELECT * FROM LOCALIDAD NATURAL JOIN TIPOLOCALIDAD) "
						+ "WHERE IDFUNCION = '"+idFuncion+"' AND NOMBRE = '"+silla.getLocalidad()+"'";
				PreparedStatement ps3 = conn.prepareStatement(sql3);
				recursos.add(ps3);
				ResultSet rs3 = ps3.executeQuery();
				int cantidad = -1;
				if(rs3.next()){
					cantidad = Integer.parseInt(rs3.getString("CANTIDAD"));
				}
				if (cantidad<capacidad){
					String sql4 = "WITH ABC AS (SELECT ID,IDSITIO,IDLOCALIDAD FROM SILLA "
							+ "NATURAL JOIN (SELECT DISTINCT IDSITIO FROM BOLETA "
							+ "NATURAL JOIN BOLETA_SILLA WHERE IDESPECTACULO = '"+idEspectaculo+"' AND IDFUNCION = '"+idFuncion+"') "
							+ "MINUS "
							+ "SELECT IDSILLA AS ID, IDSITIO, IDLOCALIDAD FROM BOLETA "
							+ "NATURAL JOIN BOLETA_SILLA "
							+ "NATURAL JOIN (SELECT ID AS IDSILLA, IDSITIO, IDLOCALIDAD FROM SILLA) "
							+ "WHERE IDESPECTACULO = '"+idEspectaculo+"' AND IDFUNCION = '"+idFuncion+"') "
							+ "SELECT * FROM ABC NATURAL JOIN SILLA WHERE IDLOCALIDAD='"+ silla.getLocalidad() +"'";
					PreparedStatement ps4 = conn.prepareStatement(sql4);
					recursos.add(ps4);
					ResultSet rs4 = ps4.executeQuery();
					if (rs4.next()){
						int numBoleta = darNumeroBoleta();
						String insert1 = "Insert into BOLETA_SILLA (ID,IDSITIO,IDSILLA) values ('"+numBoleta+"','"+silla.getIdSitio()+"','"+silla.getId()+"')";
						String insert2 = "Insert into BOLETA (ID,IDFUNCION,IDESPECTACULO) values ('"+numBoleta+"','"+idFuncion+"','"+idEspectaculo+"')";
						String insert3 ="";
						if (idCliente!=0){
							insert3 = "Insert into BOLETA_DETALLE (ID,IDCLIENTE,ESCLIENTE) values ('"+numBoleta+"','"+idCliente+"','t')";
						}
						else{
							insert3 = "Insert into BOLETA_DETALLE (ID,IDCLIENTE,ESCLIENTE) values ('"+numBoleta+"','','t')";
						}
						PreparedStatement i1 = conn.prepareStatement(insert1);
						PreparedStatement i2 = conn.prepareStatement(insert2);
						PreparedStatement i3 = conn.prepareStatement(insert3);
						recursos.add(i1);
						recursos.add(i2);
						recursos.add(i3);
						i1.executeQuery();
						i2.executeQuery();
						i3.executeQuery();
					}
					else{
						throw new Exception("La silla no está disponible.");
					}
				}
			}
		}
		else{
			throw new Exception("No hay boletas para el numero de funcion y localidad dadas.");
		}
		return recibo;

	}

	public Recibo registrarCompra(long idCliente, long idFuncion, long idEspectaculo, long idSilla, long idSitio, String abonada) throws Exception{
		Silla silla = obtenerSilla(idSilla, idFuncion, idEspectaculo, idSitio);
		Recibo recibo = new Recibo();
		String sql = "SELECT * FROM SILLA "
				+ "NATURAL JOIN (SELECT ID AS IDLOCALIDAD, IDSITIO, PRECIO, NUMERADA, CAPACIDAD FROM LOCALIDAD)"
				+ "WHERE IDLOCALIDAD = '"+silla.getLocalidad()+"' AND IDSITIO = '"+idSitio+"'";
		PreparedStatement ps = conn.prepareStatement(sql);
		System.out.println("sql stm: " + sql);
		recursos.add(ps);
		ResultSet rs = ps.executeQuery();

		if (rs.next()){
			if(abonada.equals("f")){
			recibo.setPrecio(Double.parseDouble(rs.getString("PRECIO")));
			}
			else{
				Double precio = Double.parseDouble(rs.getString("PRECIO"));
				
			}
			
			recibo.setFuncion(de.obtenerFuncion(idEspectaculo, idFuncion, conn));
			
			recibo.setSilla(silla);

			recibo.setSitio(de.obtenerSitio(silla.getIdSitio(), conn));
			
			boolean numerada = rs.getString("NUMERADA").equals("t");
			if (numerada){

				if (sillaDisponible(idEspectaculo, idFuncion, idSilla, silla.getLocalidad()))
				{
					
					int numBoleta = darNumeroBoleta();
					String insert1 = "Insert into BOLETA_SILLA (ID,IDSITIO,IDSILLA) values ('"+numBoleta+"','"+silla.getIdSitio()+"','"+silla.getId()+"')";
					String insert2 = "";
					if(abonada.equals("f")){
						insert2 ="Insert into BOLETA (ID,IDFUNCION,IDESPECTACULO, ABONADA) values ('"+numBoleta+"','"+idFuncion+"','"+idEspectaculo+"','f')";
					}else{
						insert2 = "Insert into BOLETA (ID,IDFUNCION,IDESPECTACULO, ABONADA) values ('"+numBoleta+"','"+idFuncion+"','"+idEspectaculo+"','t')";
					}
					String insert3 ="";
					if (idCliente!=0){
						insert3 = "Insert into BOLETA_DETALLE (ID,IDCLIENTE,ESCLIENTE) values ('"+numBoleta+"','"+idCliente+"','t')";
					}
					else{
						insert3 = "Insert into BOLETA_DETALLE (ID,IDCLIENTE,ESCLIENTE) values ('"+numBoleta+"','','t')";
					}
					PreparedStatement i1 = conn.prepareStatement(insert1);
					System.out.println("sql stm: " + insert1);
					PreparedStatement i2 = conn.prepareStatement(insert2);
					System.out.println("sql stm: " + insert2);
					PreparedStatement i3 = conn.prepareStatement(insert3);
					System.out.println("sql stm: " + insert3);
					recursos.add(i1);
					recursos.add(i2);
					recursos.add(i3);
					i1.executeQuery();
					i2.executeQuery();
					i3.executeQuery();
				}
				else{
					throw new Exception("no est� disponible la silla");
					
				}
			}
			else if (!numerada){
				System.out.println("entro");
				int capacidad = Integer.parseInt(rs.getString("CAPACIDAD"));
				System.out.println("3nee3");
				String sql3 = "SELECT COUNT(*) AS CANTIDAD FROM BOLETA "
						+ "NATURAL JOIN BOLETA_SILLA NATURAL JOIN (SELECT * FROM LOCALIDAD NATURAL JOIN TIPOLOCALIDAD) "
						+ "WHERE IDFUNCION = '"+idFuncion+"' AND NOMBRE = '"+silla.getLocalidad()+"'";
				PreparedStatement ps3 = conn.prepareStatement(sql3);
				System.out.println("sql stm: " + sql3);
				recursos.add(ps3);
				ResultSet rs3 = ps3.executeQuery();
				int cantidad = -1;
				if(rs3.next()){
					cantidad = Integer.parseInt(rs3.getString("CANTIDAD"));
				}
				if (cantidad<capacidad){
					String sql4 = "WITH ABC AS (SELECT ID,IDSITIO,IDLOCALIDAD FROM SILLA "
							+ "NATURAL JOIN (SELECT DISTINCT IDSITIO FROM BOLETA "
							+ "NATURAL JOIN BOLETA_SILLA WHERE IDESPECTACULO = '"+idEspectaculo+"' AND IDFUNCION = '"+idFuncion+"') "
							+ "MINUS "
							+ "SELECT IDSILLA AS ID, IDSITIO, IDLOCALIDAD FROM BOLETA "
							+ "NATURAL JOIN BOLETA_SILLA "
							+ "NATURAL JOIN (SELECT ID AS IDSILLA, IDSITIO, IDLOCALIDAD FROM SILLA) "
							+ "WHERE IDESPECTACULO = '"+idEspectaculo+"' AND IDFUNCION = '"+idFuncion+"') "
							+ "SELECT * FROM ABC NATURAL JOIN SILLA WHERE IDLOCALIDAD='"+ silla.getLocalidad() +"'";
					PreparedStatement ps4 = conn.prepareStatement(sql4);
					System.out.println("sql stm: " + sql4);
					recursos.add(ps4);
					ResultSet rs4 = ps4.executeQuery();
					if (rs4.next()){
						int numBoleta = darNumeroBoleta();
						String insert1 = "Insert into BOLETA_SILLA (ID,IDSITIO,IDSILLA) values ('"+numBoleta+"','"+silla.getIdSitio()+"','"+silla.getId()+"')";
						String insert2 = "Insert into BOLETA (ID,IDFUNCION,IDESPECTACULO) values ('"+numBoleta+"','"+idFuncion+"','"+idEspectaculo+"')";
						String insert3 ="";
						if (idCliente!=0){
							insert3 = "Insert into BOLETA_DETALLE (ID,IDCLIENTE,ESCLIENTE) values ('"+numBoleta+"','"+idCliente+"','t')";
						}
						else{
							insert3 = "Insert into BOLETA_DETALLE (ID,IDCLIENTE,ESCLIENTE) values ('"+numBoleta+"','','t')";
						}
						PreparedStatement i1 = conn.prepareStatement(insert1);
						PreparedStatement i2 = conn.prepareStatement(insert2);
						PreparedStatement i3 = conn.prepareStatement(insert3);
						recursos.add(i1);
						recursos.add(i2);
						recursos.add(i3);
						i1.executeQuery();
						i2.executeQuery();
						i3.executeQuery();
					}
					else{
						throw new Exception("No hay sillas disponibles.");
					}

				}
			}
			else{
				throw new Exception("No hay boletas para el numero de funcion y localidad dadas.");
			}
		}
		return recibo;
	}

	public ArrayList<Recibo> registrarCompraMultiple( Long idCliente,CompraBoleta[] cbs) throws Exception
	{
		ArrayList<Recibo> recibos= new ArrayList<>(); 
		for(int i = 0; i<cbs.length; i++ )
		{
			recibos.add(registrarCompra(idCliente, cbs[i].getIdFuncion(), cbs[i].getIdEspectaculo(), cbs[i].getIdSilla(), cbs[i].getIdSitio(), "f"));
		}
		return recibos;
		
	}
	
	public ArrayList<Recibo> registrarAbono(Long idCliente, CompraBoleta[]cbs) throws Exception{
		ArrayList<Recibo> recibos = new ArrayList<>();
		for(int i =0; i< cbs.length; i++){
			recibos.add(registrarCompra(idCliente, cbs[i].getIdFuncion(), cbs[i].getIdEspectaculo(), cbs[i].getIdSilla(), cbs[i].getIdSitio(), "t"));
		}
		return null;
	}

}