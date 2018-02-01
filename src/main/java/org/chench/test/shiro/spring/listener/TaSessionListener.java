/**
 * 
 */
package org.chench.test.shiro.spring.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @desc org.chench.test.shiro.spring.listener.TaSessionListener
 * @author chench9@lenovo.com
 * @date 2017年3月8日
 */
public class TaSessionListener implements HttpSessionListener {

	public void sessionCreated(HttpSessionEvent se) {
		System.out.println("sessionCreated: " + se.getSession() + "--" + se.getSession().getAttribute("test"));
	}

	public void sessionDestroyed(HttpSessionEvent se) {
		System.out.println("sessionDestroyed: " + se.getSession() + "--" + se.getSession().getAttribute("test") + ", isNew: " + se.getSession().isNew());
	}

}
