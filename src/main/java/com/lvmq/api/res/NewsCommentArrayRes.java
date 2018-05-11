package com.lvmq.api.res;

import java.util.List;

public class NewsCommentArrayRes {
	private List<NewsCommentRes> newsCommentRes;
	
	
	public NewsCommentArrayRes() {
		// TODO Auto-generated constructor stub
	}
	
	public NewsCommentArrayRes(List<NewsCommentRes> newsCommentRes) {
		// TODO Auto-generated constructor stub
		this.newsCommentRes=newsCommentRes;
	}

	public List<NewsCommentRes> getNewsCommentRes() {
		return newsCommentRes;
	}

	public void setNewsCommentRes(List<NewsCommentRes> newsCommentRes) {
		this.newsCommentRes = newsCommentRes;
	}
	
	
}
