/**
 * 
 */
package org.chench.test.shiro.spring.listener;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

/**
 * @desc org.chench.test.shiro.spring.listener.MySessionListener
 * @author chench9@lenovo.com
 * @date 2017年3月8日
 */
public class MySessionListener implements HttpSessionBindingListener {

	public void valueBound(HttpSessionBindingEvent event) {
		System.out.println("valueBound");
	}

	public void valueUnbound(HttpSessionBindingEvent event) {
		System.out.println("valueUnbound");
		System.out.println(event.getName() + " = " + event.getValue());
		System.out.println("event source: " + event.getSource());
		System.out.println("session: " + event.getSession());
		System.out.println(event.getSession().getAttribute("test"));
	}

}
