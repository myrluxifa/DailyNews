package com.lvmq.model;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;


/**
 * The persistent class for the t_day_mission database table.
 * 
 */
@Entity
@Table(name="t_day_mission")
public class DayMission implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -617846242229849841L;

	@Id
	@GenericGenerator(name="system-uuid",strategy="uuid")
	@GeneratedValue(generator="system-uuid")
	private String id;

	private String mdate;

	private String param;

	@Column(name="user_id")
	private String userId;

	public DayMission() {
	}

	public DayMission(String userId, String mdate) {
		this.userId = userId;
		this.mdate = mdate;
		this.param = "0|0|0|0|0";
	}

	public DayMission(String userId, String mdate, String param) {
		this.userId = userId;
		this.mdate = mdate;
		this.param = param;
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

	public String getParam() {
		return this.param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}