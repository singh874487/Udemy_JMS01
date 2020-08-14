package com.arjun.jms.anatomyOfJmsMsg;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.jms.StreamMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MessageTypes_StreamMessage_Demo_13 {

	public static void main(String[] args) throws NamingException, JMSException {

		InitialContext context = new InitialContext();
		Queue queue = (Queue) context.lookup("queue/myQueue");

		try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext = cf.createContext()) {

			JMSProducer producer = jmsContext.createProducer();
			StreamMessage streamMessage = jmsContext.createStreamMessage();
			streamMessage.writeBoolean(true);
			streamMessage.writeFloat(123456);

			producer.send(queue, streamMessage);

			JMSConsumer consumer = jmsContext.createConsumer(queue);
			StreamMessage messageReceived = (StreamMessage) consumer.receive(5000);

			System.out.println("Message received : " + messageReceived.readBoolean());
			System.out.println("Message received : " + messageReceived.readFloat());

		}

	}
}

/*
 * 
 */
