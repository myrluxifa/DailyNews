package com.lvmq.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="t_news_info_read")
public class NewsInfoRead {
	@Id
	@GenericGenerator(name="system-uuid",strategy="uuid")
	@GeneratedValue(generator="system-uuid")
	private String id;
	
	private String userId;
	
	private String newsId;
	
	private Date createTime;
	
	private int flag;
	
	
	public NewsInfoRead() {
		// TODO Auto-generated constructor stub
	}
	
	public NewsInfoRead(String userId,String newsId,int flag) {
		// TODO Auto-generated constructor stub
		this.userId=userId;
		this.newsId=newsId;
		this.createTime=new Date();
		this.flag=flag;
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

	public String getNewsId() {
		return newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
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
