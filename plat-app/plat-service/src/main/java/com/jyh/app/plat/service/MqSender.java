package com.jyh.app.plat.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.alibaba.fastjson.JSON;

/**
 * @author jiangyonghua
 * @date 2018年3月16日 下午2:19:19
 */
// @Component
public class MqSender implements ConfirmCallback {

	private static final Logger log = LoggerFactory.getLogger(MqSender.class);

	@Autowired
	@Qualifier("MyRabbitTemplate")
	private RabbitTemplate rabbitTemplate;

	public void send(Map<String, Object> obj) {
		// CorrelationData correlationId = new
		// CorrelationData(UUID.randomUUID().toString());
		log.info("发送消息：" + JSON.toJSONString(obj));
		rabbitTemplate.convertAndSend(obj);
	}

	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		log.info("确认消息：" + correlationData.getId());
		if (ack) {
			log.info("消息成功消费...");
		} else {
			log.info("消息消费失败：" + cause);
		}
	}
}
