package com.jyh.app.plat.service.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.jyh.api.plat.service.AppService;
import com.jyh.app.plat.service.conf.KafkaConfig;
import com.jyh.app.plat.service.dao.ApiMapper;
import com.jyh.app.plat.service.dao.AppMapper;
import com.jyh.app.plat.service.dao.AppTypeMapper;
import com.jyh.app.plat.service.dao.ServiceMapper;
import com.jyh.entity.plat.service.ApiEntity;
import com.jyh.entity.plat.service.AppEntity;
import com.jyh.entity.plat.service.AppTypeEntity;
import com.jyh.entity.plat.service.ServiceEntity;

/**
 * @author jiangyonghua
 * @date 2018年3月5日 下午3:21:39
 */
@Service
public class AppServiceImpl implements AppService {

	private static final Logger log = LoggerFactory.getLogger(AppServiceImpl.class);

	@Autowired
	private ServiceMapper serviceMapper;

	@Autowired
	private ApiMapper apiMapper;

	@Autowired
	private AppMapper appMapper;

	@Autowired
	private AppTypeMapper appTypeMapper;

	// @Autowired
	// private MqSender mqSender;

	/**
	 * 应用缓存
	 */
	private static final Map<String, AppEntity> APP_CACHE_MAP = new HashMap<String, AppEntity>();

	@Autowired
	private KafkaTemplate<Integer, String> template;

	// @KafkaListener(topics = { KafkaConfig.TOPIC_NAME })
	// public void listen(ConsumerRecord<Integer, String> record) {
	// Optional<String> kafkaMessage = Optional.ofNullable(record.value());
	// if (kafkaMessage.isPresent()) {
	// log.info("收到：" + kafkaMessage.get());
	// }
	// }

	// public void send(Map<String, Object> obj) {
	// mqSender.send(obj);
	// }

	public void sendKfkMsg(String msg) {
		template.send(KafkaConfig.TOPIC_NAME, msg);
		template.flush();
	}

	@Cacheable("aux_platform_app_apptree")
	public Map<String, AppEntity> loadApp() {
		log.info("缓存应用...");
		APP_CACHE_MAP.clear();
		List<AppEntity> apps = appMapper.selectAll();
		final List<ServiceEntity> services = serviceMapper.selectAll();
		final List<ApiEntity> apis = apiMapper.selectAll();
		apps.forEach(new Consumer<AppEntity>() {
			public void accept(final AppEntity app) {
				services.forEach(new Consumer<ServiceEntity>() {
					public void accept(final ServiceEntity service) {
						if (service.getAppId().equals(app.getId())) {
							app.getServices().add(service);
							apis.forEach(new Consumer<ApiEntity>() {
								public void accept(ApiEntity api) {
									if (api.getServiceId().equals(service.getId())) {
										service.getApis().add(api);
									}
								}
							});
						}
					}
				});
				APP_CACHE_MAP.put(app.getAppid(), app);
			}
		});
		log.info("完成缓存应用...");
		return APP_CACHE_MAP;
	}

	public List<AppTypeEntity> loadTree() {
		List<AppTypeEntity> apptypes = appTypeMapper.select(new AppTypeEntity());
		final List<AppEntity> apps = appMapper.selectAll();
		final List<ServiceEntity> services = serviceMapper.selectAll();
		final List<ApiEntity> apis = apiMapper.selectAll();
		apps.forEach(new Consumer<AppEntity>() {
			public void accept(final AppEntity app) {
				services.forEach(new Consumer<ServiceEntity>() {
					public void accept(final ServiceEntity service) {
						if (service.getAppId().equals(app.getId())) {
							app.getServices().add(service);
							apis.forEach(new Consumer<ApiEntity>() {
								public void accept(ApiEntity api) {
									if (api.getServiceId().equals(service.getId())) {
										service.getApis().add(api);
									}
								}
							});
						}
					}
				});
			}
		});
		// 组装树
		apptypes.forEach(new Consumer<AppTypeEntity>() {
			public void accept(final AppTypeEntity type) {
				apps.forEach(new Consumer<AppEntity>() {
					public void accept(AppEntity app) {
						if (type.getId().equals(app.getOwner())) {
							type.getChildren().add(app);
						}
					}
				});
			}
		});
		return apptypes;
	}

}
