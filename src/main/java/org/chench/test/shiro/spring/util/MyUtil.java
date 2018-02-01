/**
 * 
 */
package org.chench.test.shiro.spring.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @desc org.chench.test.shiroweb.util.MyUtil
 * @author chench9@lenovo.com
 * @date 2017年3月9日
 */
public class MyUtil {
	public static String redirect(HttpServletRequest req, HttpServletResponse resp, String uri) throws IOException {
		return new StringBuilder().append("redirect:").append(uri).toString().trim();
	}

	private MyUtil(){
	}
}
