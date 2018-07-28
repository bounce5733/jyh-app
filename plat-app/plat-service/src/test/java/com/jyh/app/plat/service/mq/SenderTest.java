package com.jyh.app.plat.service.mq;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jyh.app.plat.service.MqSender;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SenderTest {

	@Autowired
	private MqSender sender;

	@Test
	@Ignore
	public void send() throws InterruptedException {
		Map<String, Object> user = new HashMap<String, Object>();
		user.put("id", 123);
		user.put("name", "测试用户");
		sender.send(user);

		CountDownLatch downLatch = new CountDownLatch(1);
		downLatch.await();
	}
}
