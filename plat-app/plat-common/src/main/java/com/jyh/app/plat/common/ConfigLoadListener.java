package com.jyh.app.plat.common;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ByteArrayResource;

import com.jyh.util.common.StringUtil;
import com.jyh.util.conf.ConfClient;

/**
 * 配置加载监听器
 * 
 * @author jiangyonghua
 * @date 2018年2月23日 上午9:52:03
 */
public class ConfigLoadListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

	private static final Logger log = LoggerFactory.getLogger(ConfigLoadListener.class);

	private static final String DEV_PROFILES = "dev";

	private static final String PROD_PROFILES = "prod";

	private String appid;

	public ConfigLoadListener(String appid) {
		this.appid = appid;
	}

	@Override
	public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
		log.info("加载应用配置...");
		YamlPropertySourceLoader yamlLoader = new YamlPropertySourceLoader();
		byte[] yamlConf = ConfClient.getYaml(appid);
		String profile;
		if (yamlConf != null) {
			try {
				List<PropertySource<?>> configs = yamlLoader.load(appid, new ByteArrayResource(yamlConf));
				if (configs == null || configs.size() != 3) {
					log.error("配置应不完整，请检查！");
					System.exit(-1);
				}

				profile = (String) event.getEnvironment().getPropertySources().get("systemEnvironment")
						.getProperty("SPRING_PROFILES_ACTIVE");
				if (StringUtil.isEmpty(profile)) {
					profile = String.valueOf(configs.get(0).getProperty("spring.profiles.active"));
				}

				event.getEnvironment().getPropertySources()
						.addLast(yamlLoader.load(appid + "_common", new ByteArrayResource(yamlConf)).get(0));

				if (StringUtil.isNotEmpty(profile) && profile.equalsIgnoreCase(DEV_PROFILES)) {
					event.getEnvironment().getPropertySources()
							.addLast(yamlLoader.load(appid + "_" + profile, new ByteArrayResource(yamlConf)).get(1));
				} else if (StringUtil.isNotEmpty(profile) && profile.equalsIgnoreCase(PROD_PROFILES)) {
					event.getEnvironment().getPropertySources()
							.addLast(yamlLoader.load(appid + "_" + profile, new ByteArrayResource(yamlConf)).get(2));
				} else {
					log.error("请配置正确的[profiles:dev|prod]");
					System.exit(-1);
				}

			} catch (IOException e) {
				log.info("加载应用配置出错，启动终止！", e);
				System.exit(-1);
			}
		} else {
			log.error("未获取应用配置，启动终止！");
			System.exit(0);
		}

		log.info("应用配置加载完成...");
	}

}
