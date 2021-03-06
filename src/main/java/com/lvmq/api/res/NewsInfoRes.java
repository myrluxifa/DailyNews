package com.lvmq.api.res;

import java.text.SimpleDateFormat;
import java.util.List;

import com.lvmq.model.NewsInfo;
import com.lvmq.util.ArrayUtil;
import com.lvmq.util.RelativeDateFormat;

public class NewsInfoRes {
	private String id;
	
	private String publishDate;
	
	private String posterScreenName;
	
	private String url;
	
	private String title;
	
	private String posterId;
	
	private String viewCount;
	
	private String content;
	
	private List<String> imgsUrl;
	
	private String ifRead;
	
	private String newsType;
	
	private String redpackage;
	
	private String redMoney;
	
	
	public NewsInfoRes() {
		// TODO Auto-generated constructor stub
	}
	
	public NewsInfoRes(NewsInfo newsInfo,String redPackage,String redMoney,int ifRead) {
		// TODO Auto-generated constructor stub
		this.id=newsInfo.getId();
		this.publishDate=RelativeDateFormat.format(newsInfo.getPublishDate());
		this.posterScreenName=newsInfo.getPosterScreenName();
		this.url=newsInfo.getUrl();
		this.title=newsInfo.getTitle();
		this.posterId=newsInfo.getPosterId();
		this.viewCount=newsInfo.getViewCount().toString();
		this.content=newsInfo.getContent();
		this.imgsUrl=ArrayUtil.stringToList(newsInfo.getImgsUrl());
		this.ifRead=String.valueOf(ifRead);
		this.newsType=ArrayUtil.getNewsTypeOrAdType(newsInfo.getImgsUrl());
		this.redpackage=redPackage;
		this.redMoney=redMoney;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public String getPosterScreenName() {
		return posterScreenName;
	}

	public void setPosterScreenName(String posterScreenName) {
		this.posterScreenName = posterScreenName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPosterId() {
		return posterId;
	}

	public void setPosterId(String posterId) {
		this.posterId = posterId;
	}

	public String getViewCount() {
		return viewCount;
	}

	public void setViewCount(String viewCount) {
		this.viewCount = viewCount;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<String> getImgsUrl() {
		return imgsUrl;
	}

	public void setImgsUrl(List<String> imgsUrl) {
		this.imgsUrl = imgsUrl;
	}

	public String getIfRead() {
		return ifRead;
	}

	public void setIfRead(String ifRead) {
		this.ifRead = ifRead;
	}

	public String getNewsType() {
		return newsType;
	}

	public void setNewsType(String newsType) {
		this.newsType = newsType;
	}

	public String getRedpackage() {
		return redpackage;
	}

	public void setRedpackage(String redpackage) {
		this.redpackage = redpackage;
	}

	public String getRedMoney() {
		return redMoney;
	}

	public void setRedMoney(String redMoney) {
		this.redMoney = redMoney;
	}
	
	
	
}
