package com.lvmq.api.res;

import java.util.List;

public class BannerRes {
	private List<ImgBanner> imgBannerArray;
	
	private List<String> charBannerArray;
	
	public BannerRes() {
		// TODO Auto-generated constructor stub
	}
	
	public BannerRes(List<ImgBanner> imgBannerArray,List<String> charBannerArray) {
		// TODO Auto-generated constructor stub
		this.imgBannerArray=imgBannerArray;
		this.charBannerArray=charBannerArray;
	}

	public List<ImgBanner> getImgBannerArray() {
		return imgBannerArray;
	}

	public void setImgBannerArray(List<ImgBanner> imgBannerArray) {
		this.imgBannerArray = imgBannerArray;
	}

	public List<String> getCharBannerArray() {
		return charBannerArray;
	}

	public void setCharBannerArray(List<String> charBannerArray) {
		this.charBannerArray = charBannerArray;
	}
	
	
}
