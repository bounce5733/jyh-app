package com.jyh.app.plat.attach;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Context {

	private static final Logger log = LoggerFactory.getLogger(Context.class);

	@Value("${app.dfs.tracker_servers}")
	private String trackerServers;

	@PostConstruct
	public void initDfsClient() {
		try {
			ClientGlobal.initByTrackers(trackerServers);
		} catch (IOException | MyException e) {
			log.error("文件服务器客户端初始化失败！", e.getMessage());
		}
	}

}
