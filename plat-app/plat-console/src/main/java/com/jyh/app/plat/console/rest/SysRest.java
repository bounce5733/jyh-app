package com.jyh.app.plat.console.rest;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jyh.app.plat.common.SessionConfig;
import com.jyh.app.plat.console.dao.UserMapper;
import com.jyh.entity.plat.console.User;
import com.jyh.util.common.CodeUtil;

/**
 * 系统API
 * 
 * @author jiangyonghua
 * @date 2017年12月26日 下午12:01:23
 */
@RestController
@RequestMapping(path = "sys")
public class SysRest {

	@Autowired
	private UserMapper userMapper;

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Map<String, String> userMap, HttpSession session) {
		// 验证用户
		User param = new User();
		param.setAccount(userMap.get("account"));
		User user = userMapper.selectOne(param);
		if (user == null || !CodeUtil.md5Encode(userMap.get("pwd").trim()).equals(user.getPwd())) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}

		session.setAttribute(SessionConfig.USER_ID_KEY, user.getId());
		session.setAttribute(SessionConfig.USER_NAME_KEY, user.getName());
		session.setAttribute(SessionConfig.USER_ACCOUNT_KEY, user.getAccount());
		
		return new ResponseEntity<String>(session.getId(), HttpStatus.OK);
	}

	@GetMapping("/logout")
	public ResponseEntity<Object> logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
}
