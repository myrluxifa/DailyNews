package com.lvmq.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="t_advert_img")
public class AdvertImg {
	
	@Id
	@GenericGenerator(name="system-uuid",strategy="uuid")
	@GeneratedValue(generator="system-uuid")
	private String id;
	
	private String advertId;
	
	private String img;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAdvertId() {
		return advertId;
	}

	public void setAdvertId(String advertId) {
		this.advertId = advertId;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	
}
