package com.jyh.app.plat.attach.rest;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jyh.app.plat.attach.dao.FileMetaDao;
import com.jyh.app.plat.attach.service.FileMetaService;
import com.jyh.entity.plat.attach.FileMeta;
import com.jyh.util.common.TimeUtil;

/**
 * 文件元数据
 * 
 * @author jiangyonghua
 * @date 2018年2月6日 上午11:00:41
 */
@RestController
@RequestMapping(path = "filemetaes")
public class FileMetaRest {

	private static final Logger log = LoggerFactory.getLogger(FileMetaRest.class);

	@Autowired
	private FileMetaDao fileMetaDao;

	@Autowired
	private FileMetaService fileMetaService;

	@PostMapping("/list")
	public ResponseEntity<Map<String, Object>> list(@RequestBody Map<String, Object> params,
			@RequestParam(value = "orderField", required = false) String orderField,
			@RequestParam(value = "order", required = false) String order, @RequestParam(value = "page") int page,
			@RequestParam(value = "rows") int rows) {
		Map<String, Object> list = fileMetaDao.list(params, orderField, order, page, rows);
		return new ResponseEntity<Map<String, Object>>(list, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Object> add(@RequestBody FileMeta fileMeta, @RequestAttribute String appid) {
		try {
			if (StringUtils.isBlank(fileMeta.getFileid()) || StringUtils.isBlank(fileMeta.getFileName())
					|| StringUtils.isBlank(fileMeta.getFileExt()) || fileMeta.getFileSize() == 0) {

				log.warn("参数不合法[fileid:" + fileMeta.getFileid() + "][fileName:" + fileMeta.getFileName() + "][fileExt:"
						+ fileMeta.getFileExt() + "][fileExt:" + fileMeta.getFileSize() + "]");
				return new ResponseEntity<Object>(HttpStatus.NOT_ACCEPTABLE);
			}
			fileMeta.setCreatedTime(TimeUtil.getTime());
			fileMeta.setAppid(appid);
			fileMetaDao.add(fileMeta);

			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping
	public ResponseEntity<Object> remove(@RequestBody Map<String, String> fileinfo) {
		try {
			fileMetaService.remove(fileinfo.get("fileid"));
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
