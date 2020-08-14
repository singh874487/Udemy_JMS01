package com.arjun.jms.anatomyOfJmsMsg;

import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.jms.TemporaryQueue;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MessageId_CorrelationId_Header_7 {

	public static void main(String[] args) throws NamingException, JMSException {

		InitialContext context = new InitialContext();
		Queue queue = (Queue) context.lookup("queue/requestQueue");
		// Queue replyQueue = (Queue) context.lookup("queue/replyQueue");

		Map<String, TextMessage> requestMessages = new HashMap<>();

		try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext = cf.createContext()) {

			// Step-1
			JMSProducer producer = jmsContext.createProducer();
			// create a temporary queue
			TemporaryQueue replyQueue = jmsContext.createTemporaryQueue();
			TextMessage message = jmsContext.createTextMessage("Hello World");
			message.setJMSReplyTo(replyQueue);
			producer.send(queue, message);
			System.out.println(message.getJMSMessageID());

			// Add message details to Map
			requestMessages.put(message.getJMSMessageID(), message);

			// Step-2
			JMSConsumer consumer = jmsContext.createConsumer(queue);
			TextMessage messageReceived = (TextMessage) consumer.receive();
			System.out.println("Message received : " + messageReceived.getText());
			System.out.println("Message received contains reply queue name : " + messageReceived.getJMSReplyTo());

			// Step-3
			JMSProducer replyProducer = jmsContext.createProducer();
			TextMessage replyMessage = jmsContext.createTextMessage("Hello World");
			replyMessage.setJMSCorrelationID(messageReceived.getJMSMessageID());

			replyProducer.send(messageReceived.getJMSReplyTo(), replyMessage);

			// Step-4
			JMSConsumer replyConsumer = jmsContext.createConsumer(replyQueue);
			TextMessage replyReceived = (TextMessage) replyConsumer.receive();
			System.out.println("Reply received Message : " + replyReceived.getText());
			System.out.println("Reply received Message has CorrelationID : " + replyReceived.getJMSCorrelationID());

			// link the request and reply messages
			System.out.println(requestMessages.get(replyMessage.getJMSCorrelationID()).getText());

		}

	}

}

// please check why this prog not working for temporary queue
// [ Step-1 and Step-2 can be a part of App-1 ] and [ Step-2 and step-3 can be part of App-2 ]
