package dtm;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TopicConnectionFactory;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.rabbitmq.jms.admin.RMQConnectionFactory;
import com.rabbitmq.jms.admin.RMQDestination;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import jms.RF15;
import jms.RF16;
import jms.RFC14;
import tm.FestivAndesMaster;
import vos.BoletaConsulta;
import vos.CompraBoleta;
import vos.ConsultaRentabilidad;
import vos.ListaCompraBoleta;
import vos.ListaRecibo;
import vos.NotaDebito;
import vos.Recibo;
import vos.RentabilidadCompania;


public class VideoAndesDistributed 
{
	private final static String QUEUE_NAME = "java:global/RMQAppQueue";
	private final static String MQ_CONNECTION_NAME = "java:global/RMQClient";

	private static VideoAndesDistributed instance;

	private FestivAndesMaster tm;

	private QueueConnectionFactory queueFactory;

	private TopicConnectionFactory factory;

	private RF15 rf15;
	private RF16 rf16;
	private RFC14 rfc14;

	private static String path;


	private VideoAndesDistributed() throws NamingException, JMSException
	{
		InitialContext ctx = new InitialContext();
		factory = (RMQConnectionFactory) ctx.lookup(MQ_CONNECTION_NAME);
		rf15 = new RF15(factory, ctx);
		rf15.start();



	}

	public void stop() throws JMSException
	{
		rf15.close();
	}

	/**
	 * M�todo que retorna el path de la carpeta WEB-INF/ConnectionData en el deploy actual dentro del servidor.
	 * @return path de la carpeta WEB-INF/ConnectionData en el deploy actual.
	 */
	public static void setPath(String p) {
		path = p;
	}

	public void setUpTransactionManager(FestivAndesMaster tm)
	{
		this.tm = tm;
	}

	private static VideoAndesDistributed getInst()
	{
		return instance;
	}

	public static VideoAndesDistributed getInstance(FestivAndesMaster tm)
	{
		if(instance == null)
		{
			try {
				instance = new VideoAndesDistributed();
			} catch (NamingException e) {
				e.printStackTrace();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		instance.setUpTransactionManager(tm);
		return instance;
	}

	public static VideoAndesDistributed getInstance()
	{
		if(instance == null)
		{
			FestivAndesMaster tm = new FestivAndesMaster(path);
			return getInstance(tm);
		}
		if(instance.tm != null)
		{
			return instance;
		}
		FestivAndesMaster tm = new FestivAndesMaster(path);
		return getInstance(tm);
	}
	
	public ArrayList<RentabilidadCompania> getLocalRF14(ConsultaRentabilidad consulta) throws Exception{
		return tm.generarReporteRentabilidadCompania(consulta.getNombre(), consulta.getRango());
	}
	
	public ArrayList<RentabilidadCompania> getRemoteRF14(ConsultaRentabilidad consulta) throws JsonGenerationException, JsonMappingException, NoSuchAlgorithmException, IOException, JMSException, InterruptedException, NonReplyException{
		return rfc14.getRemoteRFC14(consulta);	
	}

	public ArrayList<Recibo> getLocalRF15(ArrayList<CompraBoleta>cbs) throws Exception{
		return tm.registrarAbono(cbs.get(0).getIdCliente(), cbs);
	}
	
	public ArrayList<Recibo> getRemoteRF15(ArrayList<CompraBoleta>cbs) throws JsonGenerationException, JsonMappingException, NoSuchAlgorithmException, IOException, JMSException, InterruptedException, NonReplyException{
		return rf15.getRemoteRF15(cbs);	
	}
	
	public ArrayList<NotaDebito> getLocalRF16(String nombreCompania ) throws Exception{
		return tm.cancelarCompania(nombreCompania);
	}
	
	public ArrayList<NotaDebito> getRemoteRF16(String nombreCompania) throws JsonGenerationException, JsonMappingException, NoSuchAlgorithmException, IOException, JMSException, InterruptedException, NonReplyException{
		return rf16.getRemoteRF16(nombreCompania);	
	}
	
}