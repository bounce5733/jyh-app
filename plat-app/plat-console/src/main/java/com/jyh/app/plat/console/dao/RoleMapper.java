package com.jyh.app.plat.console.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jyh.app.plat.console.base.Mapper;
import com.jyh.entity.plat.console.Role;

public interface RoleMapper extends Mapper<Role> {

	void assignMenu(@Param("id") String id, @Param("roleid") String roleid, @Param("menuid") String menuid);

	void assignUser(@Param("id") String id, @Param("roleid") String roleid, @Param("userid") String userid);

	void clearAssignedUsers(@Param("roleid") String roleid);

	void clearAssignedMenus(@Param("roleid") String roleid);

	void removeRoleMenuByMenuId(@Param("menuid") String menuid);

	void removeRoleUserByUserId(@Param("userid") String userid);

	List<String> roleMenus(@Param("roleid") String roleid);

	List<String> userMenus(@Param("userid") String userid);
}
