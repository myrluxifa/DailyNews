package com.lvmq.model;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;


/**
 * The persistent class for the t_withdraw_log database table.
 * 
 */
@Entity
@Table(name="t_withdraw_log")
public class WithdrawLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5438808887059204695L;

	@Id
	@GenericGenerator(name="system-uuid",strategy="uuid")
	@GeneratedValue(generator="system-uuid")
	private String id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="auditing_time")
	private Date auditingTime;

	@Column(name="auditing_user")
	private String auditingUser;

	private String captcha;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	private String fee;

	private String remark;

	private String state;

	@Column(name="user_id")
	private String userId;

	public WithdrawLog(String captcha, Date createTime, String fee, String state, String userId) {
		super();
		this.captcha = captcha;
		this.createTime = createTime;
		this.fee = fee;
		this.state = state;
		this.userId = userId;
	}

	public WithdrawLog() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getAuditingTime() {
		return this.auditingTime;
	}

	public void setAuditingTime(Date auditingTime) {
		this.auditingTime = auditingTime;
	}

	public String getAuditingUser() {
		return this.auditingUser;
	}

	public void setAuditingUser(String auditingUser) {
		this.auditingUser = auditingUser;
	}

	public String getCaptcha() {
		return this.captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getFee() {
		return this.fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
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

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}