package com.arjun.jms.anatomyOfJmsMsg;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MessageTypes_ObjectMessage_Demo_15 {

	public static void main(String[] args) throws NamingException, JMSException {

		InitialContext context = new InitialContext();
		Queue queue = (Queue) context.lookup("queue/myQueue");

		try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext = cf.createContext()) {

			JMSProducer producer = jmsContext.createProducer();
			ObjectMessage objectMessage = jmsContext.createObjectMessage();
			Patient patient = new Patient();
			patient.setId(123);
			patient.setName("Aman");
			objectMessage.setObject(patient);

			producer.send(queue, objectMessage);

			JMSConsumer consumer = jmsContext.createConsumer(queue);
			ObjectMessage messageReceived = (ObjectMessage) consumer.receive(5000);

			Patient receivedObject = (Patient) messageReceived.getObject();
			System.out.println("Message received : " + receivedObject.getId());
			System.out.println("Message received : " + receivedObject.getName());

		}

	}
}

/*
 * 
 */
