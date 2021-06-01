package com.cdzq.api.exception;

import com.cdzq.api.base.ResultData;
import com.cdzq.api.util.ResponseUtil;
import com.cdzq.api.util.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常拦截全部的Exception
 * @author Administrator
 *
 */

@Slf4j
@RestControllerAdvice
public class AllException {

	//参数校验失败
	@ExceptionHandler(BindException.class)
	public void badRequestException(BindException e, HttpServletResponse response) {
		ResultData resultData = ResultData.error();
		resultData.setHttpcode(200);
		resultData.setResultcode(-1);
		BindingResult bindingResult = e.getBindingResult();
		String errorMesssage = "";
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			errorMesssage += errorMesssage.equals("")?fieldError.getDefaultMessage():","+fieldError.getDefaultMessage();
		}
		resultData.setMessage(errorMesssage);
		resultData.setSuccess(false);
		ResponseUtil.out(response, resultData);
	}

	//处理自定义异常
	@ExceptionHandler(BadRequestException.class)
	public void badRequestException(BadRequestException e, HttpServletResponse response) {
		ResultData resultData = ResultData.error();
		resultData.setHttpcode(e.getHttp_code());
		resultData.setResultcode(e.getResult_code());
		resultData.setMessage(e.getMessage());
		resultData.setSuccess(false);
		ResponseUtil.out(response, resultData);
	}

	//未知的系统报错异常
	@ExceptionHandler(Exception.class)
	public ModelAndView handlerException(Exception e, HttpServletRequest request, HttpServletResponse response) {
		e.printStackTrace();
		StringBuffer sb = new StringBuffer();
		StackTraceElement stackTraceElement= e.getStackTrace()[0];
		sb.append("Message="+e.getMessage());
		sb.append("&Class="+stackTraceElement.getClassName());
		sb.append("&File="+stackTraceElement.getFileName());
		sb.append("&Method="+stackTraceElement.getMethodName());
		sb.append("&Line="+stackTraceElement.getLineNumber());
		sb.append("&Ip="+ IpUtils.getIp(request));
		sb.append("&Url="+request.getRequestURL());
		sb.append("&HttpType="+request.getMethod());
		String parameters = "";
		for(String key:request.getParameterMap().keySet()){
			parameters += (key+"="+request.getParameter(key)+",");
		}
		if(parameters.length()>0){
			parameters = parameters.substring(0, parameters.length()-1);
		}
		sb.append("&Parameters=‘"+parameters+"’");
		
		log.error(sb.toString());
		
		/**
		 * 定义返回结果
		 */
		ResultData resultData = ResultData.error();
		resultData.setHttpcode(500);
		resultData.setResultcode(500);
		resultData.setMessage("500 error:系统内部错误");
		resultData.setSuccess(false);
		/**
		 * 判断是页面请求还是JSON请求
		 */
		boolean method_return_is_view = false;
		Object o = request.getAttribute("method_return_is_view");
		if (o != null && (Boolean) o) {
			method_return_is_view = true;
		}

		ModelAndView mv;
		if (method_return_is_view) { //返回页面
			mv = new ModelAndView("error/500");
			mv.addObject("resultData", resultData);
			return mv;
		} else {//返回JSON
			ResponseUtil.out(response, resultData);
			return null;
		}

	}

}
