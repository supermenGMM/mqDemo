package com.mm.rabbit.queues;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class NewTask {
	private static final String TASK_QUEUE="task_queue";
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(TASK_QUEUE,true,false,false,null);
		//分发消息
		for (int i = 0; i < 5; i++) {
			String message = "hello world"+i;
			channel.basicPublish("", TASK_QUEUE, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
			System.out.println("[x] sent '"+message+"'");
		}
		channel.close();
		connection.close();
		
	}
}
