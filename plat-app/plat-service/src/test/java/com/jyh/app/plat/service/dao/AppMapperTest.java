package com.jyh.app.plat.service.dao;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.jyh.entity.plat.service.AppEntity;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppMapperTest {

	private static final Logger log = LoggerFactory.getLogger(AppMapperTest.class);

	@Autowired
	private AppMapper appMapper;

	@Test
	@Ignore
	public void selectAll() {
		List<AppEntity> apps = appMapper.selectAll();
		log.info(JSON.toJSONString(apps));
	}

	@Test
	@Ignore
	public void select() {
		AppEntity app = new AppEntity();
		app.setAppid("plat_console");
		List<AppEntity> apps = appMapper.select(app);
		log.info(JSON.toJSONString(apps));
	}

}
