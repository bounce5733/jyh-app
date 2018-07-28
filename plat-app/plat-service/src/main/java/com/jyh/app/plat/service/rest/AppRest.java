package com.jyh.app.plat.service.rest;

import java.util.List;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jyh.app.plat.service.dao.AppMapper;
import com.jyh.app.plat.service.dao.AppTypeMapper;
import com.jyh.app.plat.service.service.AppServiceImpl;
import com.jyh.entity.plat.service.AppEntity;
import com.jyh.entity.plat.service.AppTypeEntity;
import com.jyh.util.common.IDGenUtil;
import com.jyh.util.common.TimeUtil;

/**
 * @author jiangyonghua
 * @date 2018年2月8日 下午3:50:56
 */
@RestController
@RequestMapping(path = "apps")
public class AppRest {

	@Autowired
	private AppServiceImpl service;

	@Autowired
	private AppMapper appMapper;

	@Autowired
	private AppTypeMapper appTypeMapper;

	// @Reference
	// private RoleService roleService;
	//
	// @GetMapping("/loadrole")
	// public ResponseEntity<List<Role>> loadrole() {
	// return new ResponseEntity<List<Role>>(roleService.load(), HttpStatus.OK);
	// }

	// @PostMapping("/mqsender")
	// public ResponseEntity<Object> mqsender(@RequestBody Map<String, Object> obj)
	// {
	// service.send(obj);
	// return new ResponseEntity<Object>(HttpStatus.OK);
	// }

	@GetMapping("/kfksender")
	public ResponseEntity<Object> kfksender(@RequestParam("msg") String msg) {
		service.sendKfkMsg(msg);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	@GetMapping("/tree")
	public ResponseEntity<List<AppTypeEntity>> loadTree() {
		return new ResponseEntity<List<AppTypeEntity>>(service.loadTree(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Object> add(@RequestBody AppEntity app, HttpServletRequest request) {
		app.setId(IDGenUtil.UUID());
		app.setCreatedBy(String.valueOf(request.getSession().getAttribute("userid")));
		app.setCreatedTime(TimeUtil.getTime());
		appMapper.insertSelective(app);

		// 刷新缓存
		service.loadApp();
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	@PatchMapping
	public ResponseEntity<Object> edit(@RequestBody AppEntity app, HttpServletRequest request) {
		app.setUpdatedBy(String.valueOf(request.getSession().getAttribute("userid")));
		app.setUpdatedTime(TimeUtil.getTime());
		appMapper.updateByPrimaryKeySelective(app);

		// 刷新缓存
		service.loadApp();
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> remove(@PathVariable("id") String id) {
		appMapper.deleteByPrimaryKey(id);

		// 刷新缓存
		service.loadApp();
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	@PostMapping("/type")
	public ResponseEntity<Object> addType(@RequestBody AppTypeEntity appType, HttpServletRequest request) {
		appType.setId(IDGenUtil.UUID());
		appType.setCreatedBy(String.valueOf(request.getSession().getAttribute("userid")));
		appType.setCreatedTime(TimeUtil.getTime());
		appTypeMapper.insertSelective(appType);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	@PatchMapping("/type")
	public ResponseEntity<Object> editType(@RequestBody AppTypeEntity appType, HttpServletRequest request) {
		appType.setUpdatedBy(String.valueOf(request.getSession().getAttribute("userid")));
		appType.setUpdatedTime(TimeUtil.getTime());
		appTypeMapper.updateByPrimaryKeySelective(appType);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	@DeleteMapping("/type/{id}")
	public ResponseEntity<Object> removeType(@PathVariable("id") String id) {
		appTypeMapper.deleteByPrimaryKey(id);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

}
