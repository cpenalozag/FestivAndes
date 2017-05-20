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
package tm;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import com.sun.security.ntlm.Client;

import dao.DAOAdministrador;
import dao.DAOCliente;
import dao.DAOCompania;
import dao.DAOEspectaculo;
import dao.DAOFestivAndes;
import dao.DAOFuncion;
import dao.DAOOperador;
import vos.BoletaConsulta;
import vos.Cliente;
import vos.ClienteBueno;
import vos.CompraBoleta;
import vos.EspectaculoPopular;
import vos.FiltrosBoletas;
import vos.Funcion;
import vos.Funcion2;
import vos.InformeAsistencia;
import vos.NotaDebito;
import vos.OperadorLogistico;
import vos.RangoFechas;
import vos.Recibo;
import vos.RentabilidadCompania;
import vos.RentabilidadCompanias;
import vos.ReporteEspectaculo;
import vos.ReporteFuncion;
import vos.Sitio;
import vos.Sitio2;
import vos.UsuarioReporte;

/**
 * Fachada en patron singleton de la aplicación
 * @author Juan
 */
public class FestivAndesMaster {


	/**
	 * Atributo estático que contiene el path relativo del archivo que tiene los datos de la conexión
	 */
	private static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";

	/**
	 * Atributo estático que contiene el path absoluto del archivo que tiene los datos de la conexión
	 */
	private  String connectionDataPath;

	/**
	 * Atributo que guarda el usuario que se va a usar para conectarse a la base de datos.
	 */
	private String user;

	/**
	 * Atributo que guarda la clave que se va a usar para conectarse a la base de datos.
	 */
	private String password;

	/**
	 * Atributo que guarda el URL que se va a usar para conectarse a la base de datos.
	 */
	private String url;

	/**
	 * Atributo que guarda el driver que se va a usar para conectarse a la base de datos.
	 */
	private String driver;

	/**
	 * Conexión a la base de datos
	 */
	private Connection conn;


	/**
	 * Método constructor de la clase VideoAndesMaster, esta clase modela y contiene cada una de las 
	 * transacciones y la logia de negocios que estas conllevan.
	 * <b>post: </b> Se crea el objeto VideoAndesMaster, se inicializa el path absoluto de el archivo de conexión y se
	 * inicializa los atributos que se usan par la conexión a la base de datos.
	 * @param contextPathP - path absoluto en el servidor del contexto del deploy actual
	 */
	public FestivAndesMaster(String contextPathP) {
		connectionDataPath = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
		initConnectionData();
	}

