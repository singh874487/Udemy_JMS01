package com.arjun.jms.anatomyOfJmsMsg;

import javax.jms.BytesMessage;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MessageTypes_BytesMessage_Demo_12 {

	public static void main(String[] args) throws NamingException, JMSException {

		InitialContext context = new InitialContext();
		Queue queue = (Queue) context.lookup("queue/myQueue");

		try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext = cf.createContext()) {

			JMSProducer producer = jmsContext.createProducer();
			BytesMessage bytesMessage = jmsContext.createBytesMessage();
			bytesMessage.writeUTF("Arun");
			bytesMessage.writeLong(12345);

			producer.send(queue, bytesMessage);

			JMSConsumer consumer = jmsContext.createConsumer(queue);
			BytesMessage messageReceived = (BytesMessage) consumer.receive(5000);

			System.out.println("Message received : " + messageReceived.readUTF());
			System.out.println("Message received : " + messageReceived.readLong());

		}

	}
}

/*
 * 
 */
