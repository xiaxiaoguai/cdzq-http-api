package com.cdzq.api.exception;

import com.cdzq.api.base.ResultData;
import com.cdzq.api.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 拦截404
 * @author Administrator
 *
 */
@Slf4j
@RestController
public class NotFoundException implements ErrorController {
 
    @Override
    public String getErrorPath() {
        return "/error";
    }
 
    @RequestMapping(value = {"/error"})
    public ResultData error(HttpServletRequest request) {
    	StringBuffer sb = new StringBuffer();
    	sb.append("Message=Not Found 404");
    	sb.append("&Ip="+ StringUtils.getIp(request));
		sb.append("&Url="+request.getAttribute("RealUrl"));
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
		resultData.setHttpcode(404);
		resultData.setResultcode(404);
		resultData.setMessage("error:404 Not Found");
		resultData.setSuccess(false);
		
        return resultData;
    }
}