package com.jyh.app.plat.console.mq;

import java.util.concurrent.CountDownLatch;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReceiverTest {

	@Test
	@Ignore
	public void receive() throws InterruptedException {
		CountDownLatch latch = new CountDownLatch(1);
		latch.await();
	}
}
