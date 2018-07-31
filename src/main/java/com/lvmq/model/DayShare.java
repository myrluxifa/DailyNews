package com.lvmq.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

/**
 * The persistent class for the t_day_share database table.
 * 
 */
@Entity
@Table(name = "t_day_share")
public class DayShare implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4472755317599970606L;

	private int cnt;

	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	private String id;

	private String mdate;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "create_time")
	private Date createTime;

	public DayShare(int cnt, String mdate, String userId) {
		super();
		this.cnt = cnt;
		this.mdate = mdate;
		this.userId = userId;
		this.createTime = new Date();
	}

	public DayShare() {
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getCnt() {
		return this.cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMdate() {
		return this.mdate;
	}

	public void setMdate(String mdate) {
		this.mdate = mdate;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}