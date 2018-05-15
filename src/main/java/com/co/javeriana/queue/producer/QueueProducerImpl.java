package com.co.javeriana.queue.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

@Component
public class QueueProducerImpl {
	
	private static final Logger log = LoggerFactory.getLogger(QueueProducerImpl.class);
	
	@Autowired
	private Environment env;
	
	public ConnectionFactory configureConnection() {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUsername(env.getProperty("rabbitMQ.username"));
		factory.setPassword(env.getProperty("rabbitMQ.password"));
		factory.setHost(env.getProperty("rabbitMQ.host"));
		factory.setPort(Integer.parseInt(env.getProperty("rabbitMQ.port")));
		return factory;
	}
	
	public void publishMessage(String message) {
		try {
			/* configura la conexion al servidor de colas */
			ConnectionFactory factory = configureConnection();
			/* abre la conexion */
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();
			/* declara la cola a la cual conectarse */
			channel.queueDeclare(env.getProperty("rabbitMQ.queuename"), true, false, false, null);		
			channel.basicPublish("", env.getProperty("rabbitMQ.queuename"), null, message.getBytes("UTF-8"));
		    /* cierra la conexion a la cola */
		    channel.close();
		    connection.close();
		}catch(Exception e) {
			e.printStackTrace();			
		}
	}	

}
