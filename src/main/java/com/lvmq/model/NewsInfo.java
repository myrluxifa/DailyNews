package com.lvmq.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.lvmq.idata.res.ToutiaoDataResponseDto;
import com.lvmq.idata.res.ToutiaoResponseDto;
import com.lvmq.util.ArrayUtil;

@Entity
@Table(name="t_news_info")
public class NewsInfo {
	
	@Id
	@GenericGenerator(name="system-uuid",strategy="uuid")
	@GeneratedValue(generator="system-uuid")
	private String id;
	
	private Date publishDate;
	
	private String idataId;
	
	private String posterScreenName;
	
	private String url;
	
	private String title;
	
	private String posterId;
	
	private Integer viewCount;
	
	private String content;
	
	private String imgsUrl;
	
	private String catId;
	
	private int flag;
	
	public String getCatId() {
		return catId;
	}

	public void setCatId(String catId) {
		this.catId = catId;
	}

	public NewsInfo() {
		// TODO Auto-generated constructor stub
	}
	
	public NewsInfo(ToutiaoDataResponseDto dto,String catId) {
		// TODO Auto-generated constructor stub
		this.publishDate=new Date(Long.valueOf(dto.getPublishDate())*1000);
		this.idataId=dto.getId();
		this.posterScreenName=dto.getPosterScreenName();
		this.url=dto.getUrl();
		this.title=dto.getTitle();
		this.posterId=dto.getPosterId();
		this.viewCount=0;
		this.content=dto.getContent();
		this.imgsUrl=ArrayUtil.arrayToString(dto.getImageUrls());
		this.catId=catId;
		this.flag=0;
	}
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public String getIdataId() {
		return idataId;
	}

	public void setIdataId(String idataId) {
		this.idataId = idataId;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPosterId() {
		return posterId;
	}

	public void setPosterId(String posterId) {
		this.posterId = posterId;
	}

	

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	public String getImgsUrl() {
		return imgsUrl;
	}

	public void setImgsUrl(String imgsUrl) {
		this.imgsUrl = imgsUrl;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	
}
