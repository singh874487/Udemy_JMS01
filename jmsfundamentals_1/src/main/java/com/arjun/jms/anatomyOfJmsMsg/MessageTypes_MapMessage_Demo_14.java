package com.arjun.jms.anatomyOfJmsMsg;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.MapMessage;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MessageTypes_MapMessage_Demo_14 {

	public static void main(String[] args) throws NamingException, JMSException {

		InitialContext context = new InitialContext();
		Queue queue = (Queue) context.lookup("queue/myQueue");

		try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext = cf.createContext()) {

			JMSProducer producer = jmsContext.createProducer();
			MapMessage mapMessage = jmsContext.createMapMessage();
			mapMessage.setBoolean("isCreditAvailable", true);
			mapMessage.setInt("Id", 546);

			producer.send(queue, mapMessage);

			JMSConsumer consumer = jmsContext.createConsumer(queue);
			MapMessage messageReceived = (MapMessage) consumer.receive(5000);

			System.out.println("Message received : " + messageReceived.getBoolean("isCreditAvailable"));
			System.out.println("Message received : " + messageReceived.getInt("Id"));

		}

	}
}

/*
 * 
 */
