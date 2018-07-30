package com.lvmq.api;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lvmq.api.res.LoginRes;
import com.lvmq.api.res.base.ResponseBean;
import com.lvmq.base.Code;
import com.lvmq.base.Consts;
import com.lvmq.model.BalanceLog;
import com.lvmq.model.DayMission;
import com.lvmq.model.GoldLog;
import com.lvmq.model.MessageCode;
import com.lvmq.model.UserLogin;
import com.lvmq.repository.BalanceLogRepository;
import com.lvmq.repository.GoldLogRepository;
import com.lvmq.repository.GoldRewardsRepository;
import com.lvmq.repository.UserLoginRepository;
import com.lvmq.service.DayMissionService;
import com.lvmq.service.UserLoginService;
import com.lvmq.util.MD5;
import com.lvmq.util.SMS;
import com.lvmq.util.TimeUtil;
import com.lvmq.util.Util;
import com.lvmq.weixin.Weixin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"微信"})
@RestController
@RequestMapping("api/wx")
public class WeixinAPI {
	
	@Autowired
	private UserLoginRepository userRepository;
	
	@Autowired
	private UserLoginService userLoginService;
	
	@Autowired
	private GoldLogRepository goldLogRepository;
	
	@Autowired
	private GoldRewardsRepository goldRewardsRepository;
	
	@Autowired
	private BalanceLogRepository balanceLogRepository;
	
	@Autowired
	private DayMissionService dayMissionService;
	
