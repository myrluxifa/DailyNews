package com.lvmq.model;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.math.BigInteger;


/**
 * The persistent class for the t_user_read_message database table.
 * 
 */
@Entity
@Table(name="t_user_read_message")
public class UserReadMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	private String id;

	@Column(name="create_time")
	private BigInteger createTime;

	@Column(name="message_id")
	private String messageId;

	@Column(name="user_id")
	private String userId;

	public UserReadMessage() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigInteger getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(BigInteger createTime) {
		this.createTime = createTime;
	}

	public String getMessageId() {
		return this.messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}