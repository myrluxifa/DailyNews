package com.lvmq.base;

/**
 * 静态参数
 * 
 * @author Easy
 *
 */
public class Consts {

	public static class GoldLog {
		public static class Type{
			/**分享**/
			public static final String SHARE = "100";
		}
		
		/**分享奖励个数**/
		public static final int SHARE_REWARD_NUMBER = 5;
		
		public static String getTypeName(String id) {
			switch(id) {
			case Type.SHARE:
				return "分享奖励";
				default:
					return "神秘奖励";
			}
		}
	}
}
