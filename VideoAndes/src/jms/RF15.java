package jms;

import java.awt.List;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.jms.DeliveryMode;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.bind.DatatypeConverter;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.rabbitmq.jms.admin.RMQConnectionFactory;
import com.rabbitmq.jms.admin.RMQDestination;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import dtm.NonReplyException;
import dtm.VideoAndesDistributed;
import vos.BoletaConsulta;
import vos.CompraBoleta;
import vos.ExchangeMsg;
import vos.ListaCompraBoleta;
import vos.ListaRecibo;
import vos.Recibo;

public class RF15 implements MessageListener, ExceptionListener{
	
	public final static int TIME_OUT = 5;
	private final static String APP = "app1";

	private final static String GLOBAL_TOPIC_NAME = "java:global/RMQRF15ALL";
	private final static String LOCAL_TOPIC_NAME = "java:global/RMQRF15LOCAL";

	private final static String REQUEST = "REQUEST";
	private final static String REQUEST_ANSWER = "REQUEST_ANSWER";
	
	private TopicConnection topicConnection;
	private TopicSession topicSession;
	private Topic globalTopic;
	private Topic localTopic;
	
	private ArrayList<Recibo> answer = new ArrayList<>();
	
	public RF15(TopicConnectionFactory factory, InitialContext ctx) throws JMSException, NamingException {
		
		topicConnection = factory.createTopicConnection();
		topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		globalTopic = (RMQDestination) ctx.lookup(GLOBAL_TOPIC_NAME);
		TopicSubscriber topicSubscriber =  topicSession.createSubscriber(globalTopic);
		topicSubscriber.setMessageListener(this);
		localTopic = (RMQDestination) ctx.lookup(LOCAL_TOPIC_NAME);
		topicSubscriber =  topicSession.createSubscriber(localTopic);
		topicSubscriber.setMessageListener(this);
		topicConnection.setExceptionListener(this);
		
	}
	public void start() throws JMSException
	{
		topicConnection.start();
	}

	public void close() throws JMSException
	{
		topicSession.close();
		topicConnection.close();
	}
	
	private void sendMessage(String payload, String status, Topic dest, String id) throws JMSException, JsonGenerationException, JsonMappingException, IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(id);
		ExchangeMsg msg = new ExchangeMsg("RFC11.app1", APP, payload, status, id);
		TopicPublisher topicPublisher = topicSession.createPublisher(dest);
		topicPublisher.setDeliveryMode(DeliveryMode.PERSISTENT);
		TextMessage txtMsg = topicSession.createTextMessage();
		txtMsg.setJMSType("TextMessage");
		String envelope = mapper.writeValueAsString(msg);
		System.out.println(envelope);
		txtMsg.setText(envelope);
		topicPublisher.publish(txtMsg);
	}
	
	public ArrayList<Recibo> getRemoteRF15(CompraBoleta[]compraBoletas) throws NoSuchAlgorithmException, JsonGenerationException, JsonMappingException, IOException, JMSException, InterruptedException, NonReplyException{
		
		answer.clear();
		String id = APP+""+System.currentTimeMillis();
		MessageDigest md = MessageDigest.getInstance("MD5");
		id = DatatypeConverter.printHexBinary(md.digest(id.getBytes())).substring(0, 8);
		//		id = new String(md.digest(id.getBytes()));
		ObjectMapper mapper = new ObjectMapper();
		String payload = mapper.writeValueAsString(compraBoletas);
		
		sendMessage(payload, REQUEST, globalTopic, id);
		boolean waiting = true;

		int count = 0;
		while(TIME_OUT != count){
			TimeUnit.SECONDS.sleep(1);
			count++;
		}
		if(count == TIME_OUT){
			if(this.answer.isEmpty()){
				waiting = false;
				throw new NonReplyException("Time Out - No Reply");
			}
		}
		waiting = false;

		if(answer.isEmpty())
			throw new NonReplyException("Non Response");
		ListaRecibo res = new ListaRecibo();
		res.setListaRecibos(answer);
		
		return answer;
	}


	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		
		TextMessage txt = (TextMessage) message;
		try 
		{
			String body = txt.getText();
			System.out.println(body);
			ObjectMapper mapper = new ObjectMapper();
			ExchangeMsg ex = mapper.readValue(body, ExchangeMsg.class);
			String id = ex.getMsgId();
			System.out.println(ex.getSender());
			System.out.println(ex.getStatus());
			if(!ex.getSender().equals(APP))
			{
				if(ex.getStatus().equals(REQUEST))
				{
					ArrayList<CompraBoleta> boletas = mapper.readValue(ex.getPayload(), ArrayList.class);
					VideoAndesDistributed dtm = VideoAndesDistributed.getInstance();
					ArrayList<Recibo> recibos = dtm.getLocalRF15(boletas);
					String payload = mapper.writeValueAsString(recibos);
					Topic t = new RMQDestination("", "RFC11.test", ex.getRoutingKey(), "", false);
					sendMessage(payload, REQUEST_ANSWER, t, id);
				}
//				7fdssdsfdsfd
				else if(ex.getStatus().equals(REQUEST_ANSWER))
				{
					ArrayList<Recibo> reci = mapper.readValue(ex.getPayload(), ArrayList.class);
					answer.addAll(reci);
				}
			}

		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	
	@Override
	public void onException(JMSException arg0) {
		// TODO Auto-generated method stub
		
	}
	

}
