package com.lvmq.base;

/**
 * 静态参数
 * 
 * @author Easy
 *
 */
public class Consts {

	public static class GoldLog {
		public static final class Type{
			/**分享**/
			public static final String SHARE = "100";
			/**签到**/
			public static final String SIGN = "101";
			/**阅读奖励**/
			public static final String READ = "102";
			
			/**注册奖励**/
			public static final String REGISTER = "105";
		}
		
		/**分享奖励个数**/
		public static final int SHARE_REWARD_NUMBER = 5;
		
		public static final String getTypeName(String id) {
			switch(id) {
			case Type.SHARE:
				return "分享奖励";
			case Type.SIGN: 
				return "签到奖励";
			case Type.READ: 
				return "阅读奖励";
			case Type.REGISTER: 
				return "注册奖励";
				default:
					return "神秘奖励";
			}
		}
	}
	
	
	public static class BalanceLog{
		public static final class Type{
			public static final String FIRST_INVITE="106";
		}
		
	}
	
	public static class LikeLog{
		public static final class Type{
			//评论点赞
			public static final String COMMENT="COMMENT";
		}
	}
	
	public static class AccessLog {
		public static final class Type{
			public static final String WITHDRAW_WEIXIN = "WITHDRAW_WEIXIN";
		}
		
		public static final class State{
			/** 待审核 **/
			public static final String WAITTING_AUTH = "0";
			/** 审核通过 **/
			public static final String PASSED = "1";
			/** 审核不通过 **/
			public static final String REFUSED = "2";
		}
		
		public static final String getTypeName(String type) {
			switch(type) {
			case Type.WITHDRAW_WEIXIN: return "微信提现";
			default: return "神秘提现";
			}
		}
		
		public static final String getStateName(String state) {
			switch(state) {
			case State.WAITTING_AUTH: return "待审核";
			case State.PASSED: return "已提现";
			case State.REFUSED: return "审核不通过";
			default: return "神秘提现";
			}
		}
	}
	
	public static final class Sign{
		public static final String COIN = "4,6,10,15,20,25,30";
	}
}
