package com.jyh.app.plat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// @DubboComponentScan(basePackages = "com.jyh.plat.console.service")
public class App {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(App.class);
		// app.addListeners(new ConfigLoadListener(AppConst.APPID));
		app.run(args);
	}

}