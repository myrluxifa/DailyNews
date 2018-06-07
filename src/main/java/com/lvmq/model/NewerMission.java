package com.lvmq.model;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the t_newer_mission database table.
 * 
 */
@Entity
@Table(name="t_newer_mission")
public class NewerMission implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8845965875335841043L;

	@Id
	@GenericGenerator(name="system-uuid",strategy="uuid")
	@GeneratedValue(generator="system-uuid")
	private String id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(name = "mread")
	private int read;

	private String remark;

	@Column(name = "msearch")
	private int search;

	@Column(name = "mshare")
	private int share;

	@Column(name = "msign")
	private int sign;

	@Column(name="update_time")
	private Timestamp updateTime;

	@Column(name="user_id")
	private String userId;

	public NewerMission() {
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getRead() {
		return this.read;
	}

	public void setRead(int read) {
		this.read = read;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getSearch() {
		return this.search;
	}

	public void setSearch(int search) {
		this.search = search;
	}

	public int getShare() {
		return this.share;
	}

	public void setShare(int share) {
		this.share = share;
	}

	public int getSign() {
		return this.sign;
	}

	public void setSign(int sign) {
		this.sign = sign;
	}

	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}