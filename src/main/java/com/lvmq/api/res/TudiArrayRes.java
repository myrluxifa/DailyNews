package com.lvmq.api.res;

import java.util.List;

public class TudiArrayRes {
	private String cnt;
	
	private String cash;
	
	private List<TudiRes> tudiArray;
	
	public TudiArrayRes() {
		// TODO Auto-generated constructor stub
	}
	
	public TudiArrayRes(String cnt,String cash,List<TudiRes> tudiArray) {
		// TODO Auto-generated constructor stub
		this.cnt=cnt;
		this.cash=cash;
		this.tudiArray=tudiArray;
	}

	public String getCnt() {
		return cnt;
	}

	public void setCnt(String cnt) {
		this.cnt = cnt;
	}

	public String getCash() {
		return cash;
	}

	public void setCash(String cash) {
		this.cash = cash;
	}

	public List<TudiRes> getTudiArray() {
		return tudiArray;
	}

	public void setTudiArray(List<TudiRes> tudiArray) {
		this.tudiArray = tudiArray;
	}
	
	
}
