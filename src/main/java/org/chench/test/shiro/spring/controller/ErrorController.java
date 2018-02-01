/**
 * 
 */
package org.chench.test.shiro.spring.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @desc org.chench.test.shiro.spring.controller.ErrorController
 * @author chench9@lenovo.com
 * @date 2017年3月16日
 */
@Controller
public class ErrorController {
	@RequestMapping("/error")
	public ModelAndView error(HttpServletRequest req, HttpServletResponse resp) {
		ModelAndView mv = new ModelAndView("error/error");
		return mv;
	}
	
	/**
	 * 未被认证
	 * @param req
	 * @return
	 */
	@RequestMapping("/unauthorized")
	public ModelAndView unauthorized(HttpServletRequest req) {
		return new ModelAndView("error/unauthorized");
	}
}
