package com.jyh.app.plat.service.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Binding.DestinationType;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;

/**
 * @author jiangyonghua
 * @date 2018年3月15日 下午2:32:36
 */
@Configuration
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

	public static final String EXCHANGE_NAME = "app.config";

	public static final String ROUTING_KEY = "app.config";

	public static final String QUEUE_NAME = "app_config";

	@Bean
	public CachingConnectionFactory conn() {
		CachingConnectionFactory conn = new CachingConnectionFactory(host, Integer.valueOf(port));
		conn.setUsername(username);
		conn.setPassword(password);
		conn.setPublisherReturns(true);
		conn.setPublisherConfirms(true);
		return conn;
	}

	@Bean
	RetryTemplate retryTemplate() {
		RetryTemplate retryTemplate = new RetryTemplate();
		ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
		backOffPolicy.setInitialInterval(500);
		backOffPolicy.setMultiplier(10.0);
		backOffPolicy.setMaxInterval(10000);
		retryTemplate.setBackOffPolicy(backOffPolicy);
		return retryTemplate;
	}

	@Bean("MyRabbitTemplate")
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate template = new RabbitTemplate(conn());
		template.setRetryTemplate(retryTemplate());
		template.setMandatory(true);
		template.setExchange(EXCHANGE_NAME);
		template.setRoutingKey(ROUTING_KEY);
		template.setMessageConverter(new Jackson2JsonMessageConverter());
		template.setConfirmCallback(new ConfirmCallback() {

			@Override
			public void confirm(CorrelationData correlationData, boolean ack, String cause) {
				log.info("confirm ack:" + ack);
			}

		});

		template.setRecoveryCallback(new RecoveryCallback<Object>() {

			@Override
			public Object recover(RetryContext context) throws Exception {
				log.info("recover:" + context.toString());
				return null;
			}

		});

		template.setReturnCallback(new ReturnCallback() {

			@Override
			public void returnedMessage(Message message, int replyCode, String replyText, String exchange,
					String routingKey) {
				log.info("return callback:" + replyCode);
			}

		});
		return template;
	}

	@Bean
	public Queue queue() {
		return QueueBuilder.durable(QUEUE_NAME).build();
	}

	@Bean
	public Exchange exchange() {
		return ExchangeBuilder.directExchange(EXCHANGE_NAME).build();
	}

	@Bean
	public Binding binding() {
		Binding binding = new Binding(QUEUE_NAME, DestinationType.QUEUE, EXCHANGE_NAME, ROUTING_KEY, null);
		return binding;
	}

}
