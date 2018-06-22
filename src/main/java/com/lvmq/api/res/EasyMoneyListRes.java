package com.lvmq.api.res;

import com.lvmq.base.Consts;

public class EasyMoneyListRes {
	private String id;
	
	private String title;
	
	private String img;
	
	private String gold;
	
	public EasyMoneyListRes() {
		// TODO Auto-generated constructor stub
	}
	
	public EasyMoneyListRes(String id,String title,String img,String gold) {
		// TODO Auto-generated constructor stub
		this.id=id;
		this.title=title;
		this.img=img;
		this.gold=gold;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getGold() {
		return gold;
	}

	public void setGold(String gold) {
		this.gold = gold;
	}
	
	
}
