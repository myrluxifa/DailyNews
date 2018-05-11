package com.lvmq.api.res;

import java.util.List;

public class NewsByTypeRes {
	private String catId;
	
	private String catValue;
	
	private List<NewsInfoRes> newsInfoArray;
	
	public NewsByTypeRes() {
		// TODO Auto-generated constructor stub
	}
	
	public NewsByTypeRes(String catId,String catValue,List<NewsInfoRes> newsInfoArray) {
		// TODO Auto-generated constructor stub
		this.catId=catId;
		this.catValue=catValue;
		this.newsInfoArray=newsInfoArray;
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

	public List<NewsInfoRes> getNewsInfoArray() {
		return newsInfoArray;
	}

	public void setNewsInfoArray(List<NewsInfoRes> newsInfoArray) {
		this.newsInfoArray = newsInfoArray;
	}
	
	
		
}
