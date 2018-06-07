package com.lvmq.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="t_recall_log")
public class RecallLog {
	@Id
	@GenericGenerator(name="system-uuid",strategy="uuid")
	@GeneratedValue(generator="system-uuid")
	private String id;
	
	private String userId;
	
	private String recallUser;
	
	private Date createTime;
	
	private int flag;
	
	public RecallLog() {
		// TODO Auto-generated constructor stub
	}
	
	
	public RecallLog(String userId,String recallUser) {
		// TODO Auto-generated constructor stub
		this.userId=userId;
		this.recallUser=recallUser;
		this.createTime=new Date();
		this.flag=0;
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

	public String getRecallUser() {
		return recallUser;
	}

	public void setRecallUser(String recallUser) {
		this.recallUser = recallUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public int getFlag() {
		return flag;
	}


	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	
	
}
