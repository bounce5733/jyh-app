package com.jyh.app.plat.console.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.jyh.api.plat.console.UserService;
import com.jyh.app.plat.console.dao.RoleMapper;
import com.jyh.app.plat.console.dao.UserMapper;
import com.jyh.entity.plat.console.User;

import tk.mybatis.mapper.entity.Condition;

/**
 * 用户服务
 * 
 * @author jiangyonghua
 * @date 2018年1月7日 下午10:42:36
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private RoleMapper roleMapper;

	@Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public void remove(String id) {
		roleMapper.removeRoleUserByUserId(id);
		userMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<User> query(String param) {
		Condition c = new Condition(User.class);
		c.createCriteria().andCondition("account LIKE '%" + param + "%' OR name LIKE '%" + param + "%'");
		return userMapper.selectByCondition(c);
	}

	@Override
	public List<User> roleUsers(String roleid) {
		return userMapper.roleUsers(roleid);
	}

	@Override
	public List<User> load() {
		return userMapper.selectAll();
	}
}
