package com.jyh.app.plat.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

@Configuration
@EnableRedisHttpSession
public class SessionConfig {

	public static final String SESSION_KEY = "X-Auth-Token";

	public static final String USER_ID_KEY = "userid";

	public static final String USER_NAME_KEY = "username";
	
	public static final String USER_ACCOUNT_KEY = "account";

	@Bean
	public HttpSessionIdResolver httpSessionIdResolver() {
		return HeaderHttpSessionIdResolver.xAuthToken();
	}
}
