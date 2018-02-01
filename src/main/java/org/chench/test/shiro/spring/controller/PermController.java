/**
 * 
 */
package org.chench.test.shiro.spring.controller;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.chench.test.shiro.spring.service.UsersService;
import org.chench.test.shiro.spring.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 权限管理
 * 
 * @desc org.chench.test.shiro.spring.controller.PermController
 * @author chench9@lenovo.com
 * @date 2017年12月13日
 */
@Controller
@RequestMapping("/perm")
@RequiresAuthentication
public class PermController implements Watcher {
	private static final Logger logger = LoggerFactory.getLogger(PermController.class);
	private static ZooKeeper zk = null;
	
	@Autowired
	private UsersService userService;

	@RequestMapping(value = "/addRole", method = {RequestMethod.POST})
	@ResponseBody
	public Object addRole(HttpServletRequest req, HttpServletResponse resp) {
		String userName = SecurityUtils.getSubject().getPrincipal().toString();
		userService.addRole(userName, "admin");
		// 修改zk节点值
		updatePermission();
		return "success";
	}
	
	/**
	 * 删除角色
	 * @param req
	 * @param resp
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/delRole", method = {RequestMethod.POST})
	@ResponseBody
	public Object delRole(HttpServletRequest req, HttpServletResponse resp) {
		String userName = SecurityUtils.getSubject().getPrincipal().toString();
		userService.delRole(userName, "admin");
		updatePermission();
		return "success";
	}
	
	/**
	 * 更新节点权限
	 */
	public void updatePermission() {
		try {
			if(zk == null) {
				zk = new ZooKeeper(Constants.ZK_SERVERS, Constants.ZK_SESSION_TIMEOUT, this);
			}
			Stat stat =	zk.exists(Constants.ZK_ZNODE_SHIRO_CACHE, false);
			
			byte[] data = String.valueOf(Calendar.getInstance().getTime().getTime()).getBytes();
			if(stat != null) {
				zk.setData(Constants.ZK_ZNODE_SHIRO_CACHE, data, stat.getVersion());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void process(WatchedEvent event) {
		String path = event.getPath();
		EventType type = event.getType();
		KeeperState state = event.getState();

		logger.info("path: {}, type: {}, state: {}", path, type, state);
	}
}
