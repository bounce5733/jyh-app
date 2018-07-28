package com.jyh.app.plat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// @DubboComponentScan(basePackages = "com.jyh.plat.app.service")
public class App {

//	@Autowired
//	private ConfigServer configServer;

//	@PostConstruct
//	public void startConfigServer() {
//		log.info("启动模块-->>[应用配置Socket服务]");
//		new Thread(configServer).start();
//	}

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}