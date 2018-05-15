package com.lvmq.api.res;

public class NewsTypeRes {
	private String catId;
	
	private String catValue;
	
	public NewsTypeRes() {
		// TODO Auto-generated constructor stub
	}
	
	public NewsTypeRes(String catId,String catValue) {
		// TODO Auto-generated constructor stub
		this.catId=catId;
		this.catValue=catValue;
	}

	public String getCatId() {
		return catId;
	}

	public void setCatId(String catId) {
		this.catId = catId;
	}

	public String getCatValue() {
		return catValue;
	}

	public void setCatValue(String catValue) {
		this.catValue = catValue;
	}
	
	
}
