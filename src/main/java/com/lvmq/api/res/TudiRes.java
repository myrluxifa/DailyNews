package com.lvmq.api.res;

public class TudiRes {
	private String headPortrait;
	
	private String userName;

	private String nickName;
	
	private String cash;
	
	public TudiRes() {
		// TODO Auto-generated constructor stub
	}
	
	public TudiRes(String headPortrait,String userName,String nickName,String cash) {
		// TODO Auto-generated constructor stub
		this.headPortrait=headPortrait;
		this.userName=userName;
		this.nickName=nickName;
		this.cash=cash;
	}

	public String getHeadPortrait() {
		return headPortrait;
	}

	public void setHeadPortrait(String headPortrait) {
		this.headPortrait = headPortrait;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getCash() {
		return cash;
	}

	public void setCash(String cash) {
		this.cash = cash;
	}
	
	

}
