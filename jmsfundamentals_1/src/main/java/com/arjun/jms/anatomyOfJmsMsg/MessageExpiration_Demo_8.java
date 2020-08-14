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

public class MessageExpiration_Demo_8 {

	public static void main(String[] args) throws NamingException, InterruptedException, JMSException {

		InitialContext context = new InitialContext();
		Queue queue = (Queue) context.lookup("queue/myQueue");

		try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext = cf.createContext()) {

			JMSProducer producer = jmsContext.createProducer();
		 	producer.setTimeToLive(2000);
			producer.send(queue, "Hello World");
			Thread.sleep(5000);

			JMSConsumer consumer = jmsContext.createConsumer(queue);

			Message messageReceived = (TextMessage) consumer.receive(5000);

			System.out.println("Message received : " + messageReceived);

		}

	}
}


/*
 *  output is null
 *  if we don't set "setTimeToLive" then output will be message
 *    
 * 
*/
