package com.lvmq.model;

import java.util.Date;

public class MessageCode {
	private String code;
	
	private Long time;
	
	
	
	public MessageCode() {
		// TODO Auto-generated constructor stub
	}
	
	
	public MessageCode(String code) {
		// TODO Auto-generated constructor stub
		this.code=code;
		this.time=new Date().getTime();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}
	
	
}
