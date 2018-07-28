package com.jyh.app.plat.console.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import com.rabbitmq.client.Channel;

/**
 * @author jiangyonghua
 * @date 2018年3月15日 下午2:32:36
 */
//@Configuration
public class AmqpConfig {

	private static final Logger log = LoggerFactory.getLogger(AmqpConfig.class);

	@Value("${spring.rabbitmq.host}")
	private String host;

	@Value("${spring.rabbitmq.port}")
	private String port;

	@Value("${spring.rabbitmq.username}")
	private String username;

	@Value("${spring.rabbitmq.password}")
	private String password;

	public static final String QUEUE_NAME = "app_config";

	@Bean
	public CachingConnectionFactory conn() {
		CachingConnectionFactory conn = new CachingConnectionFactory(host, Integer.valueOf(port));
		conn.setUsername(username);
		conn.setPassword(password);
		return conn;
	}

	@Bean
	public SimpleMessageListenerContainer messageContainer() {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(conn());
		container.setQueues(queue());
		container.setExposeListenerChannel(true);
		container.setMaxConcurrentConsumers(1);
		container.setConcurrentConsumers(1);
		container.setAcknowledgeMode(AcknowledgeMode.MANUAL); // 设置确认模式手工确认
		container.setMessageListener(new ChannelAwareMessageListener() {

			@Override
			public void onMessage(Message message, Channel channel) throws Exception {
				log.info("收到消息：" + new String(message.getBody()));
				channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); // 确认消息成功消费
			}

		});
		return container;
	}

	@Bean
	public Queue queue() {
		return QueueBuilder.durable(QUEUE_NAME).build();
	}
}
