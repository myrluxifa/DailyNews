package com.lvmq.api.res;

import java.util.Optional;

import org.springframework.util.StringUtils;

import com.lvmq.base.Consts;
import com.lvmq.model.UserLogin;
import com.lvmq.util.NumberUtils;

public class LoginRes {
	private String user_id;

	private String phone;

	private String head_portrait;

	private String gold;

	private String my_invite_code;

	private String balance;

	private String earnings;

	private String invite_code;

	private String newer_mission;

	private boolean bindwx;

	private boolean oneyuan;
	
	private String name;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public LoginRes() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the oneyuan
	 */
	public boolean isOneyuan() {
		return oneyuan;
	}

	/**
	 * @param oneyuan
	 *            the oneyuan to set
	 */
	public void setOneyuan(boolean oneyuan) {
		this.oneyuan = oneyuan;
	}

	public LoginRes(UserLogin userLogin) {
		// TODO Auto-generated constructor stub
		this.user_id = userLogin.getId();
		this.phone = Optional.ofNullable(userLogin.getUserName()).map(user -> user).orElse("");
		this.head_portrait = userLogin.getHeadPortrait() == null ? "" : userLogin.getHeadPortrait();
		this.my_invite_code = userLogin.getMyInviteCode();
		this.gold = String.valueOf(userLogin.getGold());
		this.balance = String.valueOf(userLogin.getBalance());
		this.earnings = String.valueOf(userLogin.getEarnings());
		this.invite_code = userLogin.getInviteCode() == null ? "" : userLogin.getInviteCode();
		this.newer_mission = userLogin.getNewerMission() == null ? "" : userLogin.getNewerMission();
		this.bindwx = StringUtils.isEmpty(userLogin.getOpenid()) ? false : true;
	}

	public LoginRes(UserLogin userLogin, int cnt,String earnings) {
		this.user_id = userLogin.getId();
		this.phone = Optional.ofNullable(userLogin.getUserName()).map(user -> user).orElse("");
		this.head_portrait = userLogin.getHeadPortrait() == null ? "" : userLogin.getHeadPortrait();
		this.my_invite_code = userLogin.getMyInviteCode();
		this.gold = String.valueOf(userLogin.getGold());
		this.balance = String.valueOf(userLogin.getBalance());
//		this.earnings = String.valueOf(userLogin.getEarnings());
		this.earnings = earnings;
		this.invite_code = userLogin.getInviteCode() == null ? "" : userLogin.getInviteCode();
		this.newer_mission = userLogin.getNewerMission() == null ? "" : userLogin.getNewerMission();
		this.bindwx = StringUtils.isEmpty(userLogin.getOpenid()) ? false : true;
		this.name = Optional.ofNullable(userLogin.getName()).map(name -> name).orElse("");
		this.oneyuan = cnt > 0 ? true : false;
	}

	/**
	 * @return the bindwx
	 */
	public boolean isBindwx() {
		return bindwx;
	}

	/**
	 * @param bindwx
	 *            the bindwx to set
	 */
	public void setBindwx(boolean bindwx) {
		this.bindwx = bindwx;
	}

	/**
	 * @return the newer_mission
	 */
	public String getNewer_mission() {
		return newer_mission;
	}

	/**
	 * @param invite_code
	 *            the invite_code to set
	 */
	public void setInvite_code(String invite_code) {
		this.invite_code = invite_code;
	}

	/**
	 * @param newer_mission
	 *            the newer_mission to set
	 */
	public void setNewer_mission(String newer_mission) {
		this.newer_mission = newer_mission;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getHead_portrait() {
		return head_portrait;
	}

	public void setHead_portrait(String head_portrait) {
		this.head_portrait = head_portrait;
	}

	public String getGold() {
		return gold;
	}

	public void setGold(String gold) {
		this.gold = gold;
	}

	public String getMy_invite_code() {
		return my_invite_code;
	}

	public void setMy_invite_code(String my_invite_code) {
		this.my_invite_code = my_invite_code;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getEarnings() {
		return earnings;
	}

	public void setEarnings(String earnings) {
		this.earnings = earnings;
	}

	public String getInvite_code() {
		return invite_code;
	}

	public void setInviteCode(String invite_code) {
		this.invite_code = invite_code;
	}

}