	@ApiOperation(value = "发送验证码")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "phone", value = "电话", required = true, dataType = "String")
	})
	@RequestMapping(value="/sendCaptcha",method=RequestMethod.POST)
	public ResponseBean sendRegisterCode(String phone) {
		try {
			boolean tag = false;
			if(userLoginService.countByUserName(phone)>0) {
				tag = true;
			}
			
			String code=Util.getRandom6();
			SMS.singleSendSms(phone, code, Consts.SmsConfig.TEMPLATECODE);
			UserAPI.messageCodeMap.put(phone, new MessageCode(code));
			return new ResponseBean(Code.SUCCESS,Code.SUCCESS_CODE,"成功", tag);
		}catch(Exception e) {
			return new ResponseBean(Code.FAIL,Code.UNKOWN_CODE,"失败");
		}
	}
	
	@ApiOperation(value = "绑定手机号", notes = "", httpMethod = "POST")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "userId", value = "用户ID", required = true, dataType = "String"),
		@ApiImplicitParam(paramType = "query", name = "phone", value = "手机号", required = true, dataType = "String"),
		@ApiImplicitParam(paramType = "query", name = "password", value = "密码", required = true, dataType = "String"),
		@ApiImplicitParam(paramType = "query", name = "captcha", value = "验证码", required = true, dataType = "String"),
		@ApiImplicitParam(paramType = "query", name = "inviteCode", value = "邀请码", required = false, dataType = "String"),
		@ApiImplicitParam(paramType = "query", name = "openid", value = "微信返回值透传", required = true, dataType = "String"),
		@ApiImplicitParam(paramType = "query", name = "nickname", value = "微信返回值透传", required = true, dataType = "String"),
		@ApiImplicitParam(paramType = "query", name = "sex", value = "微信返回值透传", required = true, dataType = "String"),
		@ApiImplicitParam(paramType = "query", name = "language", value = "微信返回值透传", required = true, dataType = "String"),
		@ApiImplicitParam(paramType = "query", name = "city", value = "微信返回值透传", required = true, dataType = "String"),
		@ApiImplicitParam(paramType = "query", name = "province", value = "微信返回值透传", required = true, dataType = "String"),
		@ApiImplicitParam(paramType = "query", name = "country", value = "微信返回值透传", required = true, dataType = "String"),
		@ApiImplicitParam(paramType = "query", name = "headimgurl", value = "微信返回值透传", required = true, dataType = "String"),
		@ApiImplicitParam(paramType = "query", name = "unionid", value = "微信返回值透传", required = true, dataType = "String")
			})
	@PostMapping("bindphone")
	public ResponseBean<Object> bindphone(String userId, String phone, String password, String captcha, String inviteCode, String openid, String nickname, String sex, String language, String city, String province, String country, String headimgurl, String unionid) throws UnsupportedEncodingException {
		
		if(!StringUtils.isEmpty(inviteCode)) {
			UserLogin uin = userRepository.findByMyInviteCodeOrUserName(inviteCode, inviteCode);
			
			if(null == uin) {
				return new ResponseBean(Code.FAIL,Code.FAIL,"失败","邀请码不存在");
			}
		}		
		
		if(!UserAPI.messageCodeMap.containsKey(phone)) {
			return new ResponseBean(Code.FAIL,Code.MESSAGE_CODE_UNFINDABLE,"验证码不存在");
		}
		
		MessageCode  m=UserAPI.messageCodeMap.get(phone);
		//如果验证码过期
		if(TimeUtil.ifPastDue(m.getTime())) {
			return new ResponseBean(Code.FAIL,Code.MESSAGE_CODE_PAST_DUE,"验证码过期");
		}
		
		if(!m.getCode().equals(captcha)) {
			return new ResponseBean(Code.FAIL,Code.MESSAGE_CODE_MISTAKE,"验证码错误");
		}
		
		//验证成功 移除当前验证码
		UserAPI.messageCodeMap.remove(phone);
		
		UserLogin userbyphone = userRepository.findByUserName(phone);
		
		
		UserLogin user;
		
		if(null != userbyphone) {
			userbyphone.setName(nickname);
			userbyphone.setHeadPortrait(headimgurl);
			userbyphone.setOpenid(openid);
			
			userRepository.deleteById(userId);
			
			user = userRepository.save(userbyphone);
		} else {
			Optional<UserLogin> u = userRepository.findById(userId);
			
			UserLogin ul = u.get();
			ul.setUserName(phone);
			ul.setPasswd(MD5.getMD5(password));
			
			user = userRepository.save(ul);
		}
		
		
		
		if(!Util.isBlank(inviteCode) && Util.isBlank(user.getInviteCode())) {
			boolean firstInvite = false;
			//给邀请人添加邀请数量
			UserLogin inviteUser=userRepository.findByMyInviteCode(inviteCode);
			if(inviteUser!=null) {
				if(inviteUser.getFirstInvite().equals("0")) {
					String money=goldRewardsRepository.findByType(Consts.BalanceLog.Type.FIRST_INVITE).getMoney();
					
					inviteUser.setBalance(String.valueOf(Double.valueOf(inviteUser.getBalance())+Double.valueOf(money)));
					//首次召徒获得现金奖励
					inviteUser.setFirstInvite("1");
					balanceLogRepository.save(new BalanceLog(inviteUser.getId(),money,inviteUser.getBalance(),String.valueOf(Double.valueOf(inviteUser.getBalance())+Double.valueOf(money)),Consts.BalanceLog.Type.FIRST_INVITE));
					firstInvite = true;
				}
				inviteUser.setInviteCount(inviteUser.getInviteCount()+1);
				if(!Util.isBlank(inviteUser.getInviteCode())) {
					UserLogin masterMasetUser=userRepository.findByMyInviteCode(inviteUser.getInviteCode());
					if(masterMasetUser.getGrandCnt()<2) {
						masterMasetUser.setGrandCnt(masterMasetUser.getGrandCnt()+1);
						user.setMasterMaster(masterMasetUser.getId());
						userRepository.save(user);
					}
				}
				userRepository.save(inviteUser);
				
				if(!firstInvite) {
					//日常任务 邀请好友
					DayMission dm = dayMissionService.updateDayMission(inviteUser.getId(), Consts.DayMission.Type.INVITE);					
				}
				
				String invite_gold=goldRewardsRepository.findByType(Consts.GoldLog.Type.SET_INVITE).getGold();
				int updateGold=(int) (Integer.valueOf(invite_gold)+user.getGold());
				
				long userGold=user.getGold();
				
				user.setGold(Long.valueOf(updateGold));
				userRepository.save(user);
				
				GoldLog goldLogInvite=new GoldLog();
				goldLogInvite.setUserId(user.getId());
				goldLogInvite.setType(Consts.GoldLog.Type.SET_INVITE);
				goldLogInvite.setNum(Integer.valueOf(invite_gold));
				goldLogInvite.setOldNum(userGold);
				goldLogInvite.setNewNum(Integer.valueOf(updateGold));
				goldLogInvite.setCreateUser(user.getId());
				goldLogInvite.setCreateTime(new Date());
				goldLogRepository.save(goldLogInvite);
			}
			
		}
		
		return new ResponseBean(Code.SUCCESS,Code.SUCCESS, new LoginRes(user));
	}

	@ApiOperation(value = "绑定微信", notes = "", httpMethod = "POST")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "userId", value = "用户ID", required = true, dataType = "String"),
		@ApiImplicitParam(paramType = "query", name = "openid", value = "微信返回值透传", required = true, dataType = "String"),
		@ApiImplicitParam(paramType = "query", name = "nickname", value = "微信返回值透传", required = true, dataType = "String"),
		@ApiImplicitParam(paramType = "query", name = "sex", value = "微信返回值透传", required = true, dataType = "String"),
		@ApiImplicitParam(paramType = "query", name = "language", value = "微信返回值透传", required = true, dataType = "String"),
		@ApiImplicitParam(paramType = "query", name = "city", value = "微信返回值透传", required = true, dataType = "String"),
		@ApiImplicitParam(paramType = "query", name = "province", value = "微信返回值透传", required = true, dataType = "String"),
		@ApiImplicitParam(paramType = "query", name = "country", value = "微信返回值透传", required = true, dataType = "String"),
		@ApiImplicitParam(paramType = "query", name = "headimgurl", value = "微信返回值透传", required = true, dataType = "String"),
		@ApiImplicitParam(paramType = "query", name = "unionid", value = "微信返回值透传", required = true, dataType = "String")
			})
	@PostMapping("bind")
	public ResponseBean<Object> bind(String userId, String openid, String nickname, String sex, String language, String city, String province, String country, String headimgurl, String unionid) throws UnsupportedEncodingException {
		
		Optional<UserLogin> user = userRepository.findById(userId);
		
		if(user.isPresent()) {
			UserLogin ul = user.get();
			if(!StringUtils.isEmpty(ul.getOpenid())) {
				return new ResponseBean<>(Code.FAIL, Code.FAIL, "您已绑定过微信，无需重复绑定~");
			}
		}
		
		int cnt = userRepository.countByOpenid(openid);
		
		if(cnt > 0) {
			return new ResponseBean<>(Code.FAIL, Code.FAIL, "该微信已被绑定过，请更换其他微信~");
		}
		
		
		
		if(user.isPresent()) {
			if(org.springframework.util.StringUtils.isEmpty(user.get().getOpenid())) {
				UserLogin uu = user.get();
				uu.setOpenid(openid);
				
				String[] states = uu.getNewerMission().split("\\|");
				uu.setNewerMission((Integer.valueOf(states[0]) + 1) + "|" + states[1] + "|" + states[2] + "|" + states[3]);
				
				uu.setHeadPortrait(headimgurl);
				
				uu.setName(nickname);
				// 增加金币
				uu.setGold(uu.getGold() + 100);
				
				userRepository.save(uu);
				
				//增加金币
				GoldLog gl = new GoldLog(userId, uu.getGold(), 100, uu.getGold() - 100, Consts.GoldLog.Type.BIND_WEIXIN);
				goldLogRepository.save(gl);
				
				return new ResponseBean<Object>(Code.SUCCESS, Code.SUCCESS, "微信绑定成功~");
			}else {
				return new ResponseBean<Object>(Code.FAIL, Code.FAIL, "您已经绑定过微信~");				
			}
		}else {
			return new ResponseBean<Object>(Code.FAIL, Code.FAIL, "用户不存在~");
		}
	}
	
	@ApiOperation(value = "微信登录", notes = "", httpMethod = "POST")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "openid", value = "微信返回值透传", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "nickname", value = "微信返回值透传", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "sex", value = "微信返回值透传", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "language", value = "微信返回值透传", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "city", value = "微信返回值透传", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "province", value = "微信返回值透传", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "country", value = "微信返回值透传", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "headimgurl", value = "微信返回值透传", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "unionid", value = "微信返回值透传", required = true, dataType = "String")
			})
	@PostMapping("login")
	public ResponseBean<LoginRes> login(String openid, String nickname, String sex, String language, String city, String province, String country, String headimgurl, String unionid) throws UnsupportedEncodingException {
		
		Optional<UserLogin> user = userRepository.findByOpenid(openid);
		
		UserLogin userLogin;
		if(user.isPresent()) {
			userLogin = user.get();
		}else {
			userLogin = userLoginService.save(new UserLogin(openid, headimgurl, "1|0|0|0", nickname, 100));		
			
			//增加金币
			GoldLog gl = new GoldLog(userLogin.getId(), userLogin.getGold(), 100, userLogin.getGold() - 100, Consts.GoldLog.Type.BIND_WEIXIN);
			goldLogRepository.save(gl);
		}
		
		return new ResponseBean<>(Code.SUCCESS,Code.SUCCESS_CODE,"成功",new LoginRes(userLogin));
	}
}
