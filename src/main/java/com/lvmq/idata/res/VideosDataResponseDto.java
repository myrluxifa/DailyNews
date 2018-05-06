package com.lvmq.idata.res;

public class VideosDataResponseDto {
	//总的分类路径
	private String catPathKey;

	//视频的踩数
	private String dislikeCount;

	//封面
	private String coverUrl;
	
	//视频的点赞数
	private String likeCount;
	
	//评论数
	private int commentCount;
	
	//分段视频
	private String partList;
	
	//视频ID
	private String id;
	
	//标题
	private String title;
	
	//发布时间
	private String publishDateStr;
	
	//媒体类型: 1=影视，2=普通
	private String mediaType;
	
	//是否付费
	private boolean isFree;
	
	//收藏数
	private String favoriteCount;
	
	//发布者ID
	private String posterId;
	
	//视频描述
	private String description;
	
	//1级分类名称
	private String catName1;
	
	//标签
	private String[] tags;
	
	//发布时间:时间戳格式
	private String publishDate;
	
	//弹幕数
	private String danmakuCount;
	
	//观看数
	private String viewCount;
	
	//发布者昵称
	private String posterScreenName;
	
	//本对象的最通用url
	private String url;
	
	//是否仅限会员
	private String memberOnly;
	
	//1级分类ID
	private String catId1;
	
	
	 //时长，单位分钟
	private int durationMin;
	
	
	private String[] videoUrls; 


	public String getCatPathKey() {
		return catPathKey;
	}


	public void setCatPathKey(String catPathKey) {
		this.catPathKey = catPathKey;
	}


	public String getDislikeCount() {
		return dislikeCount;
	}


	public void setDislikeCount(String dislikeCount) {
		this.dislikeCount = dislikeCount;
	}


	public String getCoverUrl() {
		return coverUrl;
	}


	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}


	public String getLikeCount() {
		return likeCount;
	}


	public void setLikeCount(String likeCount) {
		this.likeCount = likeCount;
	}


	public int getCommentCount() {
		return commentCount;
	}


	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}


	public String getPartList() {
		return partList;
	}


	public void setPartList(String partList) {
		this.partList = partList;
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


	public String getPublishDateStr() {
		return publishDateStr;
	}


	public void setPublishDateStr(String publishDateStr) {
		this.publishDateStr = publishDateStr;
	}


	public String getMediaType() {
		return mediaType;
	}


	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}


	public boolean isFree() {
		return isFree;
	}


	public void setFree(boolean isFree) {
		this.isFree = isFree;
	}


	public String getFavoriteCount() {
		return favoriteCount;
	}


	public void setFavoriteCount(String favoriteCount) {
		this.favoriteCount = favoriteCount;
	}


	public String getPosterId() {
		return posterId;
	}


	public void setPosterId(String posterId) {
		this.posterId = posterId;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getCatName1() {
		return catName1;
	}


	public void setCatName1(String catName1) {
		this.catName1 = catName1;
	}


	


	public String[] getTags() {
		return tags;
	}


	public void setTags(String[] tags) {
		this.tags = tags;
	}


	public String getPublishDate() {
		return publishDate;
	}


	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}


	public String getDanmakuCount() {
		return danmakuCount;
	}


	public void setDanmakuCount(String danmakuCount) {
		this.danmakuCount = danmakuCount;
	}


	public String getViewCount() {
		return viewCount;
	}


	public void setViewCount(String viewCount) {
		this.viewCount = viewCount;
	}


	public String getPosterScreenName() {
		return posterScreenName;
	}


	public void setPosterScreenName(String posterScreenName) {
		this.posterScreenName = posterScreenName;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getMemberOnly() {
		return memberOnly;
	}


	public void setMemberOnly(String memberOnly) {
		this.memberOnly = memberOnly;
	}


	public String getCatId1() {
		return catId1;
	}


	public void setCatId1(String catId1) {
		this.catId1 = catId1;
	}


	public int getDurationMin() {
		return durationMin;
	}


	public void setDurationMin(int durationMin) {
		this.durationMin = durationMin;
	}


	public String[] getVideoUrls() {
		return videoUrls;
	}


	public void setVideoUrls(String[] videoUrls) {
		this.videoUrls = videoUrls;
	}
	
	
	
	
	
}
