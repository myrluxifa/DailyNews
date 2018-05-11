package com.lvmq.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="t_advert_info")
public class AdvertInfo {
	
	@Id
	@GenericGenerator(name="system-uuid",strategy="uuid")
	@GeneratedValue(generator="system-uuid")
	private String id;
	
	private String title;
	
	private String url;
	
	private String readNum;
	
	private Date createTime;
	
	private String createUser;
	
	private int flag;
	
	@OneToMany(cascade= {CascadeType.ALL})
	@JoinColumn(name="advertId")
	private List<AdvertImg> advertImgs;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getReadNum() {
		return readNum;
	}

	public void setReadNum(String readNum) {
		this.readNum = readNum;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public List<AdvertImg> getAdvertImgs() {
		return advertImgs;
	}

	public void setAdvertImgs(List<AdvertImg> advertImgs) {
		this.advertImgs = advertImgs;
	}
	
	
	
}
