package com.jyh.app.plat.service.conf;

import java.nio.charset.Charset;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.jyh.app.plat.common.SessionConfig;
import com.jyh.app.plat.common.SysConst;
import com.jyh.app.plat.service.AppConst;

/**
 * Spring MVC 配置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	// 使用阿里 FastJson 作为JSON MessageConverter
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
		FastJsonConfig config = new FastJsonConfig();
		config.setSerializerFeatures(SerializerFeature.WriteMapNullValue, // 保留空的字段
				SerializerFeature.WriteNullStringAsEmpty, // String null -> ""
				SerializerFeature.WriteNullNumberAsZero);// Number null -> 0
		converter.setFastJsonConfig(config);
		converter.setDefaultCharset(Charset.forName("UTF-8"));
		converters.add(converter);
	}

	// 解决跨域问题
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*")
				.allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "TRACE", "OPTIONS", "PATCH")
				.allowedHeaders("X-Requested-With", "accept", "content-type", SessionConfig.SESSION_KEY)
				.exposedHeaders(SessionConfig.SESSION_KEY, SessionConfig.USER_NAME_KEY).allowCredentials(false)
				.maxAge(1800);
	}

	// 添加拦截器
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new HandlerInterceptorAdapter() {
			@Override
			public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
					throws Exception {
				request.setCharacterEncoding(SysConst.ENCODING);
				response.setCharacterEncoding(SysConst.ENCODING);

				if (!AppConst.AUTH_SKIP_URI.contains(request.getServletPath())
						&& !request.getMethod().equalsIgnoreCase("OPTIONS")) {
					// ==========检查会话==========
					if (request.getSession(false) == null) {
						responseResult(response, HttpStatus.UNAUTHORIZED.value());
						return false;
					}
				}

				return true;
			}
		});
	}

	/**
	 * 返回头
	 * 
	 * @param response
	 * @param status
	 */
	private void responseResult(HttpServletResponse response, int status) {
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-type", "application/json;charset=UTF-8");
		response.setStatus(status);
	}

}
