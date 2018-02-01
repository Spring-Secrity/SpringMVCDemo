/**
 * 
 */
package org.chench.test.shiro.spring.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.chench.test.shiro.spring.dao.UsersDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @desc org.chench.test.shiro.spring.service.UsersService
 * @author chench9@lenovo.com
 * @date 2017年3月16日
 */
@Service
public class UsersService {
	@Autowired
	private UsersDAO usersDAO;
	
	/**
	 * 获取用户权限
	 * @param username
	 * @return
	 */
	public List<String> getUserPermissions(String username) {
		return usersDAO.getUserPermissions(username);
	}
	
	public Set<String> getUserPermissionSet(String username) {
		List<String> permissionList = usersDAO.getUserPermissions(username);
		Set<String> permissionSet = new HashSet<String>();
		permissionSet.addAll(permissionList);
		return permissionSet;
	}
	
	public Integer addRole(String userName, String roleName) {
		return usersDAO.addRole(userName, roleName);
	}
	
	public Integer delRole(String userName, String roleName) {
		return usersDAO.delRole(userName, roleName);
	}

}
