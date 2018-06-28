package com.lvmq.api.res;

public class WithdrawPageRes {

	private String title;
	private String time;
	private String state;
	private String fee;

	public WithdrawPageRes() {
		super();
	}

	public WithdrawPageRes(String title, String time, String state, String fee) {
		super();
		this.title = title;
		this.time = time;
		this.state = state;
		this.fee = fee;
	}

	/**
	 * @return the fee
	 */
	public String getFee() {
		return fee;
	}

	/**
	 * @param fee the fee to set
	 */
	public void setFee(String fee) {
		this.fee = fee;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

}
