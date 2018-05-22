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
	
	
}
