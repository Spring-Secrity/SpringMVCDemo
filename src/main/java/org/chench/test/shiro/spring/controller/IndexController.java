/**
 * 
 */
package org.chench.test.shiro.spring.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ConcurrentAccessException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.chench.test.shiro.spring.listener.MySessionListener;
import org.chench.test.shiro.spring.util.MyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * 首页
 * @desc org.chench.test.shiro.spring.controller.IndexController
 * @author chench9@lenovo.com
 * @date 2017年2月9日
 */
@Controller
public class IndexController {
	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest req, HttpServletResponse resp) {
		req.getSession().setAttribute("test", new MySessionListener());
		ModelAndView mv = new ModelAndView("index");
		return mv; 
	}
	
	@RequestMapping("/login")
	public String login(HttpServletRequest req, HttpServletResponse resp,
			@RequestParam("username") String username, 
			@RequestParam("password") String password,
			@RequestParam(value="rememberMe", required=false) boolean rememberMe) throws ServletException, IOException {
		Subject currentUser =  SecurityUtils.getSubject();
		Exception ex = null;
		if(!currentUser.isAuthenticated()) {
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			try {
				currentUser.login(token);
			} catch (UnknownAccountException e) {
				ex = e;
				logger.error(String.format("user not found: %s", username), e);
			} catch(IncorrectCredentialsException e) {
				ex = e;
				logger.error(String.format("user: %s pwd: %s error", username, password), e);
			} catch (ConcurrentAccessException e) {
				ex = e;
				logger.error(String.format("user has been authenticated: %s", username), e);
			} catch (AuthenticationException e) {
				ex = e;
			    logger.error(String.format("account except: %s", username), e);
			}
		}
		
		if(ex != null) {
			req.setAttribute("ex", ex);
			return "index";
		}
		return MyUtil.redirect(req, resp, "/home");
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		SecurityUtils.getSubject().logout();
		req.getSession().invalidate();
		return MyUtil.redirect(req, resp, "/index");
	}

}
