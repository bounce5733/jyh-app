package com.jyh.app.plat.console.rest;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jyh.app.plat.common.SessionConfig;
import com.jyh.app.plat.console.base.Context;
import com.jyh.app.plat.console.dao.CodeItemMapper;
import com.jyh.app.plat.console.dao.CodeMapper;
import com.jyh.app.plat.console.service.CodeServiceImpl;
import com.jyh.entity.plat.console.Code;
import com.jyh.entity.plat.console.CodeItem;
import com.jyh.util.common.IDGenUtil;
import com.jyh.util.common.TimeUtil;

/**
 * 码表API
 * 
 * @author jiangyonghua,wangmingyue
 * @date 2017年12月27日 下午4:17:33
 */
@RestController
@RequestMapping(path = "codes")
public class CodeRest {

	private static final Logger log = LoggerFactory.getLogger(CodeRest.class);

	@Autowired
	private Context context;

	@Autowired
	private CodeMapper codeMapper;

	@Autowired
	private CodeItemMapper codeItemMapper;

	@Autowired
	private CodeServiceImpl service;

	// @Reference
	// private AppService appService;

	// @GetMapping("/loadapp")
	// public ResponseEntity<Map<String, AppEntity>> loadapp() {
	// return new ResponseEntity<Map<String, AppEntity>>(appService.loadApp(),
	// HttpStatus.OK);
	// }

	@GetMapping
	public ResponseEntity<List<Code>> load() {
		return new ResponseEntity<List<Code>>(service.codes(), HttpStatus.OK);
	}

	@GetMapping("/cacheMap")
	public ResponseEntity<Map<String, List<CodeItem>>> cacheMap() {
		return new ResponseEntity<Map<String, List<CodeItem>>>(context.loadCodes(), HttpStatus.OK);
	}

	@GetMapping("/cachePathMap")
	public ResponseEntity<Map<String, Map<String, Object>>> cachePathMap() {
		return new ResponseEntity<Map<String, Map<String, Object>>>(context.loadCodePathMap(), HttpStatus.OK);
	}

	@PostMapping
	@CacheEvict(value = { "plat_console_codes", "plat_console_code_path_map" })
	public ResponseEntity<Object> addCode(@RequestBody Code code, HttpSession session) {
		code.setCreatedBy(String.valueOf(session.getAttribute(SessionConfig.USER_ACCOUNT_KEY)));
		code.setCreatedTime(TimeUtil.getTime());
		codeMapper.insertSelective(code);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	@DeleteMapping("/{code}")
	@CacheEvict(value = { "plat_console_codes", "plat_console_code_path_map" })
	public ResponseEntity<Object> removeCode(@PathVariable String code) {
		codeMapper.deleteByPrimaryKey(code);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	@PostMapping("/item")
	@CacheEvict(value = { "plat_console_codes", "plat_console_code_path_map" })
	public ResponseEntity<Object> addItem(@RequestBody CodeItem item, HttpServletRequest request) {
		item.setId(IDGenUtil.UUID());
		item.setCreatedBy(request.getSession().getAttribute(SessionConfig.USER_ACCOUNT_KEY).toString());
		item.setCreatedTime(TimeUtil.getTime());
		codeItemMapper.insertSelective(item);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	@PatchMapping("/item")
	@CacheEvict(value = { "plat_console_codes", "plat_console_code_path_map" })
	public ResponseEntity<Object> editItem(@RequestBody CodeItem item, HttpServletRequest request) {
		item.setUpdatedBy(request.getSession().getAttribute(SessionConfig.USER_ACCOUNT_KEY).toString());
		item.setUpdatedTime(TimeUtil.getTime());
		codeItemMapper.updateByPrimaryKeySelective(item);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	@DeleteMapping("/item/{id}")
	@CacheEvict(value = { "plat_console_codes", "plat_console_code_path_map" })
	public ResponseEntity<Object> removeItem(@PathVariable("id") String id) {
		try {
			codeItemMapper.deleteByPrimaryKey(id);
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if (e instanceof DataIntegrityViolationException) {
				return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}
}
