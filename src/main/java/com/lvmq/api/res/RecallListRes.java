package com.lvmq.api.res;

public class RecallListRes {
	private String userId;
	
	private String phone;
	
	private String name;
	
	
	public RecallListRes() {
		// TODO Auto-generated constructor stub
	}
	
	public RecallListRes(String userId,String phone,String name) {
		// TODO Auto-generated constructor stub
		this.userId=userId;
		this.phone=phone;
		this.name=name;
		
	}
	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
