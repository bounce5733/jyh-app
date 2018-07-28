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
import com.jyh.entity.plat.service.AppTypeEntity;
import com.jyh.util.common.IDGenUtil;
import com.jyh.util.common.TimeUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTypeMapperTest {

	private static final Logger log = LoggerFactory.getLogger(AppTypeMapperTest.class);

	@Autowired
	private AppTypeMapper appTypeMapper;

	@Test
	@Ignore
	public void selectAll() {
		List<AppTypeEntity> types = appTypeMapper.select(new AppTypeEntity());
		log.info(JSON.toJSONString(types));
	}

	@Test
	@Ignore
	public void insertType() {
		AppTypeEntity type = new AppTypeEntity();
		type.setId(IDGenUtil.UUID());
		type.setName("测试类型");
		type.setDescn("测试描述");
		type.setCreatedBy("2324324");
		type.setCreatedTime(TimeUtil.getTime());
		appTypeMapper.insertSelective(type);
	}

	@Test
	@Ignore
	public void updateType() {
		AppTypeEntity type = new AppTypeEntity();
		type.setId("7cf26ff3419d41b9a2e5c8852ab99fd1");
		type.setName("测试类型11");
		type.setDescn("测试描述11");
		type.setUpdatedBy("2324324");
		type.setUpdatedTime(TimeUtil.getTime());

		appTypeMapper.updateByPrimaryKeySelective(type);
	}

	@Test
	@Ignore
	public void deleteType() {
		appTypeMapper.deleteByPrimaryKey("7cf26ff3419d41b9a2e5c8852ab99fd1");
	}

}