	/*
	 * Método que  inicializa los atributos que se usan para la conexion a la base de datos.
	 * <b>post: </b> Se han inicializado los atributos que se usan par la conexión a la base de datos.
	 */
	private void initConnectionData() {
		try {
			File arch = new File(this.connectionDataPath);
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream(arch);
			prop.load(in);
			in.close();
			this.url = prop.getProperty("url");
			this.user = prop.getProperty("usuario");
			this.password = prop.getProperty("clave");
			this.driver = prop.getProperty("driver");
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método que  retorna la conexión a la base de datos
	 * @return Connection - la conexión a la base de datos
	 * @throws SQLException - Cualquier error que se genere durante la conexión a la base de datos
	 */
	private Connection darConexion() throws SQLException {
		System.out.println("Connecting to: " + url + " With user: " + user);
		return DriverManager.getConnection(url, user, password);
	}

	////////////////////////////////////////
	///////Transacciones////////////////////
	////////////////////////////////////////

	public List<Funcion2> darFuncionesCompania(String nombre) throws Exception
	{
		List<Funcion2> funciones = new ArrayList<Funcion2>();
             DAOFuncion daoFuncion = new DAOFuncion();
		try{
			this.conn = darConexion();
			daoFuncion.setConn(conn);
			funciones = daoFuncion.darFuncionesCompania(nombre);
		}catch(SQLException e){
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}finally{
			try {
				daoFuncion.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

		return funciones;
	}

	public List<Funcion2> darFuncionesIdioma(String idioma) throws Exception
	{
		List<Funcion2> funciones = new ArrayList<Funcion2>();
		DAOFuncion daoFuncion = new DAOFuncion();
		try{
			this.conn = darConexion();
			daoFuncion.setConn(conn);
			funciones = daoFuncion.darFuncionesIdioma(idioma);
		}catch(SQLException e){
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}finally{
			try {
				daoFuncion.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

		return funciones;
	}

	public List<Funcion2> darFuncionesFecha(String fecha) throws Exception
	{
		List<Funcion2> funciones = new ArrayList<Funcion2>();
		DAOFuncion daoFuncion = new DAOFuncion();
		try{
			this.conn = darConexion();
			daoFuncion.setConn(conn);
			funciones = daoFuncion.darFuncionesFecha(fecha);
		}catch(SQLException e){
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}finally{
			try {
				daoFuncion.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

		return funciones;
	}

	public List<Funcion2> darFuncionesCategoria(String categoria) throws Exception
	{
		List<Funcion2> funciones = new ArrayList<Funcion2>();
		DAOFuncion daoFuncion = new DAOFuncion();
		try{
			this.conn = darConexion();
			daoFuncion.setConn(conn);
			funciones = daoFuncion.darFuncionesCategoria(categoria);
		}catch(SQLException e){
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}finally{
			try {
				daoFuncion.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

		return funciones;
	}

	public List<Funcion2> darFuncionesTraducidas(Boolean tradu) throws Exception
	{
		List<Funcion2> funciones = new ArrayList<Funcion2>();
		DAOFuncion daoFuncion = new DAOFuncion();
		try{
			this.conn = darConexion();
			daoFuncion.setConn(conn);
			funciones = daoFuncion.darFuncionesTraducidas(tradu);
		}catch(SQLException e){
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}finally{
			try {
				daoFuncion.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

		return funciones;
	}

	public Sitio2 darSitio(String nombre) throws Exception
	{
		Sitio2 sitio = new Sitio2();
		DAOFestivAndes daoFestivAndes = new DAOFestivAndes();
		try{
			this.conn = darConexion();
			daoFestivAndes.setConn(conn);
			sitio = daoFestivAndes.darSitio(nombre);
		}catch(SQLException e){
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}finally{
			try {
				daoFestivAndes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

		return sitio;
	}
	public Cliente agregarPreferencia(Long idCliente, String tipoCate)throws Exception {
		DAOCliente daoCliente = new DAOCliente();
		Cliente cli;
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoCliente.setConn(conn);
			cli =daoCliente.agregarPreferencia(idCliente,tipoCate);
			conn.commit();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCliente.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return cli;
		
	}
	public Cliente modificarPreferencia(Long idCliente, String catAnt, String catNueva) throws Exception {
		DAOCliente daoCliente = new DAOCliente();
		Cliente cli;
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoCliente.setConn(conn);
			cli=daoCliente.modificarPreferencia(idCliente,catAnt,catAnt);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCliente.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return cli;
	}
	
	public Cliente borrarPreferencia(Long idCliente, String categoria) throws Exception {
		DAOCliente daoCliente = new DAOCliente();
		Cliente cli;
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoCliente.setConn(conn);
			cli=daoCliente.borrarPreferencia(idCliente,categoria);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCliente.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return cli;
	}
	
	public Funcion2 marcarComoRealizado(Long  idOperario, Long idFuncion, Long idEsp, boolean isreali) throws Exception {
		DAOOperador daoOperador = new DAOOperador();
		Funcion2 fun;
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoOperador.setConn(conn);
			fun= daoOperador.marcarComoRealizado(idOperario,idFuncion, idEsp, isreali);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoOperador.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return fun;
	}
	
	public List<OperadorLogistico> darOperadores() throws Exception
	{
		List<OperadorLogistico> operadores = new ArrayList<OperadorLogistico>();
		DAOOperador daoOperador = new DAOOperador();
		try{
			this.conn = darConexion();
			daoOperador.setConn(conn);
			operadores = daoOperador.darOperadores();
		}catch(SQLException e){
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}finally{
			try {
				daoOperador.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

		return operadores;
	}
	
	public List<Cliente> darClientes() throws SQLException
	{
		List<Cliente> clientes = new ArrayList<Cliente>();
		DAOCliente daoCliente = new  DAOCliente();
		try{
			this.conn = darConexion();
			daoCliente.setConn(conn);
			clientes = daoCliente.darClientes();
		}catch(SQLException e){
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}finally{
			try {
				daoCliente.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

		return clientes;
	}
	
	public InformeAsistencia darInformeAsistencia(Long idCliente) throws SQLException{
		DAOCliente daoCliente = new DAOCliente();
		InformeAsistencia info = new InformeAsistencia();
		try{
			this.conn = darConexion();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			daoCliente.setConn(conn);
			conn.setSavepoint();
			info = daoCliente.informeAsistencia(idCliente);
			conn.commit();
			conn.setAutoCommit(true);
		}catch(SQLException e){
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}finally{
			try {
				daoCliente.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

		return info;
		
	}
	
	public Recibo generarCompra(int idCliente, int idFuncion, int idEspectaculo, int idSilla, int idSitio, String abonada) throws Exception {
		DAOCliente daoCliente = new DAOCliente();
		Recibo r = new Recibo();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoCliente.setConn(conn);
			r = daoCliente.registrarCompra(idCliente, idFuncion, idEspectaculo, idSilla, idSitio, abonada);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCliente.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return r;
	}
	
	
	public ArrayList<Recibo> registrarCompraMultiple( int idCliente,CompraBoleta[] cbs) throws Exception
	{
		DAOCliente daoCliente = new DAOCliente();
		ArrayList<Recibo> rs = new ArrayList<>();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			daoCliente.setConn(conn);
			conn.setSavepoint();
			rs = daoCliente.registrarCompraMultiple(idCliente,cbs);
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			
			throw e;
		} finally {
			try {
				daoCliente.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return rs;
	}
	
	public NotaDebito devolverBoleta( Long idCliente, Long idBoleta) throws Exception
	{
		DAOCliente daoCliente = new DAOCliente();
		NotaDebito nd = new NotaDebito();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			daoCliente.setConn(conn);
			conn.setSavepoint();
			nd = daoCliente.devolverBoleta(idBoleta, idCliente);
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCliente.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return nd;
	}
	
	
	
	public ArrayList<Recibo> registrarAbono(int idCliente, CompraBoleta[]cbs) throws Exception {
		DAOCliente daoCliente = new DAOCliente();
		ArrayList<Recibo> rs = new ArrayList<>();
		try 
		{
			//////Transacción
			
			this.conn = darConexion();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			daoCliente.setConn(conn);
			conn.setSavepoint();
			rs = daoCliente.registrarAbono(idCliente,cbs);
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			
			throw e;
		} finally {
			try {
				daoCliente.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return rs;
		
	}
	
	public ArrayList<NotaDebito> devolverAbono(Long idCliente) throws Exception {
		DAOCliente daoCliente = new DAOCliente();
		ArrayList<NotaDebito> nd = new ArrayList<>();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			daoCliente.setConn(conn);
			conn.setSavepoint();
			nd = daoCliente.devolverAbonamiento(idCliente);
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			try {
				daoCliente.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return nd;
		
	}
	
	public ArrayList<NotaDebito> cancelarFuncion(Long idFuncion, Long idEspectaculo) throws Exception{
		
		DAOAdministrador daoAdmin = new DAOAdministrador();
		ArrayList<NotaDebito> nd = new ArrayList<>();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			daoAdmin.setConn(conn);
			conn.setSavepoint();
			nd = daoAdmin.cancelarFuncion(idFuncion, idEspectaculo);
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoAdmin.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return nd;
		
	}
	public ArrayList<ReporteFuncion> generarReporteFuncion(Long idFuncion, Long idEspectaculo)throws Exception {
		DAOFuncion daoFuncion = new DAOFuncion();
		ArrayList<ReporteFuncion> reportes = new ArrayList<>();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoFuncion.setConn(conn);
			reportes = daoFuncion.generarReporte(idFuncion, idEspectaculo);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoFuncion.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return reportes;
		
	}
	
	public ArrayList<ReporteEspectaculo> generarReporteEspectaculo(Long idEspectaculo)throws Exception {
		DAOEspectaculo daoEspectaculo = new DAOEspectaculo();
		ArrayList<ReporteEspectaculo> reportes = new ArrayList<>();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoEspectaculo.setConn(conn);
			reportes = daoEspectaculo.generarReporte(idEspectaculo);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoEspectaculo.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return reportes;
		
	}
	
	public ArrayList<RentabilidadCompania> generarReporteRentabilidadCompania(Long idCompania, RangoFechas rango)throws Exception {
		DAOCompania dao = new DAOCompania();
		ArrayList<RentabilidadCompania> reportes = new ArrayList<RentabilidadCompania>();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			dao.setConn(conn);
			reportes = dao.generarReporte(idCompania, rango.getFechaInicial(), rango.getFechaFinal());
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return reportes;
	}
	
	public ArrayList<RentabilidadCompanias> generarReporteRentabilidadTodasCompanias(String fechaIni, String fechaFin)throws Exception {
		DAOOperador dao = new DAOOperador();
		ArrayList<RentabilidadCompanias> reportes = new ArrayList<>();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			dao.setConn(conn);
			reportes = dao.generarReporte(fechaIni,fechaFin);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return reportes;
	}
	
	public EspectaculoPopular darEspectaculoPopular(String fechaIni, String fechaFin)throws Exception {
		DAOOperador dao = new DAOOperador();
		EspectaculoPopular esp = new EspectaculoPopular();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			dao.setConn(conn);
			esp = dao.espectaculoMasPopular(fechaIni, fechaFin);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return esp;
	}
	
	public ArrayList<ClienteBueno> clientesBuenos(int n)throws Exception {
		DAOAdministrador dao = new DAOAdministrador();
		ArrayList<ClienteBueno> clientes = new ArrayList<>();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			dao.setConn(conn);
			clientes = dao.clientesBuenos(n);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return clientes;
	}
	
	public ArrayList<BoletaConsulta> consultaBoletas(FiltrosBoletas filtros)throws Exception {
		DAOAdministrador dao = new DAOAdministrador();
		ArrayList<BoletaConsulta> consultas = new ArrayList<>();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			dao.setConn(conn);
			consultas = dao.consultarCompraBoletas(filtros);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return consultas;
	}
	
	public ArrayList<UsuarioReporte> darConsultaAsistencia(String nombreCompania, String fechaInicio, String fechaFin, String asistencia, String orderBy)throws Exception{
		DAOAdministrador dao = new DAOAdministrador();
		ArrayList<UsuarioReporte> resp = new ArrayList<>();
		try 
		{
			//////Transacci�n
			this.conn = darConexion();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			dao.setConn(conn);
			conn.setSavepoint();
			resp = dao.darConsultaAsistencia(nombreCompania, fechaInicio, fechaFin, asistencia, orderBy);
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				dao.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return resp;
	}
	
}
	


