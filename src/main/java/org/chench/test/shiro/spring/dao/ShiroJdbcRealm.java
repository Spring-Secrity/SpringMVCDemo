/**
 * 
 */
package org.chench.test.shiro.spring.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.chench.test.shiro.spring.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义shiro jdbc realm，实现shiro缓存控制
 * @desc org.chench.test.shiro.spring.dao.ShiroJdbcRealm
 * @author chench9@lenovo.com
 * @date 2017年12月13日
 */
public class ShiroJdbcRealm extends JdbcRealm {
	private static final Logger logger = LoggerFactory.getLogger(ShiroJdbcRealm.class);
	
	/**
	 * 角色缓存
	 */
	private static final Set<String> CACHE_ROLES = new HashSet<String>();
	
	/**
	 * 权限缓存
	 */
	private static final Set<String> CACHE_PERMISSIONS = new HashSet<String>();

	@Override
	protected Set<String> getRoleNamesForUser(Connection conn, String username) throws SQLException {
		logger.info("roles cache size: {}, refresh: {}, zk connected: {}", new Object[] {CACHE_ROLES.size(), Constants.isRefresh(), Constants.isConnected()});
		
		if(CACHE_ROLES.isEmpty() || !Constants.isConnected() || Constants.isRefresh()) {
			logger.info("refresh role names cache");
			CACHE_ROLES.clear();
			Set<String> roleNames =	super.getRoleNamesForUser(conn, username);
			CACHE_ROLES.addAll(roleNames);
			return CACHE_ROLES;
		}
		
		return CACHE_ROLES;
	}

	@Override
	protected Set<String> getPermissions(Connection conn, String username, Collection<String> roleNames)
			throws SQLException {
		logger.info("permissions cache size: {}, refresh: {}, zk connected: {}", new Object[] {CACHE_PERMISSIONS.size(), Constants.isRefresh(), Constants.isConnected()});
		
		if(CACHE_PERMISSIONS.isEmpty() || !Constants.isConnected() || Constants.isRefresh()) {
			logger.info("refresh permissions cache");
			CACHE_PERMISSIONS.clear();
			Set<String> permissions = super.getPermissions(conn, username, roleNames);
			CACHE_PERMISSIONS.addAll(permissions);
			Constants.setRefresh(false);
		}
		
		return CACHE_PERMISSIONS;
	}

}
