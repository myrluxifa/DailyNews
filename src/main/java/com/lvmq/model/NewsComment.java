package com.lvmq.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="t_news_comment")
public class NewsComment {
	@Id
	@GenericGenerator(name="system-uuid",strategy="uuid")
	@GeneratedValue(generator="system-uuid")
	private String id;
	
	private String newsId;
	
	private String comment;
	
	
	private int flag;
	
	private int level;
	
	private String parentId;
	
	private String createUser;
	
	private Date createTime;

	
	private Long likeCnt;
	
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="createUser",insertable = false, updatable = false, nullable=true)
	private UserLogin userLogin;
	
	
	public NewsComment() {
		// TODO Auto-generated constructor stub
	}
	
	public NewsComment(String newsId,String loginId,String parentId,String level,String comment) {
		// TODO Auto-generated constructor stub
		
		this.newsId=newsId;
		this.createUser=loginId;
		this.comment=comment;
		this.flag=0;
		this.level=Integer.valueOf(level);
		this.parentId=parentId;
		this.createTime=new Date();
		this.likeCnt=0l;
	}
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNewsId() {
		return newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	

	public UserLogin getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(UserLogin userLogin) {
		this.userLogin = userLogin;
	}

	public Long getLikeCnt() {
		return likeCnt;
	}

	public void setLikeCnt(Long likeCnt) {
		this.likeCnt = likeCnt;
	}

	
	
	
}
