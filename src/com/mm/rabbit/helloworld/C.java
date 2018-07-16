package com.mm.rabbit.helloworld;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

public class C {
	private final static String QUEUE="hello";	
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(QUEUE, false, false, false,null );
		System.out.println("C [*] Waiting for message , to exit press CTRL+C");
//		DefaultConsumer实现了consumer接口。通过传入频道，告诉服务器我们需要哪个频道的消息。如果频道中有消息，就会执行回调函数
		Consumer consumer = new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,
					BasicProperties properties, byte[] body) throws IOException {
				String message = new String(body, "utf-8");
				System.out.println("C [x] receive :"+message);
			}
		};
//		自动回复队列应答
		channel.basicConsume(QUEUE, true,consumer);
	}
}
