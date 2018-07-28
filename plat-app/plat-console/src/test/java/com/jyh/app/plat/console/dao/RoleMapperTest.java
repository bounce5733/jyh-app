package com.jyh.app.plat.console.dao;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.druid.support.json.JSONUtils;
import com.jyh.app.plat.console.dao.RoleMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleMapperTest {

	private static final Logger log = LoggerFactory.getLogger(RoleMapperTest.class);

	@Autowired
	private RoleMapper roleMapper;

	@Test
	@Ignore
	public void roleMenus() {
		List<String> roleMenus = roleMapper.roleMenus("e7b043dd693b418ab8ce71af1a08c850");
		log.info(JSONUtils.toJSONString(roleMenus));
	}
}
