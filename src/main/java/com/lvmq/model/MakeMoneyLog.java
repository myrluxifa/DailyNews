package com.lvmq.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="t_make_money_log")
public class MakeMoneyLog {
	
	@Id
	@GenericGenerator(name="system-uuid",strategy="uuid")
	@GeneratedValue(generator="system-uuid")
	private String id;
	
	private String userId;
	
	private String makeMoneyId;
	
	private int status;
	
	private Date createTime;
	
	private Date updateTime;
	
	private String updateUser;
	
	private Date 	endTime;
	
	private String imgs;
	
	public MakeMoneyLog() {
		// TODO Auto-generated constructor stub
	}
	
	public MakeMoneyLog(String userId,String makeMoneyId,int status,Date endTime) {
		// TODO Auto-generated constructor stub
		this.userId=userId;
		this.makeMoneyId=makeMoneyId;
		this.createTime=new Date();
		this.updateTime=new Date();
		this.status=status;
		this.endTime=endTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMakeMoneyId() {
		return makeMoneyId;
	}

	public void setMakeMoneyId(String makeMoneyId) {
		this.makeMoneyId = makeMoneyId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getImgs() {
		return imgs;
	}

	public void setImgs(String imgs) {
		this.imgs = imgs;
	}
	
	
}
