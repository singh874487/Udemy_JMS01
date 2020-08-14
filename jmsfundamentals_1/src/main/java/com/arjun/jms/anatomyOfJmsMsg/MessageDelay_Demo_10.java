package com.arjun.jms.anatomyOfJmsMsg;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MessageDelay_Demo_10 {

	public static void main(String[] args) throws NamingException, JMSException  {

		InitialContext context = new InitialContext();
		Queue queue = (Queue) context.lookup("queue/myQueue");

		try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext = cf.createContext()) {

			JMSProducer producer = jmsContext.createProducer();
				
			producer.send(queue, "Hello World");
			
			producer.setDeliveryDelay(3000);
			producer.send(queue, "Hello World 2");
			

			JMSConsumer consumer = jmsContext.createConsumer(queue);
			TextMessage messageReceived = (TextMessage) consumer.receive(5000);

			System.out.println("Message received : " + messageReceived.getText());
			
			
			TextMessage messageReceived2 = (TextMessage) consumer.receive(5000);

			System.out.println("Message received : " + messageReceived2.getText());
 
		}

	}
}

/*
 * set DeliveryDelay to delay the message
 * 
 */
