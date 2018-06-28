package com.lvmq.api.res;

public class WithdrawPageRes {

	private String title;
	private String time;
	private String state;

	public WithdrawPageRes() {
		super();
	}

	public WithdrawPageRes(String title, String time, String state) {
		super();
		this.title = title;
		this.time = time;
		this.state = state;
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
