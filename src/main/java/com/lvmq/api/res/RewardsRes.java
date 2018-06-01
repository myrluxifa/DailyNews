package com.lvmq.api.res;

public class RewardsRes {
	private String used;
	
	private String cnt;

	
	
	public RewardsRes() {
		// TODO Auto-generated constructor stub
	}
	
	public RewardsRes(String used,String cnt) {
		// TODO Auto-generated constructor stub
		this.used=used;
		this.cnt=cnt;
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
	
	
}
