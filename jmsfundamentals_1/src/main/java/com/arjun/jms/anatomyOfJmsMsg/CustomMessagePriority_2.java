package com.arjun.jms.anatomyOfJmsMsg;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class CustomMessagePriority_2 {

	public static void main(String[] args) throws NamingException, JMSException {

		InitialContext context = new InitialContext();
		Queue queue = (Queue) context.lookup("queue/myQueue");

		try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext = cf.createContext()) {

			JMSProducer producer = jmsContext.createProducer();

			String[] messages = new String[3];
			messages[0] = "Message One";
			messages[1] = "Message Two";
			messages[2] = "Message Three";

			producer.setPriority(3);
			producer.send(queue, messages[0]);

			producer.setPriority(1);
			producer.send(queue, messages[1]);

			producer.setPriority(9);
			producer.send(queue, messages[2]);

			JMSConsumer consumer = jmsContext.createConsumer(queue);

			for (int i = 0; i < messages.length; i++) {

				System.out.println("Message received : " + consumer.receiveBody(String.class));
			}

		}

	}

}

/*
 * 10 is Highest priority
 * 
 * 0 is low priority
 * 
 */
