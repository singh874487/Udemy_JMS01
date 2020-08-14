package com.arjun.jms.basics.JMS1X;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class P2PExample {

	public static void main(String[] args) {

		InitialContext initialContext = null;
		Connection connection = null;

		try {
			initialContext = new InitialContext();

			Queue queue = (Queue) initialContext.lookup("queue/myQueue");

			ConnectionFactory cf = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
			connection = cf.createConnection();
			Session session = connection.createSession();

			MessageProducer producer = session.createProducer(queue);

			TextMessage message = session.createTextMessage("Hello World");
			producer.send(message);
			System.out.println("Message Sent " + message.getText());

			MessageConsumer consumer = session.createConsumer(queue);

			connection.start();
			TextMessage messageReceive = (TextMessage) consumer.receive(5000);
			System.out.println("Message Received " + messageReceive.getText());

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
