package com.lvmq.api.res;

import java.util.List;

public class VideosArrayRes {
	private List<VideosRes> videos;

	
	public VideosArrayRes() {
		// TODO Auto-generated constructor stub
	}
	
	public VideosArrayRes(List<VideosRes> videos ) {
		// TODO Auto-generated constructor stub
		this.videos=videos;
	}
	
	public List<VideosRes> getVideos() {
		return videos;
	}

	public void setVideos(List<VideosRes> videos) {
		this.videos = videos;
	}
	
	
}
