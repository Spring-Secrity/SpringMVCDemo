/**
 * 
 */
package org.chench.test.shiro.spring.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @desc org.chench.test.shiro.spring.interceptor.MyInterceptor
 * @author chench9@lenovo.com
 * @date 2017年2月8日
 */
public class MyInterceptor implements HandlerInterceptor {
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		// 在filter中获取shiro认证用户信息
		//System.out.println(String.format("在过滤器中获取shiro认证用户信息：%s", SecurityUtils.getSubject().getPrincipal()));
		
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
