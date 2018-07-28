package com.jyh.app.plat.attach.service;

import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.TrackerClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jyh.app.plat.attach.dao.FileMetaDao;

@Service
public class FileMetaService {

	private static final Logger log = LoggerFactory.getLogger(FileMetaService.class);

	@Autowired
	private FileMetaDao fileMetaDao;

	public void remove(String fileid) {

		try {
			// 删除DFS文件
			StorageClient1 client = new StorageClient1(new TrackerClient().getConnection(), null);
			int rtn = client.delete_file1(fileid);

			if (rtn == 0) {
				// 删除元信息
				fileMetaDao.remove(fileid);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
}
