package com.lvmq.model;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;


/**
 * The persistent class for the t_sign_log database table.
 * 
 */
@Entity
@Table(name="t_sign_log")
public class SignLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2964860985654759807L;

	private int countinuous;

	@Column(name="create_date")
	private String createDate;

	@Id
	@GenericGenerator(name="system-uuid",strategy="uuid")
	@GeneratedValue(generator="system-uuid")
	private String id;

	@Column(name="user_id")
	private String userId;

	public SignLog() {
	}

	public int getCountinuous() {
		return this.countinuous;
	}

	public void setCountinuous(int countinuous) {
		this.countinuous = countinuous;
	}

	public String getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}