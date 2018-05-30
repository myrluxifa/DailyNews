package com.lvmq.api.res;

public class InviteInfoRes {
	private String income;
	
	private String inviteCount;
	
	private String myInviteCode;
	
	
	public InviteInfoRes() {
		// TODO Auto-generated constructor stub
	}
	
	public InviteInfoRes(String income,String inviteCount,String myInviteCode) {
		// TODO Auto-generated constructor stub
		this.income=income;
		this.inviteCount=inviteCount;
		this.myInviteCode=myInviteCode;
	}

	public String getIncome() {
		return income;
	}

	public void setIncome(String income) {
		this.income = income;
	}

	public String getInviteCount() {
		return inviteCount;
	}

	public void setInviteCount(String inviteCount) {
		this.inviteCount = inviteCount;
	}

	public String getMyInviteCode() {
		return myInviteCode;
	}

	public void setMyInviteCode(String myInviteCode) {
		this.myInviteCode = myInviteCode;
	}
	
	
}
