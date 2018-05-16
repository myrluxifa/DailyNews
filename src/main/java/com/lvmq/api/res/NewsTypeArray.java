package com.lvmq.api.res;

import java.util.List;

public class NewsTypeArray {
	private List<NewsTypeRes> newsTypeRes;
	
	public NewsTypeArray() {
		// TODO Auto-generated constructor stub
	}
	
	public NewsTypeArray(List<NewsTypeRes> newsTypeRes) {
		// TODO Auto-generated constructor stub
		this.newsTypeRes=newsTypeRes;
	}

	public List<NewsTypeRes> getNewsTypeRes() {
		return newsTypeRes;
	}

	public void setNewsTypeRes(List<NewsTypeRes> newsTypeRes) {
		this.newsTypeRes = newsTypeRes;
	}
	
	
}
