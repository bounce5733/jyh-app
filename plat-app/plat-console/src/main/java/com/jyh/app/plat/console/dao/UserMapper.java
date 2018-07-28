package com.jyh.app.plat.console.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jyh.app.plat.console.base.Mapper;
import com.jyh.entity.plat.console.User;

public interface UserMapper extends Mapper<User> {

	List<User> roleUsers(@Param("roleid") String roleid);
}
