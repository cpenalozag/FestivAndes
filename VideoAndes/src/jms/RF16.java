package jms;

import java.io.IOException;
import java.util.ArrayList;

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

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.rabbitmq.jms.admin.RMQDestination;

import vos.CompraBoleta;
import vos.ExchangeMsg;
import vos.NotaDebito;

public class RF16 implements MessageListener, ExceptionListener {
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
	
	private ArrayList<NotaDebito> answer = new ArrayList<>();
	
	public RF16(TopicConnectionFactory factory, InitialContext ctx) throws JMSException, NamingException {
		
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
	

	@Override
	public void onException(JMSException arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onMessage(Message arg0) {
		// TODO Auto-generated method stub
		
	}

}
