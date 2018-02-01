/**
 * 
 */
package org.chench.test.shiro.spring.listener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.zookeeper.AsyncCallback.StatCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.chench.test.shiro.spring.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 在应用上下文监听器中监听zk事件,从而实现shiro缓存更新通知.
 * @desc org.chench.test.shiro.spring.listener.ShiroCacheListener
 * @author chench9@lenovo.com
 * @date 2017年12月13日
 */
public class ShiroCacheListener implements ServletContextListener, Watcher, StatCallback {
	private Logger logger = LoggerFactory.getLogger(ShiroCacheListener.class);
	private ZooKeeper zk = null;
	

	public void contextInitialized(ServletContextEvent sce) {
		logger.info("shiro cache listener context initialized");
		init();
	}


	public void contextDestroyed(ServletContextEvent sce) {
		logger.info("shiro cache listener context destroyed");
		release();
	}
	
	private void init() {
		try {
			zk = new ZooKeeper(Constants.ZK_SERVERS, Constants.ZK_SESSION_TIMEOUT, this);
			Stat stat =	zk.exists(Constants.ZK_ZNODE_SHIRO_CACHE, false);
			if(stat != null) {
				zk.exists(Constants.ZK_ZNODE_SHIRO_CACHE, true, this, null);
				return;
			}
			
			byte[] data = String.valueOf(Calendar.getInstance().getTime().getTime()).getBytes();
			zk.create(Constants.ZK_ZNODE_SHIRO_CACHE, data, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		} catch (Exception e) {
			e.printStackTrace();
			Constants.setRefresh(true);
		}
	}

	private void release() {
		try {
			if(zk != null) {
				zk.close();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			zk = null;
		}
	}
	

	public void process(WatchedEvent event) {
		String path = event.getPath();
		logger.info("watcher process path: " + path + " event type: " + event.getType());
		if(Event.EventType.None == event.getType()) {
			switch (event.getState()) {
			case SyncConnected:
				logger.info("watcher process SyncConnected");
				Constants.setConnected(true);
				break;
			case Disconnected:
			case Expired:
				logger.info("watcher process {}", event.getState());
				Constants.setConnected(false);
				Constants.setRefresh(true);
				break;
			default:
				break;
			}
		}else if(Event.EventType.NodeCreated == event.getType()) {
			if(Constants.ZK_ZNODE_SHIRO_CACHE.equals(path)) {
				zk.exists(Constants.ZK_ZNODE_SHIRO_CACHE, true, this, null);
			}
		}else if(Event.EventType.NodeDataChanged == event.getType()){
			if(Constants.ZK_ZNODE_SHIRO_CACHE.equals(path)) {
				zk.exists(Constants.ZK_ZNODE_SHIRO_CACHE, true, this, null);
				Constants.setRefresh(true);
			}
		}else {
			logger.info("do nothing");
		}
	}

	// 读取znode数据
	public void processResult(int rc, String path, Object ctx, Stat stat) {
		logger.info("rc: {}, path:{}, ctx: {}, stat: {}", new Object[] {rc, path, ctx, stat});
		
		switch (rc) {
		case Code.Ok:
			logger.info("statcallback proess result Ok");
			break;
		case Code.NoNode:
			logger.info("statcallback proess result NoNode");
			break;
		case Code.ConnectionLoss:
			logger.info("statcallback proess result ConnectionLoss");
			break;
		case Code.SessionExpired:
			logger.info("statcallback proess result SessionExpired");
			break;
		case Code.OperationTimeout:
			logger.info("statcallback proess result OperationTimeout");
			break;
		default:
			zk.exists(Constants.ZK_ZNODE_SHIRO_CACHE, true, this, null);
			break;
		}
		
		try {
			byte[] bytes = zk.getData(Constants.ZK_ZNODE_SHIRO_CACHE, false, null);
			long timestamp = Long.valueOf(new String(bytes, 0, bytes.length));
			SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			logger.info("修改时间: " + format.format(new Date(timestamp)));
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
