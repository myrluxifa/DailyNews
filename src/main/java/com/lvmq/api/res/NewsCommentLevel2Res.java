package com.lvmq.api.res;

public class NewsCommentLevel2Res {
	private String name;
	
	private String userName;
	
	private String comment;
	
	public NewsCommentLevel2Res() {
		// TODO Auto-generated constructor stub
	}
	
	public NewsCommentLevel2Res(String name,String comment,String userName) {
		// TODO Auto-generated constructor stub
		this.name=name;
		this.comment=comment;
		this.userName=userName;
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
	
	
}
