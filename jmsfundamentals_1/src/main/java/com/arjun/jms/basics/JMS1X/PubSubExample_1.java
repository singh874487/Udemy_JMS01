package com.arjun.jms.basics.JMS1X;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;

public class PubSubExample_1 {

	public static void main(String[] args) throws Exception {

		InitialContext initialContext = new InitialContext();
		Topic topic = (Topic) initialContext.lookup("topic/myTopic");

		ConnectionFactory cf = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
		Connection connection = cf.createConnection();
		
		// create producer and consumer
		Session session = connection.createSession();
		MessageProducer producer = session.createProducer(topic);
		MessageConsumer consumer_1 = session.createConsumer(topic);
		MessageConsumer consumer_2 = session.createConsumer(topic);
		
		// message creation
		TextMessage message = session.createTextMessage("Hello World");
		producer.send(message);
		System.out.println("Message Sent to Topic : " + message.getText());
		connection.start();
		
		// message receive
		TextMessage messageReceive_1 = (TextMessage) consumer_1.receive(5000);
		System.out.println("Message Received from consumer 1 : " + messageReceive_1.getText());
		
		TextMessage messageReceive_2 = (TextMessage) consumer_2.receive(5000);
		System.out.println("Message Received from consumer 2 : " + messageReceive_2.getText());
		
		
		// close connection
		
		connection.close();
		initialContext.close();

	}

}
