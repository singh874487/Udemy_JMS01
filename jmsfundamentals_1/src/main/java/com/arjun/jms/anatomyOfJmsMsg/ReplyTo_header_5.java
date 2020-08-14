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

public class ReplyTo_header_5 {

	public static void main(String[] args) throws NamingException, JMSException {

		InitialContext context = new InitialContext();
		Queue queue = (Queue) context.lookup("queue/requestQueue");
		Queue replyQueue = (Queue) context.lookup("queue/replyQueue");

		try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext = cf.createContext()) {

			// Step-1
			JMSProducer producer = jmsContext.createProducer();
			TextMessage message = jmsContext.createTextMessage("Hello World");
			message.setJMSReplyTo(replyQueue);
			producer.send(queue, message);

			// Step-2
			JMSConsumer consumer = jmsContext.createConsumer(queue);
			TextMessage messageReceived = (TextMessage) consumer.receive();
			System.out.println("Message received : " + messageReceived.getText());

			// Step-3
			JMSProducer replyProducer = jmsContext.createProducer();
			replyProducer.send(messageReceived.getJMSReplyTo(), "You are Awesome!!");

			// Step-4
			JMSConsumer replyConsumer = jmsContext.createConsumer(replyQueue);
			System.out.println("Message received : " + replyConsumer.receiveBody(String.class));

		}

	}

}

// [ Step-1 and Step-2 can be a part of App-1 ] and [ Step-2 and step-3 can be part of App-2 ]
