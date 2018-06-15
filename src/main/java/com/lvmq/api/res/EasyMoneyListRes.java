package com.lvmq.api.res;

public class EasyMoneyListRes {
	private String id;
	
	private String title;
	
	private String img;
	
	public EasyMoneyListRes() {
		// TODO Auto-generated constructor stub
	}
	
	public EasyMoneyListRes(String id,String title,String img) {
		// TODO Auto-generated constructor stub
		this.id=id;
		this.title=title;
		this.img=img;
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
	
	
}
