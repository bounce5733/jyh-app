package com.jyh.app.plat.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.RegistryConfig;

/**
 * @author jiangyonghua
 * @date 2018年3月5日 下午2:19:29
 */
//@Configuration
public class DubboConsumeConfig {

	@Value("${spring.application.name}")
	private String dubboConsumeName;

	@Value("${dubbo.zookeeper.ip}")
	private String zookeeperIP;

	@Value("${dubbo.zookeeper.port}")
	private String zookeeperPort;

	@Bean
	public ApplicationConfig applicationConfig() {
		ApplicationConfig applicationConfig = new ApplicationConfig();
		applicationConfig.setName(dubboConsumeName);
		return applicationConfig;
	}

	@Bean
	public ConsumerConfig consumerConfig() {
		ConsumerConfig consumerConfig = new ConsumerConfig();
		consumerConfig.setTimeout(3000);
		return consumerConfig;
	}

	@Bean
	public RegistryConfig registryConfig() {
		RegistryConfig registryConfig = new RegistryConfig();
		registryConfig.setAddress("zookeeper://" + zookeeperIP + ":" + zookeeperPort);
		return registryConfig;
	}
}
