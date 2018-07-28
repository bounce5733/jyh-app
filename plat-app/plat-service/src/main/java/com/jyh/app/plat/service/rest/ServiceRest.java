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

import com.jyh.app.plat.service.dao.ServiceMapper;
import com.jyh.app.plat.service.service.AppServiceImpl;
import com.jyh.entity.plat.service.ServiceEntity;
import com.jyh.util.common.IDGenUtil;
import com.jyh.util.common.TimeUtil;

/**
 * @author jiangyonghua
 * @date 2018年3月8日 下午4:36:50
 */
@RestController
@RequestMapping(path = "services")
public class ServiceRest {

	@Autowired
	private AppServiceImpl appService;

	@Autowired
	private ServiceMapper serviceMapper;

	@PostMapping
	public ResponseEntity<Object> add(@RequestBody ServiceEntity service, HttpServletRequest request) {
		service.setId(IDGenUtil.UUID());
		service.setCreatedBy(String.valueOf(request.getSession().getAttribute("userid")));
		service.setCreatedTime(TimeUtil.getTime());
		serviceMapper.insertSelective(service);

		// 刷新缓存
		appService.loadApp();
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	@PatchMapping
	public ResponseEntity<Object> edit(@RequestBody ServiceEntity service, HttpServletRequest request) {
		service.setUpdatedBy(String.valueOf(request.getSession().getAttribute("userid")));
		service.setUpdatedTime(TimeUtil.getTime());
		serviceMapper.updateByPrimaryKeySelective(service);

		// 刷新缓存
		appService.loadApp();
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Object> remove(@PathVariable("id") String id) {
		serviceMapper.deleteByPrimaryKey(id);

		// 刷新缓存
		appService.loadApp();
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

}
