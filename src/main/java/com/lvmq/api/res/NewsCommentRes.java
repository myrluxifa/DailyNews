package com.lvmq.api.res;

import java.util.List;

import com.lvmq.model.NewsComment;

public class NewsCommentRes {
	private String id;
	
	private String headPortrait;
	
	private String name;
	
	private String like;
	
	private String time;
	
	private String comment;
	
	private String ilike;
	
	private List<NewsCommentLevel2Res> newsCommentLevel2Array;
	
	
	public NewsCommentRes(NewsComment newsComment,String headPortrait,String name) {
		// TODO Auto-generated constructor stub
		this.id=newsComment.getId();
		this.like=String.valueOf(newsComment.getLikeCnt());
		this.time=String.valueOf(newsComment.getCreateTime().getTime());
		this.comment=newsComment.getComment();
		this.headPortrait=headPortrait;
		this.name=name;
	}
	
	public NewsCommentRes(NewsComment newsComment,List<NewsCommentLevel2Res> newsCommentLevel2Array,String ilike) {
		// TODO Auto-generated constructor stub
		this.id=newsComment.getId();
		this.headPortrait=newsComment.getUserLogin().getHeadPortrait();
		this.name=newsComment.getUserLogin().getName();
		this.like=String.valueOf(newsComment.getLikeCnt());
		this.time=String.valueOf(newsComment.getCreateTime().getTime());
		this.comment=newsComment.getComment();
		this.newsCommentLevel2Array=newsCommentLevel2Array;
		this.ilike=ilike;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHeadPortrait() {
		return headPortrait;
	}

	public void setHeadPortrait(String headPortrait) {
		this.headPortrait = headPortrait;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLike() {
		return like;
	}

	public void setLike(String like) {
		this.like = like;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<NewsCommentLevel2Res> getNewsCommentLevel2Array() {
		return newsCommentLevel2Array;
	}

	public void setNewsCommentLevel2Array(List<NewsCommentLevel2Res> newsCommentLevel2Array) {
		this.newsCommentLevel2Array = newsCommentLevel2Array;
	}

	public String getIlike() {
		return ilike;
	}

	public void setIlike(String ilike) {
		this.ilike = ilike;
	}
	
	
	
	
}
