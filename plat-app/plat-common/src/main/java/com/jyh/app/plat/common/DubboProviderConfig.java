package com.jyh.app.plat.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;

/**
 * @author jiangyonghua
 * @date 2018年3月2日 上午9:55:21
 */
//@Configuration
public class DubboProviderConfig {

	@Value("${spring.application.name}")
	private String dubboProviderName;

	@Value("${dubbo.zookeeper.ip}")
	private String zookeeperIP;

	@Value("${dubbo.zookeeper.port}")
	private String zookeeperPort;

	@Value("${server.port}")
	private String serverPort;

	@Bean
	public ApplicationConfig applicationConfig() {
		ApplicationConfig applicationConfig = new ApplicationConfig();
		applicationConfig.setName(dubboProviderName);
		return applicationConfig;
	}

	@Bean
	public ProtocolConfig protocolConfig() {
		ProtocolConfig protocolConfig = new ProtocolConfig();
		protocolConfig.setPort(20000 + Integer.valueOf(serverPort));
		return protocolConfig;
	}

	@Bean
	public RegistryConfig registryConfig() {
		RegistryConfig registryConfig = new RegistryConfig();
		registryConfig.setAddress("zookeeper://" + zookeeperIP + ":" + zookeeperPort);
		return registryConfig;
	}
}
