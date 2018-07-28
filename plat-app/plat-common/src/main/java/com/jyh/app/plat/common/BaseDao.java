package com.jyh.app.plat.common;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 数据操作公共抽象类
 * 
 * @author jiangyonghua
 * @date 2017年8月9日 下午12:18:27
 * @param <T>
 */
public abstract class BaseDao<T> {

	private static final Logger log = LoggerFactory.getLogger(BaseDao.class);

	@Autowired
	protected JdbcTemplate template;

	/**
	 * @param params
	 *            参数
	 * @param orderField
	 *            排序字段
	 * @param order
	 *            asc/desc
	 * @param page
	 *            当前页码
	 * @param rows
	 *            每页显示信息数量
	 */
	public abstract Map<String, Object> list(Map<String, Object> params, String orderField, String order, int page,
			int rows);

	public abstract T get(String id);

	public abstract void add(T t);

	public abstract void edit(T t);

	public abstract void remove(String id);

	/**
	 * 根据sql结果集自动包装Bean实例
	 * 
	 * @param sql
	 * @param c
	 * @param params
	 * @return
	 */
	protected T wrapBean(String sql, Class<T> c, Object... params) {
		try {
			T t = c.newInstance();
			Map<String, Object> map = template.queryForMap(sql, params);
			for (String key : map.keySet()) {
				Field f = c.getDeclaredField(key);
				f.setAccessible(true);
				f.set(t, map.get(key));
			}
			return t;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 根据sql结果集自动包装Bean实例列表
	 * 
	 * @param sql
	 * @param c
	 * @param params
	 * @return
	 */
	protected List<T> wrapBeanList(String sql, Class<T> c, Object... params) {
		try {
			List<T> tlist = new LinkedList<T>();
			List<Map<String, Object>> maplist = template.queryForList(sql, params);
			for (Map<String, Object> item : maplist) {
				T t = c.newInstance();
				for (String key : item.keySet()) {
					Field f = c.getDeclaredField(key);
					f.setAccessible(true);
					f.set(t, item.get(key));
				}
				tlist.add(t);
			}
			return tlist;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

}
