package com.jyh.app.plat.console.rest;

import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;

import com.jyh.app.plat.common.SessionConfig;
import com.jyh.app.plat.console.dao.RoleMapper;
import com.jyh.app.plat.console.service.RoleServiceImpl;
import com.jyh.entity.plat.console.Role;
import com.jyh.util.common.IDGenUtil;
import com.jyh.util.common.TimeUtil;

/**
 * 角色API
 * 
 * @author jiangyonghua
 * @date 2018年1月7日 下午10:43:57
 */
@RestController
@RequestMapping(path = "roles")
public class RoleRest {

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private RoleServiceImpl service;

	@GetMapping
	public ResponseEntity<List<Role>> load() {
		return new ResponseEntity<List<Role>>(roleMapper.selectAll(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Object> add(@RequestBody Role role, HttpSession session) {
		role.setId(IDGenUtil.UUID());
		role.setCreatedBy(String.valueOf(session.getAttribute(SessionConfig.USER_ACCOUNT_KEY)));
		role.setCreatedTime(TimeUtil.getTime());
		roleMapper.insertSelective(role);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	@PatchMapping
	public ResponseEntity<Object> edit(@RequestBody Role role, HttpSession session) {
		role.setUpdatedBy(String.valueOf(session.getAttribute(SessionConfig.USER_ACCOUNT_KEY)));
		role.setUpdatedTime(TimeUtil.getTime());

		roleMapper.updateByPrimaryKeySelective(role);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> remove(@PathVariable("id") String id) {
		try {
			service.remove(id);
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<Object>(HttpStatus.LOCKED);
		}
	}

	/**
	 * 分配用户给角色
	 * 
	 * @param roleid
	 * @param userids
	 * @return
	 */
	@PostMapping("/{roleid}/assign/users")
	public ResponseEntity<Object> assignUsers(@PathVariable("roleid") String roleid,
			@RequestBody List<String> userids) {
		service.assignUsers(roleid, userids);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	/**
	 * 分配菜单给角色
	 * 
	 * @param roleid
	 * @param menuids
	 * @return
	 */
	@PostMapping("/{roleid}/assign/menus")
	public ResponseEntity<Object> assignMenus(@PathVariable("roleid") String roleid,
			@RequestBody List<String> menuids) {
		service.assignMenus(roleid, menuids);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	/**
	 * 角色菜单
	 * 
	 * @param roleid
	 * @return
	 */
	@GetMapping("/menus/{roleid}")
	public ResponseEntity<List<String>> roleMenus(@PathVariable("roleid") String roleid) {
		return new ResponseEntity<List<String>>(roleMapper.roleMenus(roleid), HttpStatus.OK);
	}

	/**
	 * 人员菜单
	 * 
	 * @param userid
	 * @return
	 */
	@GetMapping("/usermenus/{userid}")
	public ResponseEntity<Set<String>> userMenus(@PathVariable("userid") String userid) {
		return new ResponseEntity<Set<String>>(service.userMenus(userid), HttpStatus.OK);
	}

}
