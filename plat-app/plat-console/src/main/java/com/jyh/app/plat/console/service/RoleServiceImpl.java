package com.jyh.app.plat.console.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.jyh.api.plat.console.RoleService;
import com.jyh.app.plat.console.dao.RoleMapper;
import com.jyh.entity.plat.console.Role;
import com.jyh.util.common.IDGenUtil;

/**
 * 角色服务
 * 
 * @author jiangyonghua
 * @date 2018年1月8日 下午4:36:37
 */
@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;

	@Override
	public List<Role> load() {
		return roleMapper.selectAll();
	}

	public void remove(String roleid) {
		// 清空授权菜单
		roleMapper.clearAssignedMenus(roleid);
		// 清空授权用户
		roleMapper.clearAssignedUsers(roleid);
		// 删除权限
		roleMapper.deleteByPrimaryKey(roleid);
	}

	/**
	 * 授权用户
	 * 
	 * @param roleid
	 * @param users
	 */
	@Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public void assignUsers(String roleid, List<String> users) {
		// 清空授权用户
		roleMapper.clearAssignedUsers(roleid);
		// 授权
		users.forEach(userid -> {
			roleMapper.assignUser(IDGenUtil.UUID(), roleid, userid);
		});
	}

	/**
	 * 授权菜单
	 * 
	 * @param roleid
	 * @param menus
	 */
	@Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
	public void assignMenus(String roleid, List<String> menuids) {
		// 清除菜单授权
		roleMapper.clearAssignedMenus(roleid);
		menuids.forEach(menuid -> {
			roleMapper.assignMenu(IDGenUtil.UUID(), roleid, menuid);
		});
	}

	/**
	 * 用户菜单，去重
	 * 
	 * @param userid
	 * @return
	 */
	public Set<String> userMenus(String userid) {
		List<String> menuids = roleMapper.userMenus(userid);
		// 去除重复
		Set<String> uniqueMenuids = new HashSet<String>();
		uniqueMenuids.addAll(menuids);
		return uniqueMenuids;
	}
}
