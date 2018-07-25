package com.lvmq.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.lvmq.idata.res.VideosDataResponseDto;
import com.lvmq.util.ArrayUtil;
import com.lvmq.util.Util;


@Entity
@Table(name="t_videos_info")
public class VideosInfo {
	
	@Id
	@GenericGenerator(name="system-uuid",strategy="uuid")
	@GeneratedValue(generator="system-uuid")
	private String id;
	
	private String title;
	
	private String idataId;
	
	private String coverUrl;
	
	private String posterId;
	
	private String catId;
	
	private int durationMin;
	
	private int viewCount;
	
	private String description;

	private int commentCount;
	
	private Date publishDate;
	
	private int flag;
	
	private String videoUrls;
	
	private String url;
	
	
	public VideosInfo() {
		// TODO Auto-generated constructor stub
	}
	
	public VideosInfo(VideosDataResponseDto videosDataResponseDto) {
		// TODO Auto-generated constructor stub
		this.title=Util.decodeUnicode(videosDataResponseDto.getTitle());
		this.idataId=videosDataResponseDto.getId();
		this.coverUrl=videosDataResponseDto.getCoverUrl();
		this.posterId=videosDataResponseDto.getPosterId();
		this.catId=videosDataResponseDto.getCatId1();
		this.durationMin=videosDataResponseDto.getDurationMin();
		this.viewCount=Integer.valueOf(videosDataResponseDto.getViewCount()==null?"0":videosDataResponseDto.getViewCount());
		this.description=videosDataResponseDto.getDescription();
		this.commentCount=videosDataResponseDto.getCommentCount();
		this.publishDate=new Date();
		this.url=videosDataResponseDto.getUrl();
		this.flag=0;
		this.videoUrls=videosDataResponseDto.getVideoUrls()==null?"":ArrayUtil.arrayToString(videosDataResponseDto.getVideoUrls());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIdataId() {
		return idataId;
	}

	public void setIdataId(String idataId) {
		this.idataId = idataId;
	}

	public String getCoverUrl() {
		return coverUrl;
	}

	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}

	public String getPosterId() {
		return posterId;
	}

	public void setPosterId(String posterId) {
		this.posterId = posterId;
	}

	public String getCatId() {
		return catId;
	}

	public void setCatId(String catId) {
		this.catId = catId;
	}

	public int getDurationMin() {
		return durationMin;
	}

	public void setDurationMin(int durationMin) {
		this.durationMin = durationMin;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getVideoUrls() {
		return videoUrls;
	}

	public void setVideoUrls(String videoUrls) {
		this.videoUrls = videoUrls;
	}
	
	
	
}
