package com.lvmq.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_user_login")
public class UserLogin {
	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	private String id;

	private String userName;

	private String passwd;

	private String openid;

	private String headPortrait;

	private Long gold;

	private int flag;

	private String inviteCode;

	private String myInviteCode;

	private int inviteCount;

	private String balance;

	private long earnings;

	private String name;

	private String firstInvite;

	private String masterMaster;

	private int grandCnt;

	private Date createTime;

	public UserLogin() {
		// TODO Auto-generated constructor stub
	}

	public UserLogin(String userName, String passwd, String inviteCode) {
		// TODO Auto-generated constructor stub
		this.userName = userName;
		this.passwd = passwd;
		this.inviteCode = inviteCode;
	}

	public UserLogin(String userName, String passwd) {
		// TODO Auto-generated constructor stub
		this.userName = userName;
		this.passwd = passwd;
	}

	/**
	 * @return the openid
	 */
	public String getOpenid() {
		return openid;
	}

	/**
	 * @param openid
	 *            the openid to set
	 */
	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getHeadPortrait() {
		return headPortrait;
	}

	public void setHeadPortrait(String headPortrait) {
		this.headPortrait = headPortrait;
	}

	public Long getGold() {
		return gold;
	}

	public void setGold(Long gold) {
		this.gold = gold;
	}

	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

	public String getMyInviteCode() {
		return myInviteCode;
	}

	public void setMyInviteCode(String myInviteCode) {
		this.myInviteCode = myInviteCode;
	}

	public int getInviteCount() {
		return inviteCount;
	}

	public void setInviteCount(int inviteCount) {
		this.inviteCount = inviteCount;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public long getEarnings() {
		return earnings;
	}

	public void setEarnings(long earnings) {
		this.earnings = earnings;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstInvite() {
		return firstInvite;
	}

	public void setFirstInvite(String firstInvite) {
		this.firstInvite = firstInvite;
	}

	public String getMasterMaster() {
		return masterMaster;
	}

	public void setMasterMaster(String masterMaster) {
		this.masterMaster = masterMaster;
	}

	public int getGrandCnt() {
		return grandCnt;
	}

	public void setGrandCnt(int grandCnt) {
		this.grandCnt = grandCnt;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
