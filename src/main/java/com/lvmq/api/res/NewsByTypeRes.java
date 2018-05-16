package com.lvmq.api.res;

import java.util.List;

public class NewsByTypeRes {
	
	private List<NewsInfoRes> newsInfoArray;
	
	public NewsByTypeRes() {
		// TODO Auto-generated constructor stub
	}
	
	public NewsByTypeRes(List<NewsInfoRes> newsInfoArray) {
		// TODO Auto-generated constructor stub
		
		this.newsInfoArray=newsInfoArray;
	}


	public List<NewsInfoRes> getNewsInfoArray() {
		return newsInfoArray;
	}

	public void setNewsInfoArray(List<NewsInfoRes> newsInfoArray) {
		this.newsInfoArray = newsInfoArray;
	}
	
	
		
}
