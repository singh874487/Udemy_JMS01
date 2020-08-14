package com.arjun.jms.anatomyOfJmsMsg;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MessageTypes_ObjectMessage_Demo_16 {

	public static void main(String[] args) throws NamingException, JMSException {

		InitialContext context = new InitialContext();
		Queue queue = (Queue) context.lookup("queue/myQueue");

		try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmsContext = cf.createContext()) {

			JMSProducer producer = jmsContext.createProducer();
			Patient patient = new Patient();
			patient.setId(123);
			patient.setName("Aman");

			producer.send(queue, patient);

			JMSConsumer consumer = jmsContext.createConsumer(queue);
			Patient receivedObject = consumer.receiveBody(Patient.class);

			System.out.println("Message received : " + receivedObject.getId());
			System.out.println("Message received : " + receivedObject.getName());

		}

	}
}

/*
 * Not required to create the object message , directly we can send the Serializable object
 * 
 */
