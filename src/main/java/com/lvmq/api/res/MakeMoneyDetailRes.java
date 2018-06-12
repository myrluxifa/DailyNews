package com.lvmq.api.res;

import com.lvmq.model.MakeMoney;

public class MakeMoneyDetailRes {
	private MakeMoney makeMoney;
	
	private String status;

	private String endTime;
	
	public MakeMoneyDetailRes() {
		// TODO Auto-generated constructor stub
	}
	
	public MakeMoneyDetailRes(MakeMoney m,int status,String endTime) {
		// TODO Auto-generated constructor stub
		this.makeMoney=m;
		this.status=String.valueOf(status);
		this.endTime=endTime;
	}
	
	public MakeMoney getMakeMoney() {
		return makeMoney;
	}

	public void setMakeMoney(MakeMoney makeMoney) {
		this.makeMoney = makeMoney;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	
}
