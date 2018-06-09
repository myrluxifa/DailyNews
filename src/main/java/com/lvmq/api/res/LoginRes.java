package com.lvmq.api.res;

import com.lvmq.model.UserLogin;

public class LoginRes {
	private String user_id;
	
	private String phone;
	
	private String head_portrait;
	
	private String gold;
	
	private String my_invite_code;
	
	private String balance;
	
	private String earnings;
	
	private String invite_code;
	
	public LoginRes() {
		// TODO Auto-generated constructor stub
	}
	
	public LoginRes(UserLogin userLogin) {
		// TODO Auto-generated constructor stub
		this.user_id=userLogin.getId();
		this.phone=userLogin.getUserName();
		this.head_portrait=userLogin.getHeadPortrait()==null?"":userLogin.getHeadPortrait();
		this.my_invite_code=userLogin.getMyInviteCode();
		this.gold=String.valueOf(userLogin.getGold());
		this.balance=String.valueOf(userLogin.getBalance());
		this.earnings=String.valueOf(userLogin.getEarnings());
		this.invite_code=userLogin.getInviteCode()==null?"":userLogin.getInviteCode();
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getHead_portrait() {
		return head_portrait;
	}

	public void setHead_portrait(String head_portrait) {
		this.head_portrait = head_portrait;
	}

	public String getGold() {
		return gold;
	}

	public void setGold(String gold) {
		this.gold = gold;
	}

	public String getMy_invite_code() {
		return my_invite_code;
	}

	public void setMy_invite_code(String my_invite_code) {
		this.my_invite_code = my_invite_code;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getEarnings() {
		return earnings;
	}

	public void setEarnings(String earnings) {
		this.earnings = earnings;
	}

	public String getInvite_code() {
		return invite_code;
	}

	public void setInviteCode(String invite_code) {
		this.invite_code = invite_code;
	}
	
	
}
