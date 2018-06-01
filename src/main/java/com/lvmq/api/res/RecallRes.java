package com.lvmq.api.res;

import java.util.List;

public class RecallRes {
	private List<RecallListRes> recallList;

	
	public RecallRes() {
		// TODO Auto-generated constructor stub
	}
	
	public RecallRes(List<RecallListRes> recallList) {
		// TODO Auto-generated constructor stub
		this.recallList=recallList;
	}
	
	public List<RecallListRes> getRecallList() {
		return recallList;
	}

	public void setRecallList(List<RecallListRes> recallList) {
		this.recallList = recallList;
	}
	
}
