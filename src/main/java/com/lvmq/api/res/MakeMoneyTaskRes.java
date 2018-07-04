package com.lvmq.api.res;


public class MakeMoneyTaskRes {
	private String id;
	
	private String logo;
	
	private String title;
	
	private String endTime;
	
	private String status;
	
	private String rewards;
	
	private String mkId;

	
	public MakeMoneyTaskRes() {
		// TODO Auto-generated constructor stub
	}
	
	
	public MakeMoneyTaskRes(String id,String logo,String title,String endTime,String status,String rewards,String mkId) {
		// TODO Auto-generated constructor stub
		this.id=id;
		this.logo=logo;
		this.title=title;
		this.endTime=endTime;
		this.status=status;
		this.rewards=rewards;
		this.mkId=mkId;
	}
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRewards() {
		return rewards;
	}

	public void setRewards(String rewards) {
		this.rewards = rewards;
	}


	public String getMkId() {
		return mkId;
	}


	public void setMkId(String mkId) {
		this.mkId = mkId;
	}


	
	
	
}
