package com.lvmq.api.res;

import java.util.List;

import com.lvmq.model.AdvertInfo;

public class AdvertRes {
	private String id;
	
	private String title;
	
	private String url;
	
	private String readNum;
	
	private List<String> imgs;
	
	
	public AdvertRes() {
		// TODO Auto-generated constructor stub
	}
	
	public AdvertRes(AdvertInfo advertInfo,List<String> imgs ) {
		// TODO Auto-generated constructor stub
		this.id=advertInfo.getId();
		this.title=advertInfo.getTitle();
		this.url=advertInfo.getUrl();
		this.readNum=advertInfo.getReadNum();
		this.imgs=imgs;
		
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getReadNum() {
		return readNum;
	}

	public void setReadNum(String readNum) {
		this.readNum = readNum;
	}

	public List<String> getImgs() {
		return imgs;
	}

	public void setImgs(List<String> imgs) {
		this.imgs = imgs;
	}
	
	
	
}
