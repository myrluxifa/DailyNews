package com.lvmq.api.res.base;

import com.lvmq.base.Code;

import io.swagger.annotations.ApiParam;

public class ResponseBean<T> {
	
	@ApiParam(name="状态：SUCCESS/FAIL")
	private String return_code;
	
	@ApiParam(name="代码")
	private String code;
	
	@ApiParam(name="返回信息")
	private String return_msg;
	
	@ApiParam(name="返回数据")
	private T data;
	
	
	public ResponseBean() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public ResponseBean(String return_code,String code,String return_msg) {
		// TODO Auto-generated constructor stub
		this.return_code=return_code;
		this.return_msg=return_msg;
		this.code=code;
	}
	
	
	public ResponseBean(String return_code,String code,String return_msg,T data) {
		// TODO Auto-generated constructor stub
		this.return_code=return_code;
		this.return_msg=return_msg;
		this.code=code;
		this.data=data;
	}
	
	

	public String getCode() {
		return code;
	}



	public void setCode(String code) {
		this.code = code;
	}



	public ResponseBean(String return_code,String return_msg ,T data) {
		// TODO Auto-generated constructor stub
		this.return_code=return_code;
		this.return_msg=return_msg;
		this.data=data;
	}

	public String getReturn_code() {
		return return_code;
	}

	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}


	public String getReturn_msg() {
		return return_msg;
	}

	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	
	
	
}
