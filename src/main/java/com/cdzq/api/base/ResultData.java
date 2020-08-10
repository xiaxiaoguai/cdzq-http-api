package com.cdzq.api.base;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用返回值
 */

@Data
public class ResultData {
	@ApiModelProperty(value = "请求状态")
	private boolean success;
	@ApiModelProperty(value = "请求描述")
	private String message;
	@ApiModelProperty(value = "http状态码")
	private Integer httpcode;
	@ApiModelProperty(value = "请求状态码")
	private Integer resultcode;
	@ApiModelProperty(value = "请求结果")
	private Map<String, Object> data = new HashMap<String, Object>();


	//成功静态方法
	public static ResultData ok() {
		ResultData r = new ResultData();
		r.setSuccess(true);
		r.setHttpcode(200);
		r.setResultcode(0);
		r.setMessage("success");
		return r;
	}

	//失败静态方法
	public static ResultData error() {
		ResultData r = new ResultData();
		r.setSuccess(false);
		r.setHttpcode(200);
		r.setResultcode(-1);
		r.setMessage("fail");
		return r;
	}


	public ResultData success(Boolean success){
		this.setSuccess(success);
		return this;
	}

	public ResultData message(String message){
		this.setMessage(message);
		return this;
	}

	public ResultData httpcode(Integer httpcode){
		this.setHttpcode(httpcode);
		return this;
	}

	public ResultData resultcode(Integer resultcode){
		this.setResultcode(resultcode);
		return this;
	}

	public ResultData data(String key, Object value){
		this.data.put(key, value);
		return this;
	}

	public ResultData data(Map<String, Object> map){
		this.setData(map);
		return this;
	}


}
