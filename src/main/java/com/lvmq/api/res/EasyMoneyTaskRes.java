package com.lvmq.api.res;

public class EasyMoneyTaskRes {
	private String id;
	
	private String img;
	
	private String title;
	
	private String cnt;
	
	private String rewards;
	
	private String flag;
	
	
	public EasyMoneyTaskRes() {
		// TODO Auto-generated constructor stub
	}
	
	public EasyMoneyTaskRes(Object[] obj) {
		// TODO Auto-generated constructor stub
		
		this.id=String.valueOf(obj[2]);
		this.img=String.valueOf(obj[1]);
		this.title=String.valueOf(obj[0]);
		this.cnt=String.valueOf(obj[4]);
		this.rewards=String.valueOf(obj[5]);
		this.flag=String.valueOf(obj[6]);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCnt() {
		return cnt;
	}

	public void setCnt(String cnt) {
		this.cnt = cnt;
	}

	public String getRewards() {
		return rewards;
	}

	public void setRewards(String rewards) {
		this.rewards = rewards;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	
	
}
