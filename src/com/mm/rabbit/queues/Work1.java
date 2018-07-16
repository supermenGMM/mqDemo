package com.mm.rabbit.queues;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

public class Work1 {
	private static final String TASK_QUEUE="task_queue";
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		final Channel channel = connection.createChannel();
		
		channel.queueDeclare(TASK_QUEUE, false, false, false, null);
		System.out.println("worker1 [*] wating for message .to exit press CTRL+C");
//		每次从队列中获取数量
		channel.basicQos(1);
		
		Consumer consumer = new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,
					BasicProperties properties, byte[] body) throws IOException {
				String message = new String(body, "utf-8");
				try {
					doWork(message);
				} finally {
//					消息处理完成确认
					System.out.println("worker1 [x] Done");
					channel.basicAck(envelope.getDeliveryTag(), false);
				}
			}
			
		};
//		消费完成确认
		channel.basicConsume(TASK_QUEUE, false,consumer);
	}
	
	private static void doWork(String message) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
