package com.lvmq.api.res;

import java.util.List;

import com.lvmq.model.VideosInfo;
import com.lvmq.util.ArrayUtil;

public class VideosRes {
	
	private String id;
	
	private String title;
	
	private String coverUrl;
	
	private String durationMin;
	
	private String viewCount;
	
	private String description;
	
	private String publishDate;
	
	private List<String> videoUrls;

	
	public VideosRes() {
		// TODO Auto-generated constructor stub
	}
	
	public VideosRes(VideosInfo videosInfo) {
		// TODO Auto-generated constructor stub
		this.id=videosInfo.getId();
		this.title=videosInfo.getTitle();
		this.coverUrl=videosInfo.getCoverUrl();
		this.durationMin=String.valueOf(videosInfo.getDurationMin());
		this.viewCount=String.valueOf(videosInfo.getViewCount());
		this.description=videosInfo.getDescription();
		this.publishDate=String.valueOf(videosInfo.getPublishDate().getTime());
		this.videoUrls=ArrayUtil.stringToList(videosInfo.getVideoUrls());
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCoverUrl() {
		return coverUrl;
	}

	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}

	public String getDurationMin() {
		return durationMin;
	}

	public void setDurationMin(String durationMin) {
		this.durationMin = durationMin;
	}

	public String getViewCount() {
		return viewCount;
	}

	public void setViewCount(String viewCount) {
		this.viewCount = viewCount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getVideoUrls() {
		return videoUrls;
	}

	public void setVideoUrls(List<String> videoUrls) {
		this.videoUrls = videoUrls;
	}

	
	
	
}
