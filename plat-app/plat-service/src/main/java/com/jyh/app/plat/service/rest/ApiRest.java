package com.jyh.app.plat.service.rest;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jyh.app.plat.service.dao.ApiMapper;
import com.jyh.app.plat.service.service.AppServiceImpl;
import com.jyh.entity.plat.service.ApiEntity;
import com.jyh.util.common.IDGenUtil;
import com.jyh.util.common.TimeUtil;

/**
 * @author jiangyonghua
 * @date 2018年3月8日 下午4:36:50
 */
@RestController
@RequestMapping(path = "apis")
public class ApiRest {

	@Autowired
	private AppServiceImpl service;

	@Autowired
	private ApiMapper apiMapper;

	@PostMapping
	public ResponseEntity<Object> add(@RequestBody ApiEntity apiEntity, HttpServletRequest request) {
		apiEntity.setId(IDGenUtil.UUID());
		apiEntity.setCreatedBy(String.valueOf(request.getSession().getAttribute("userid")));
		apiEntity.setCreatedTime(TimeUtil.getTime());
		apiMapper.insertSelective(apiEntity);

		// 刷新缓存
		service.loadApp();
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	@PatchMapping
	public ResponseEntity<Object> edit(@RequestBody ApiEntity apiEntity, HttpServletRequest request) {
		apiEntity.setUpdatedBy(String.valueOf(request.getSession().getAttribute("userid")));
		apiEntity.setUpdatedTime(TimeUtil.getTime());
		apiMapper.updateByPrimaryKeySelective(apiEntity);

		// 刷新缓存
		service.loadApp();
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Object> remove(@PathVariable("id") String id) {
		apiMapper.deleteByPrimaryKey(id);

		// 刷新缓存
		service.loadApp();
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

}
