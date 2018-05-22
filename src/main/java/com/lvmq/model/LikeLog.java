package com.lvmq.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="t_like_log")
public class LikeLog {
	
	@Id
	@GenericGenerator(name="system-uuid",strategy="uuid")
	@GeneratedValue(generator="system-uuid")
	private String id;
	
	private String type;
	
	private String userId;
	
	
	private String outId;
	
	private Date createTime;
	
	public LikeLog() {
		// TODO Auto-generated constructor stub
	}
	
	public LikeLog(String outId,String userId,String type) {
		// TODO Auto-generated constructor stub
		this.type=type;
		this.userId=userId;
		this.outId=outId;
	}
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOutId() {
		return outId;
	}

	public void setOutId(String outId) {
		this.outId = outId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	
}
