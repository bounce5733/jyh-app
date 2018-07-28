package com.jyh.app.plat.console.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jyh.app.plat.common.SessionConfig;
import com.jyh.app.plat.common.SysConst;
import com.jyh.app.plat.console.dao.UserMapper;
import com.jyh.app.plat.console.service.RoleServiceImpl;
import com.jyh.app.plat.console.service.UserServiceImpl;
import com.jyh.entity.plat.console.User;
import com.jyh.util.common.IDGenUtil;
import com.jyh.util.common.TimeUtil;

/**
 * 用户API
 * 
 * @author jiangyonghua
 * @date 2018年1月7日 下午10:43:57
 */
@RestController
@RequestMapping(path = "users")
public class UserRest {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private RoleServiceImpl roleService;

	@GetMapping
	public ResponseEntity<List<User>> load() {
		return new ResponseEntity<List<User>>(userService.load(), HttpStatus.OK);
	}

	@GetMapping("/userinfo")
	public ResponseEntity<Map<String, Object>> userinfo(HttpSession session) {
		Map<String, Object> userinfo = new HashMap<String, Object>();
		String userid = String.valueOf(session.getAttribute(SessionConfig.USER_ID_KEY));
		User user = userMapper.selectByPrimaryKey(userid);
		Set<String> menuids = roleService.userMenus(userid);
		userinfo.put("user", user);
		userinfo.put("menuids", menuids);
		return new ResponseEntity<Map<String, Object>>(userinfo, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> get(@PathVariable("id") String id) {
		return new ResponseEntity<User>(userMapper.selectByPrimaryKey(id), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Object> add(@RequestBody User user, HttpSession session) {
		user.setId(IDGenUtil.UUID());
		user.setCreatedBy(String.valueOf(session.getAttribute(SessionConfig.USER_ACCOUNT_KEY)));
		user.setCreatedTime(TimeUtil.getTime());
		user.setPwd(SysConst.SYS_DEFAULT_PWD);
		userMapper.insertSelective(user);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	@PatchMapping
	public ResponseEntity<Object> edit(@RequestBody User user, HttpSession session) {
		user.setUpdatedBy(String.valueOf(session.getAttribute(SessionConfig.USER_ACCOUNT_KEY)));
		user.setUpdatedTime(TimeUtil.getTime());
		userMapper.updateByPrimaryKeySelective(user);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> remove(@PathVariable("id") String id) {
		try {
			userService.remove(id);
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<Object>(HttpStatus.LOCKED);
		}
	}

	@GetMapping("/matchWithAccountOrName")
	public ResponseEntity<List<User>> query(@RequestParam("param") String param) {
		return new ResponseEntity<List<User>>(userService.query(param), HttpStatus.OK);
	}

	@GetMapping("/role/{roleid}")
	public ResponseEntity<List<User>> roleUsers(@PathVariable("roleid") String roleid) {
		return new ResponseEntity<List<User>>(userService.roleUsers(roleid), HttpStatus.OK);
	}
}
