package com.youtu.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.youtu.common.utils.CookieUtils;

/**
 * @author:王贤锐
 * @date:2018年1月24日 下午3:28:27
 **/
public class LoginInterceptor implements HandlerInterceptor {


	/**
	 * 拦截器执行之前处理，返回值决定是否执行，true表示执行，false表示不执行
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 在Handler执行之前处理
		// 判断用户是否登录
		// 从cookie中取昵称
		String userNick = CookieUtils.getCookieValue(request, "YOUTU_ADMIN");
		
		//防止拦截登录页面
		String uri = request.getRequestURI();
		String file[] = uri.split("/");
		if(file[file.length-1].equals("login")){
			return true;
		}
		
		// 取不到用户信息
		if (StringUtils.isBlank(userNick)) {
			// 跳转到登录页面，把用户请求的url作为参数传递给登录页面。
			response.sendRedirect("http://localhost:8080/login?redirect=" + request.getRequestURL());
			// 返回false
			return false;
		}
		// 返回值决定handler是否执行。true：执行，false：不执行。
		return true;
	}

	/**
	 * 拦截器执行之后，返回ModelAndView之前
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * 拦截器执行之后处理
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
