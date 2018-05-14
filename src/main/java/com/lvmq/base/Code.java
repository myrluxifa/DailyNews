package com.lvmq.base;

public class Code {
	public static final String SUCCESS="SUCCESS";
	
	public static final String FAIL="FAIL";
	
	public static final String SUCCESS_CODE="0000";
	
	public static final String UNKOWN_CODE="0001";
	
	//已存在
	public static final String USER_ALREADY_EXISTS="0003";
	
	//用户不存在
	public static final String USER_UNFINDABLE="0002";
	
	//验证码不存在
	public static final String MESSAGE_CODE_UNFINDABLE="0004";
	
	//验证码过期
	public static final String MESSAGE_CODE_PAST_DUE="0005";
	
	//验证码过期
	public static final String MESSAGE_CODE_MISTAKE="0006";
	
	public static class SHARE {
		/**每天最多分享次数**/
		public static int MAX_TIMES = 5;
		/**分享间隔时间 秒**/
		public static long INTERVAL_TIME = 3600;
		/**分享奖励金币数**/
		public static int REWARD_NUMBER = 5;
		
		public static class MORE_THAN_LIMITED_TIMES {
			public static final String code = "-1000";
			public static final String msg = "每天只能分享5次";
		}
		
		public static class LESS_THAN_LIMITED_INTERVAL {
			public static final String code = "-1001";
			public static final String msg = "每隔" + INTERVAL_TIME/3600 + "小时才能分享一次";
		}
	}

}
