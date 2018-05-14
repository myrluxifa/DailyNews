package com.lvmq.model;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;


/**
 * The persistent class for the t_access_log database table.
 * 
 */
@Entity
@Table(name="t_access_log")
public class AccessLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4234643903459300561L;

	@Id
	@GenericGenerator(name="system-uuid",strategy="uuid")
	@GeneratedValue(generator="system-uuid")
	private String id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="auth_time")
	private Date authTime;

	@Column(name="auth_user")
	private String authUser;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="create_user")
	private String createUser;

	private double fee;

	@Column(name="new_fee")
	private double newFee;

	@Column(name="old_fee")
	private double oldFee;

	private String remark;

	private String state;

	private String type;

	@Column(name="user_id")
	private String userId;

	public AccessLog() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getAuthTime() {
		return this.authTime;
	}

	public void setAuthTime(Date authTime) {
		this.authTime = authTime;
	}

	public String getAuthUser() {
		return this.authUser;
	}

	public void setAuthUser(String authUser) {
		this.authUser = authUser;
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

	public double getFee() {
		return this.fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	public double getNewFee() {
		return this.newFee;
	}

	public void setNewFee(double newFee) {
		this.newFee = newFee;
	}

	public double getOldFee() {
		return this.oldFee;
	}

	public void setOldFee(double oldFee) {
		this.oldFee = oldFee;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}