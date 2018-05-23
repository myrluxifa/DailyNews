package com.lvmq.api.res;

import com.lvmq.model.InviteImgBanner;

public class ImgBanner {
	private String id;
	
	private String url;
	
	private String imgUrl;
	
	public ImgBanner() {
		// TODO Auto-generated constructor stub
	}
	
	
	public ImgBanner(InviteImgBanner b) {
		// TODO Auto-generated constructor stub
		this.id=b.getId();
		this.url=b.getUrl();
		this.imgUrl=b.getImgUrl();
	}
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	
}
