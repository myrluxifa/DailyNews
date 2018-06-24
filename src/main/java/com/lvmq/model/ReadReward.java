package com.lvmq.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="t_sys_read_rewards")
public class ReadReward {
	@Id
	@GenericGenerator(name="system-uuid",strategy="uuid")
	@GeneratedValue(generator="system-uuid")
	private String id;
	
	private int dailyCnt;

	private int gold;

	private int hour;
	
	private int horCnt;
	
	
	private String explain;
	
	private String horMoney;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getDailyCnt() {
		return dailyCnt;
	}

	public void setDailyCnt(int dailyCnt) {
		this.dailyCnt = dailyCnt;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getHorCnt() {
		return horCnt;
	}

	public void setHorCnt(int horCnt) {
		this.horCnt = horCnt;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public String getHorMoney() {
		return horMoney;
	}

	public void setHorMoney(String horMoney) {
		this.horMoney = horMoney;
	}
	
	
}
