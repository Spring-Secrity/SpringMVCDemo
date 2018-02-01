/**
 * 
 */
package org.chench.test.shiro.spring.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @desc org.chench.test.shiro.spring.controller.HomeController
 * @author chench9@lenovo.com
 * @date 2017年2月9日
 */
@Controller
public class HomeController {
	@RequestMapping("/home")
	//@RequiresPermissions(value={"log:manage:*"})
	public ModelAndView home(HttpServletRequest req) {
		ModelAndView mv = new ModelAndView("home");
		return mv;
	}
}




