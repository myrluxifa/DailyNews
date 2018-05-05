package com.lvmq.idata.res;

public class ToutiaoDataResponseDto {
		//发布者ID
		private String posterId;
		//新闻的踩数
		private String dislikeCount;
		//发布日期时间戳
		private String publishDate;
		//新闻的点赞数
		private String likeCount;
		//评论数
		private String commentCount;
		//图像url合集
		private String[] imageUrls;
		//新闻编号
		private String id;
		//观看数
		private String viewCount;
		//发布者名称
		private String posterScreenName;
		//标题
		private String title;
		//新闻连接
		private String url;
		//发布时间（UTC时间）
		private String publishDateStr;
		//新闻内容
		private String content;
		public String getPosterId() {
			return posterId;
		}
		public void setPosterId(String posterId) {
			this.posterId = posterId;
		}
		public String getDislikeCount() {
			return dislikeCount;
		}
		public void setDislikeCount(String dislikeCount) {
			this.dislikeCount = dislikeCount;
		}
		public String getPublishDate() {
			return publishDate;
		}
		public void setPublishDate(String publishDate) {
			this.publishDate = publishDate;
		}
		public String getLikeCount() {
			return likeCount;
		}
		public void setLikeCount(String likeCount) {
			this.likeCount = likeCount;
		}
		public String getCommentCount() {
			return commentCount;
		}
		public void setCommentCount(String commentCount) {
			this.commentCount = commentCount;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
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
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getPublishDateStr() {
			return publishDateStr;
		}
		public void setPublishDateStr(String publishDateStr) {
			this.publishDateStr = publishDateStr;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String[] getImageUrls() {
			return imageUrls;
		}
		public void setImageUrls(String[] imageUrls) {
			this.imageUrls = imageUrls;
		}
		
		
}
