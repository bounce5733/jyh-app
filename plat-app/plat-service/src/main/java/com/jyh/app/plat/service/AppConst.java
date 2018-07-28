package com.jyh.app.plat.service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jiangyonghua
 * @date 2018年3月5日 下午3:17:23
 */
public class AppConst {

	public static final String BASE_PACKAGE = "com.jyh.plat.app";// 项目基础包名称，根据自己公司的项目修改

	public static final String MODEL_PACKAGE = "com.jyh.entity.plat.app";// Model所在包
	public static final String MAPPER_PACKAGE = BASE_PACKAGE + ".dao";// Mapper所在包
	public static final String SERVICE_PACKAGE = BASE_PACKAGE + ".service";// Service所在包
	public static final String SERVICE_IMPL_PACKAGE = SERVICE_PACKAGE + ".impl";// ServiceImpl所在包
	public static final String CONTROLLER_PACKAGE = BASE_PACKAGE + ".rest";// Controller所在包

	public static final String MAPPER_INTERFACE_REFERENCE = BASE_PACKAGE + ".Mapper";// Mapper插件基础接口的完全限定名

	// 菜单路径随机字符串长度
	public static final int MENU_PATH_LENGTH = 8;

	// 免验证资源
	public static final List<String> AUTH_SKIP_URI = new ArrayList<String>();

	static {
		AUTH_SKIP_URI.add("/apps/mqsender");
		AUTH_SKIP_URI.add("/apps/kfksender");
		AUTH_SKIP_URI.add("/apps/loadrole");
	}
}
