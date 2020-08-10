package com.cdzq.api.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Slf4j
public class BaseInterceptor implements HandlerInterceptor {

	/**
	 * 判断是否是Response请求，添加RealUrl
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		log.debug("RealUrl="+request.getRequestURL().toString());
		request.setAttribute("RealUrl", request.getRequestURL().toString());
		if (handler instanceof HandlerMethod) {
			HandlerMethod hm = (HandlerMethod) handler;
			Method method = hm.getMethod();
			boolean b1 = method.getReturnType().equals(String.class);
			boolean b2 = !method.isAnnotationPresent(ResponseBody.class);
			boolean b3 = !hm.getBeanType().isAnnotationPresent(RestController.class);
			boolean bf = b1 && b2 && b3;
			request.setAttribute("method_return_is_view", bf);
		}

		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
	

}
