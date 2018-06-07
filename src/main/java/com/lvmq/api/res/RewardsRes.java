package com.lvmq.api.res;

public class RewardsRes {
	private String used;
	
	private String cnt;

	private String gold;
	
	public RewardsRes() {
		// TODO Auto-generated constructor stub
	}
	
	public RewardsRes(String used,String cnt,String gold) {
		// TODO Auto-generated constructor stub
		this.used=used;
		this.cnt=cnt;
		this.gold=gold;
	}
	
	public String getUsed() {
		return used;
	}

	public void setUsed(String used) {
		this.used = used;
	}

	public String getCnt() {
		return cnt;
	}

	public void setCnt(String cnt) {
		this.cnt = cnt;
	}

	public String getGold() {
		return gold;
	}

	public void setGold(String gold) {
		this.gold = gold;
	}
	
	
}
