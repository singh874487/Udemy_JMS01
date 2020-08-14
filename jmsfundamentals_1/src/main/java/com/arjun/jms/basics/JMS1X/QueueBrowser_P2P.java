package com.arjun.jms.basics.JMS1X;

import java.util.Enumeration;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class QueueBrowser_P2P {

	public static void main(String[] args) {

		InitialContext initialContext = null;
		Connection connection = null;

		try {
			// get resources from JNDI
			initialContext = new InitialContext();
			Queue queue = (Queue) initialContext.lookup("queue/myQueue");
			ConnectionFactory cf = (ConnectionFactory) initialContext.lookup("ConnectionFactory");

			// create connection and session
			connection = cf.createConnection();
			Session session = connection.createSession();

			// create producer, messages and consumer
			MessageProducer producer = session.createProducer(queue);
			TextMessage message_1 = session.createTextMessage("Hello World 1");
			TextMessage message_2 = session.createTextMessage("Hello World 2");
			TextMessage message_3 = session.createTextMessage("Hello World 3");
			producer.send(message_1);
			producer.send(message_2);
			producer.send(message_3);

			QueueBrowser brower = session.createBrowser(queue);
			Enumeration messageEnum = brower.getEnumeration();

			while (messageEnum.hasMoreElements()) {
				TextMessage eachMessage = (TextMessage) messageEnum.nextElement();
				System.out.println("eachMessage : " + eachMessage.getText());
			}

			MessageConsumer consumer = session.createConsumer(queue);

			connection.start();
			TextMessage messageReceive_1 = (TextMessage) consumer.receive(5000);
			TextMessage messageReceive_2 = (TextMessage) consumer.receive(5000);
			TextMessage messageReceive_3 = (TextMessage) consumer.receive(5000);

			System.out.println("Message Received " + messageReceive_1.getText());
			System.out.println("Message Received " + messageReceive_2.getText());
			System.out.println("Message Received " + messageReceive_3.getText());

		} catch (NamingException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			if (initialContext != null) {
				try {
					initialContext.close();
				} catch (NamingException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
