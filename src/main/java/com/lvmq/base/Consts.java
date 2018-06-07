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
			
			/**师傅获得阅读奖励**/
			public static final String MASTER_READ_REWARDS="109";
			
			//师爷在前徒孙注册前5天获得奖励类型
			public static final String FIVE_MASTER_MASTER_READ_REWARDS="108";
			//师爷在前徒孙注册前5天后获得奖励类型
			public static final String MASTER_MASTER_READ_REWARDS="110";
			
			public static final String LOGIN="111";
			
			//召回徒弟奖励
			public static final String RECALL="112";
			
			//被召回奖励
			public static final String RECALL_BACK="113";
		}
		
		/**分享奖励个数**/
		public static final int REWARD_SHARE_NUMBER = 5;
		/** 每日登录奖励 **/
		public static final int REWARD_LOGIN = 100;
		/** 2.【推荐】展示红包次数：展示用户可用阅读领取奖励总次数，基本次数25次+每小时提供次数3次，后台都可设置 **/
		public static final int REWARD_SHOW_REDPACKAGE = 1;

		
		
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
			case Type.MASTER_READ_REWARDS: 
				return "师傅获得阅读奖励";
			case Type.FIVE_MASTER_MASTER_READ_REWARDS: 
				return "师爷在前徒孙注册前5天获得奖励类型";
			case Type.MASTER_MASTER_READ_REWARDS: 
				return "师爷在前徒孙注册前5天后获得奖励类型";
			case Type.LOGIN:
				return "登录奖励";
			case Type.RECALL:
				return "召回奖励";
			case Type.RECALL_BACK:
				return "被召回奖励";
				default:
					return "神秘奖励";
			}
		}
	}
	
	
	public static class BalanceLog{
		public static final class Type{
			public static final String FIRST_INVITE="106";
			
			//徒弟前8天师傅奖励
			public static final String EIGHT_DAY_REWARDS="107";
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
	
	public static final long GOLD_RATIO=1000;

	public static class NewerMission{
		
		public static final class Type {
			public static final String READ = "read";
			public static final String SHARE = "share";
			public static final String SIGN = "sign";
			public static final String SEARCH= "search";
		}
	}
}
