package com.lvmq.model;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the t_gold_log database table.
 * 
 */
@Entity
@Table(name="t_gold_log")
public class GoldLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4685595757727804409L;

	@Id
	@GenericGenerator(name="system-uuid",strategy="uuid")
	@GeneratedValue(generator="system-uuid")
	private String id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="create_user")
	private String createUser;

	@Column(name="new_num")
	private long newNum;

	private long num;

	@Column(name="old_num")
	private long oldNum;

	private String remark;

	private String type;

	@Column(name="update_time")
	private Timestamp updateTime;

	@Column(name="user_id")
	private String userId;

	public GoldLog() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public long getNewNum() {
		return this.newNum;
	}

	public void setNewNum(long newNum) {
		this.newNum = newNum;
	}

	public long getNum() {
		return this.num;
	}

	public void setNum(long num) {
		this.num = num;
	}

	public long getOldNum() {
		return this.oldNum;
	}

	public void setOldNum(long oldNum) {
		this.oldNum = oldNum;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
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