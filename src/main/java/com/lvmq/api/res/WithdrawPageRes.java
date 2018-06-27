package com.lvmq.api.res;

public class WithdrawPageRes {

	private String fee;
	private String time;
	private String state;

	public WithdrawPageRes() {
		super();
	}

	public WithdrawPageRes(String fee, String time, String state) {
		super();
		this.fee = fee;
		this.time = time;
		this.state = state;
	}

	/**
	 * @return the fee
	 */
	public String getFee() {
		return fee;
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
	 * @param fee
	 *            the fee to set
	 */
	public void setFee(String fee) {
		this.fee = fee;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

}
