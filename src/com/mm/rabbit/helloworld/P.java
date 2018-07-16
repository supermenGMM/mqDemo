package com.mm.rabbit.helloworld;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class P {
	private final static String QUEUE_NAME="hello";

	public static void main(String[] args) throws IOException, TimeoutException {
//		创建连接工厂
		ConnectionFactory connectionFactory = new ConnectionFactory();
//		设置rabbitmq地址
		connectionFactory.setHost("localhost");
		
//		创建一个新连接
		Connection connection = connectionFactory.newConnection();
		
//		创建一个频道
		Channel channel = connection.createChannel();
//		声明一个队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//		发送消息到队列中
		String message = "hello wolrd";
		channel.basicPublish("", QUEUE_NAME, null, message.getBytes("utf-8"));
//		关闭频道和连接
		channel.close();
		connection.close();
		
	}
}
