/**
 * 
 */
package org.chench.test.shiro.spring.util;

/**
 * @desc org.chench.test.shiro.spring.util.Constants
 * @author chench9@lenovo.com
 * @date 2017年12月13日
 */
public class Constants {
	public static final int ZK_SESSION_TIMEOUT = 3000;
	public static final String ZK_SERVERS = "192.168.70.135:2181";
	public static final String ZK_ZNODE_SHIRO_CACHE = "/shiro";
	
	/**
	 * 刷新角色
	 */
	private static boolean refresh = true;
	
	/**
	 * zk连通性
	 */
	private static boolean connected = false;
	

	public static boolean isRefresh() {
		return refresh;
	}

	public static void setRefresh(boolean refresh) {
		Constants.refresh = refresh;
	}
	
	public static boolean isConnected() {
		return connected;
	}

	public static void setConnected(boolean connected) {
		Constants.connected = connected;
	}

	private Constants() {
	}
}
