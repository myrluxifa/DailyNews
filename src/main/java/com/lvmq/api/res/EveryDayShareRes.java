package com.lvmq.api.res;

public class EveryDayShareRes {
	private String inviteCode;
	
	private String headPortrait;
	
	private String phone;
	
	private String money;

	public EveryDayShareRes() {
		// TODO Auto-generated constructor stub
		this.inviteCode="";
		this.headPortrait="";
		this.phone="";
		this.money="0.00";
	}
	
	
	public EveryDayShareRes(String inviteCode,String headPortrait,String money,String phone) {
		// TODO Auto-generated constructor stub
		this.inviteCode=inviteCode;
		this.headPortrait=headPortrait;
		this.phone=phone;
		this.money=money;
	}
	
	
	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

	public String getHeadPortrait() {
		return headPortrait;
	}

	public void setHeadPortrait(String headPortrait) {
		this.headPortrait = headPortrait;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	
}
