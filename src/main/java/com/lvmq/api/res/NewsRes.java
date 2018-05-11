package com.lvmq.api.res;

import java.util.List;

public class NewsRes {

	private List<NewsByTypeRes> newsByType;
	
	private List<AdvertRes> advertArray;
	
	public NewsRes() {
		// TODO Auto-generated constructor stub
	}
	
	
	public NewsRes(List<NewsByTypeRes> newsByType,List<AdvertRes> advertArray) {
		// TODO Auto-generated constructor stub
		this.newsByType=newsByType;
		this.advertArray=advertArray;
	}
	
	

	public List<NewsByTypeRes> getNewsByType() {
		return newsByType;
	}

	public void setNewsByType(List<NewsByTypeRes> newsByType) {
		this.newsByType = newsByType;
	}


	public List<AdvertRes> getAdvertArray() {
		return advertArray;
	}


	public void setAdvertArray(List<AdvertRes> advertArray) {
		this.advertArray = advertArray;
	}
	
	
}
