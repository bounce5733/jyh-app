package com.jyh.app.plat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import com.jyh.app.plat.attach.AppConst;
import com.jyh.app.plat.common.ConfigLoadListener;

@SpringBootApplication
@ServletComponentScan
public class App {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(App.class);
		app.addListeners(new ConfigLoadListener(AppConst.APPID));
		app.run(args);
	}

}