package com.arjun.jms.anatomyOfJmsMsg;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class RequestReplyDemo_4 {

	public static void main(String[] args) throws NamingException {

		InitialContext context = new InitialContext();
		Queue queue = (Queue) context.lookup("queue/requestQueue");
		Queue replyQueue = (Queue) context.lookup("queue/replyQueue");

		try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext = cf.createContext()) {

			// Step-1
			JMSProducer producer = jmsContext.createProducer();
			producer.send(queue, "Hello World");

			// Step-2
			JMSConsumer consumer = jmsContext.createConsumer(queue);
			String messageReceived = consumer.receiveBody(String.class);
			System.out.println("Message received : " + messageReceived);

			// Step-3
			JMSProducer replyProducer = jmsContext.createProducer();
			replyProducer.send(replyQueue, "You are Awesome!!");

			// Step-4
			JMSConsumer replyConsumer = jmsContext.createConsumer(replyQueue);
			System.out.println("Message received : " + replyConsumer.receiveBody(String.class));

		}

	}

}

// [ Step-1 and Step-2 can be a part of App-1 ] and [ Step-2 and step-3 can be part of App-2 ]
