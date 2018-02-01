/**
 * 
 */
package org.chench.test.shiro.spring.dao;

import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.chench.test.shiro.spring.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 扩展使用了缓存组件的JDBC Realm
 * @desc org.chench.test.shiro.spring.dao.ShiroCacheJdbcRealm
 * @author chench9@lenovo.com
 * @date 2017年12月14日
 */
public class ShiroCacheJdbcRealm extends JdbcRealm {
	private static final Logger logger = LoggerFactory.getLogger(ShiroCacheJdbcRealm.class);

	@Override
	public Cache<Object, AuthorizationInfo> getAuthorizationCache() {
		Cache<Object, AuthorizationInfo> cache = super.getAuthorizationCache();
		if(cache == null) {
			return cache;
		}
		if(!Constants.isConnected() || Constants.isRefresh()) {
			if(logger.isWarnEnabled()) {
				logger.warn("clear shiro cache");
			}
			cache.clear();
		}
		return cache;
	}
}
