package com.lvmq.api.res;

import com.lvmq.model.MakeMoney;

public class MakeMoneyRes {
	
	private String id;
	
	private String title;
	
	private String description;
	
	private String cash;
	
	private String type;
	
	//状态：0未参与,1待上传，2待审批，3审批通过，4审批不通过
	private String status;
	
	private String endTime;
	
	
	public MakeMoneyRes() {
		// TODO Auto-generated constructor stub
	}
	
	public MakeMoneyRes(MakeMoney m) {
		// TODO Auto-generated constructor stub
		this.id=m.getId();
		this.title=m.getTitle();
		this.description=m.getLineThree();
		this.cash=m.getCash();
		this.type=m.getType();
		this.status="0";
		this.endTime="";
		
	}
	
	public MakeMoneyRes(MakeMoney m,int status,String endTime) {
		// TODO Auto-generated constructor stub
		this.id=m.getId();
		this.title=m.getTitle();
		this.description=m.getLineThree();
		this.cash=m.getCash();
		this.type=m.getType();
		this.status=String.valueOf(status);
		this.endTime=endTime;
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

	

	public String getCash() {
		return cash;
	}

	public void setCash(String cash) {
		this.cash = cash;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
