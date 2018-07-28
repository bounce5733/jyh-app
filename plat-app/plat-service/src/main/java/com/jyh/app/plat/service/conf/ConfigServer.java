package com.jyh.app.plat.service.conf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jyh.app.plat.common.SysConst;
import com.jyh.app.plat.service.dao.AppMapper;
import com.jyh.entity.plat.service.AppEntity;
import com.jyh.util.common.StringUtil;

/**
 * 应用配置socket服务端
 * 
 * @author jiangyonghua
 * @date 2018年2月21日 上午11:00:56
 */
@Component
public class ConfigServer implements Runnable {

	private static final Logger log = LoggerFactory.getLogger(ConfigServer.class);

	private ServerSocket server;

	@Autowired
	private AppMapper appMapper;

	@Value("${app.conf.socket.port}")
	private int port;

	@Override
	public void run() {
		try {
			log.info("开始启动应用配置Socket服务...");
			server = new ServerSocket(port);
			log.info("应用配置Socket服务成功启动，端口[" + port + "]");
			while (true) {
				Socket socket = server.accept();
				new Thread(new ServerSocketThread(socket)).start();
			}
		} catch (IOException e) {
			log.error("应用配置Socket服务启动失败！", e);
		} finally {
			if (server != null && !server.isClosed()) {
				try {
					server.close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
		}
	}

	/**
	 * 服务端执行线程
	 */
	class ServerSocketThread implements Runnable {

		private Socket socket;

		private InputStream is;

		private OutputStream os;

		public ServerSocketThread(Socket socket) {
			this.socket = socket;
		}

		@SuppressWarnings("unchecked")
		@Override
		public void run() {
			log.debug("收到连接，新建socket线程处理...");
			ByteArrayOutputStream buffer = null;
			try {
				is = socket.getInputStream();
				byte[] b = new byte[1024];
				int nRead;
				buffer = new ByteArrayOutputStream();
				while ((nRead = is.read(b)) != -1) {
					buffer.write(b, 0, nRead);
				}
				buffer.flush();
				if (buffer.size() > 0) {
					String msg = new String(buffer.toByteArray(), SysConst.ENCODING);
					log.debug("收到：" + msg);

					// 响应报文
					os = socket.getOutputStream();
					int retcode = 0;// -1:消息格式错误|0:成功|1:appid不存在
					StringBuilder result = new StringBuilder();
					Map<String, Object> msgMap = null;
					try {
						msgMap = new ObjectMapper().readValue(msg, Map.class);
					} catch (IOException e) {
						log.error(e.getMessage(), e);
						retcode = -1;
						result.append("报文格式错误");
						os.write(new StringBuilder("{\"code\":" + retcode + ",\"result\":" + result + "}").toString()
								.getBytes());
						os.flush();
						return;
					}

					if (msgMap.get("appid") != null && StringUtil.isNotEmpty(String.valueOf(msgMap.get("appid")))) {
						String appid = String.valueOf(msgMap.get("appid"));
						AppEntity param = new AppEntity();
						param.setAppid(appid);
						List<AppEntity> apps = appMapper.select(param);
						if (apps.size() == 0) {
							throw new RuntimeException("应用[" + appid + "]不存在！");
						}
						AppEntity app = apps.get(0);
						if (app == null) {
							retcode = 1;
							result.append("应用[" + appid + "]不存在");
						} else {
							os.write(app.getConf().getBytes());
							os.flush();
							return;
						}
					} else {
						retcode = -1;
						result.append("报文格式错误");
					}

					os.write(new StringBuilder("{\"code\":" + retcode + ",\"result\":" + result + "}").toString()
							.getBytes());
				}
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			} finally {
				try {
					if (socket != null && !socket.isClosed()) {
						socket.close();
					}
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
		}
	}
}
