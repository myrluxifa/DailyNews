package com.lvmq.api.res;

public class NewsCommentLevel2Res {
	private String name;
	
	private String comment;
	
	public NewsCommentLevel2Res() {
		// TODO Auto-generated constructor stub
	}
	
	public NewsCommentLevel2Res(String name,String comment) {
		// TODO Auto-generated constructor stub
		this.name=name;
		this.comment=comment;
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
	
	
}
