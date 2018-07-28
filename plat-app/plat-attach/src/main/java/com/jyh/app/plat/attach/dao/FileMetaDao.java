package com.jyh.app.plat.attach.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jyh.app.plat.common.BaseDao;
import com.jyh.entity.plat.attach.FileMeta;
import com.jyh.util.common.StringUtil;

/**
 * 文件元数据
 * 
 * @author jiangyonghua
 * @date 2018年2月6日 上午11:00:08
 */
@Repository
public class FileMetaDao extends BaseDao<FileMeta> {

	@Override
	public Map<String, Object> list(Map<String, Object> params, String orderField, String order, int page, int rows) {
		StringBuffer sql = new StringBuffer(
				"SELECT fileid,appid,file_name AS fileName,file_ext AS fileExt,file_size AS fileSize,created_time AS createdTime FROM file_meta WHERE 1=1 ");

		if (StringUtil.isNotEmpty(String.valueOf(params.get("fileName")))) {
			sql.append(" AND file_name like '%" + params.get("fileName") + "%'");
		}

		int total = template.queryForObject("SELECT COUNT(*) FROM (" + sql.toString() + ") t", Integer.class);

		if (StringUtil.isNotEmpty(orderField) && StringUtil.isNotEmpty(order)) {
			order = "ascending".equals(order) ? " ASC" : " DESC";
			sql.append(" ORDER BY " + orderField + order);
		}

		sql.append(" LIMIT ").append((page - 1) * rows).append(",").append(rows);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("list", this.wrapBeanList(sql.toString(), FileMeta.class));
		result.put("total", total);
		return result;
	}

	@Override
	public FileMeta get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(FileMeta t) {
		String sql = "INSERT INTO file_meta (fileid,appid,file_name,file_ext,file_size,created_time) VALUES (?,?,?,?,?,?)";
		template.update(sql, t.getFileid(), t.getAppid(), t.getFileName(), t.getFileExt(), t.getFileSize(),
				t.getCreatedTime());
	}

	@Override
	public void edit(FileMeta t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(String fileid) {
		String sql = "DELETE FROM file_meta WHERE fileid = ?";
		template.update(sql, fileid);
	}

}
