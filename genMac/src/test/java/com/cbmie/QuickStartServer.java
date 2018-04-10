package com.cbmie;

import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cbmie.jetty.JettyFactory;
import com.cbmie.jetty.Profiles;



/**
 * 使用Jetty运行调试Web应用, 在Console输入回车快速重新加载应用.
 * 
 * @author calvin
 */
public class QuickStartServer {

	public static final int PORT = 8080;
	public static final String CONTEXT = "/genMac";
	private static final String[] TLD_JAR_NAMES = new String[] {"spring-webmvc", "shiro-web"};
	
	private static Logger logger = LoggerFactory.getLogger(QuickStartServer.class);

	public static void main(String[] args) throws Exception {
		// 设定Spring的profile
		Profiles.setProfileAsSystemProperty(Profiles.DEVELOPMENT);

		// 启动Jetty
		Server server = JettyFactory.createServerInSource(PORT, CONTEXT);
		JettyFactory.setTldJarNames(server, TLD_JAR_NAMES);

		try {
			server.start();

			System.out.println("[INFO] 服务启动  http://localhost:" + PORT + CONTEXT);
			System.out.println("[HINT] 按回车迅速重启");

			// 等待用户输入回车重载应用.
			while (true) {
				char c = (char) System.in.read();
				if (c == '\n') {
					JettyFactory.reloadContext(server);
				}
			}
		} catch (Exception e) {
			System.exit(-1);
			logger.error(e.getLocalizedMessage(),e);
		}
	}
}
