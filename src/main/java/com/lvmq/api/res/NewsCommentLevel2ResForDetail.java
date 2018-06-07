package com.lvmq.api.res;

public class NewsCommentLevel2ResForDetail {
private String name;
	
	private String userName;
	
	private String comment;
	
	private String headPortrait;
	
	private String time;
	
	public NewsCommentLevel2ResForDetail() {
		// TODO Auto-generated constructor stub
	}
	
	public NewsCommentLevel2ResForDetail(String name,String comment,String userName,String headPortrait,String time) {
		// TODO Auto-generated constructor stub
		this.name=name;
		this.comment=comment;
		this.userName=userName;
		this.headPortrait=headPortrait;
		this.time=time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getHeadPortrait() {
		return headPortrait;
	}

	public void setHeadPortrait(String headPortrait) {
		this.headPortrait = headPortrait;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
}
