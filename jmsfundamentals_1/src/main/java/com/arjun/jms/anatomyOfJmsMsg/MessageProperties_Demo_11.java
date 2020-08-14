package com.arjun.jms.anatomyOfJmsMsg;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MessageProperties_Demo_11 {

	public static void main(String[] args) throws NamingException, JMSException {

		InitialContext context = new InitialContext();
		Queue queue = (Queue) context.lookup("queue/myQueue");

		try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext = cf.createContext()) {

			JMSProducer producer = jmsContext.createProducer();
			TextMessage textMessage = jmsContext.createTextMessage("Message 1");
			textMessage.setBooleanProperty("loggedIn", true);
			textMessage.setStringProperty("userToken", "abc123");
			producer.send(queue, textMessage);

			JMSConsumer consumer = jmsContext.createConsumer(queue);
			TextMessage messageReceived = (TextMessage) consumer.receive(5000);

			System.out.println("Message received : " + messageReceived.getText());
			System.out.println("Message received : " + messageReceived.getBooleanProperty("loggedIn"));
			System.out.println("Message received : " + messageReceived.getStringProperty("userToken"));

		}

	}
}

/*
 * add custom properties to the message
 * 
 */
