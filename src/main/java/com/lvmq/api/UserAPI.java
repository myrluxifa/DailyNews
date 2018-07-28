package com.lvmq.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.lvmq.api.res.LoginRes;
import com.lvmq.api.res.base.ResponseBean;
import com.lvmq.base.Code;
import com.lvmq.base.Consts;
import com.lvmq.idata.IDataAPI;
import com.lvmq.idata.res.ToutiaoResponseDto;
import com.lvmq.model.DayMission;
import com.lvmq.model.GoldLog;
import com.lvmq.model.MessageCode;
import com.lvmq.model.UserLogin;
import com.lvmq.repository.GoldLogRepository;
import com.lvmq.repository.UserLoginRepository;
import com.lvmq.repository.WithdrawLogRepository;
import com.lvmq.service.DayMissionService;
import com.lvmq.service.NewsService;
import com.lvmq.service.UserLoginService;
import com.lvmq.service.VideosService;
import com.lvmq.util.MD5;
import com.lvmq.util.SMS;
import com.lvmq.util.TimeUtil;
import com.lvmq.util.Util;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = {"用户"})
@RestController
@RequestMapping("/api/user")
public class UserAPI {
	
	@Autowired
	private UserLoginService userLoginService;
	
	@Autowired
	private UserLoginRepository userLoginRepository;
	
	@Autowired
	private DayMissionService dayMissionService;
	
	@Autowired
	private GoldLogRepository goldLogRepository;
	
	@Autowired
	private WithdrawLogRepository withdrawLogRepository;
	
	private static final Logger log = LoggerFactory.getLogger(UserAPI.class);
	
	
	public static Map<String,MessageCode> messageCodeMap=new HashMap<String,MessageCode>();
	
	
	

	@ApiOperation(value = "登录", notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "userName", value = "电话", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "passwd", value = "密码", required = true, dataType = "String")
	})
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ResponseBean<LoginRes> login(String userName,String passwd) {
		
		if(userLoginService.countByUserName(userName)==0) return new ResponseBean(Code.FAIL,Code.USER_UNFINDABLE,"账号不存在"); 
		
		Optional<UserLogin> userLoginOpt=userLoginService.login(new UserLogin(userName,MD5.getMD5(passwd)));
		
		if(userLoginOpt.isPresent()) {
			UserLogin userLogin=userLoginOpt.get();
		
			//1元提现
			int cnt = withdrawLogRepository.countByUserIdAndFeeAndState(userLogin.getId(), "1", Consts.Withdraw.State.PASS);
			
			String earnings=userLoginService.getUserEarnings(userLogin.getId());
			
			int tag = withdrawLogRepository.countByUserIdAndState(userLogin.getId(), Consts.Withdraw.State.DEFAULT);
			
			return new ResponseBean<LoginRes>(Code.SUCCESS,Code.SUCCESS_CODE,"成功",new LoginRes(userLogin, cnt, tag,earnings));
		}else {
			return new ResponseBean(Code.FAIL,Code.UNKOWN_CODE,"账号或密码错误"); 
		}
	}
	
	
	
	
	@ApiOperation(value = "发送验证码", notes = "临时验证码123456")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "phone", value = "电话", required = true, dataType = "String")
	})
	@RequestMapping(value="/sendRegisterCode",method=RequestMethod.POST)
	public ResponseBean sendRegisterCode(String phone) {
		try {
			if(userLoginService.countByUserName(phone)>0) return new ResponseBean(Code.FAIL,Code.USER_ALREADY_EXISTS,"账号已存在");
			
			String code="1";//Util.getRandom6();
			SMS.singleSendSms(phone, code, Consts.SmsConfig.TEMPLATECODE);
			messageCodeMap.put(phone, new MessageCode(code));
			return new ResponseBean(Code.SUCCESS,Code.SUCCESS_CODE,"成功");
		}catch(Exception e) {
			return new ResponseBean(Code.FAIL,Code.UNKOWN_CODE,"失败");
		}
	}
	
	@ApiOperation(value = "发送忘记密码验证码", notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "phone", value = "电话", required = true, dataType = "String")
	})
	@RequestMapping(value="/sendForgetCode",method=RequestMethod.POST)
	public ResponseBean sendForgetCode(String phone) {
		try {
			if(userLoginService.countByUserName(phone)==0) return new ResponseBean(Code.FAIL,Code.MESSAGE_CODE_UNFINDABLE,"账号不存在"); 
			
			String code=Util.getRandom6();
			SMS.singleSendSms(phone, code, Consts.SmsConfig.TEMPLATECODE);
			messageCodeMap.put(phone, new MessageCode(code));
			return new ResponseBean(Code.SUCCESS,Code.SUCCESS_CODE,"成功");
		}catch(Exception e) {
			return new ResponseBean(Code.FAIL,Code.UNKOWN_CODE,"失败");
		}
	}
	
	@ApiOperation(value = "修改密码2", notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "userId", value = "用户Id", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "oldPasswd", value = "原密码", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "newPasswd", value = "新密码", required = true, dataType = "String")
	})
	@RequestMapping(value="/updatePasswd2", method=RequestMethod.POST)
	public ResponseBean updatePasswd2(String userId,String newPasswd,String oldPasswd) {
		
		try {
			Optional<UserLogin> ouser = userLoginRepository.findById(userId);
			
			UserLogin user = ouser.get();
			
			if(!MD5.getMD5(oldPasswd).equals(user.getPasswd())) {
				return new ResponseBean<>(Code.FAIL, Code.FAIL, "原密码不正确");
			}
			
			user.setPasswd(MD5.getMD5(newPasswd));
			
			userLoginRepository.save(user);
			
			return new ResponseBean(Code.SUCCESS,Code.SUCCESS_CODE,"成功");
		}catch(Exception e) {
			return new ResponseBean(Code.FAIL,Code.UNKOWN_CODE,"未知错误");
		}
		
	}
	
	@ApiOperation(value = "修改密码", notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "userName", value = "电话", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "newPasswd", value = "新密码", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "code", value = "验证码", required = true, dataType = "String")
	})
	@RequestMapping(value="/updatePasswd",method=RequestMethod.POST)
	public ResponseBean updatePasswd(String userName,String newPasswd,String code) {
		
		if(!messageCodeMap.containsKey(userName)) {
			return new ResponseBean(Code.FAIL,Code.MESSAGE_CODE_UNFINDABLE,"验证码不存在");
		}
		
		MessageCode  m=messageCodeMap.get(userName);
		//如果验证码过期
		if(TimeUtil.ifPastDue(m.getTime())) {
			return new ResponseBean(Code.FAIL,Code.MESSAGE_CODE_PAST_DUE,"验证码过期");
		}
		
		if(!m.getCode().equals(code)) {
			return new ResponseBean(Code.FAIL,Code.MESSAGE_CODE_MISTAKE,"验证错误");
		}
		
		//验证成功 移除当前验证码
		messageCodeMap.remove(userName);
		try {
			userLoginService.updatePasswd(userName, newPasswd);
			return new ResponseBean(Code.SUCCESS,Code.SUCCESS_CODE,"成功");
		}catch(Exception e) {
			return new ResponseBean(Code.FAIL,Code.UNKOWN_CODE,"未知错误");
		}
		
	}
	
	@ApiOperation(value = "注册", notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "userName", value = "电话", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "passwd", value = "密码", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "code", value = "验证码", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "inviteCode", value = "邀请码", required = false, dataType = "String")
	})
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public ResponseBean register(String userName,String passwd,String code,String inviteCode) {
		try {
			if(!messageCodeMap.containsKey(userName)) {
				return new ResponseBean(Code.FAIL,Code.MESSAGE_CODE_UNFINDABLE,"验证码不存在");
			}
			
			MessageCode  m=messageCodeMap.get(userName);
			//如果验证码过期
			if(TimeUtil.ifPastDue(m.getTime())) {
				return new ResponseBean(Code.FAIL,Code.MESSAGE_CODE_PAST_DUE,"验证码过期");
			}
			
			if(!m.getCode().equals(code)) {
				return new ResponseBean(Code.FAIL,Code.MESSAGE_CODE_MISTAKE,"验证错误");
			}
			
			//验证成功 移除当前验证码
			messageCodeMap.remove(userName);
			
			userLoginService.save(new UserLogin(userName,MD5.getMD5(passwd),inviteCode));
			
			return new ResponseBean(Code.SUCCESS,Code.SUCCESS_CODE,"成功");
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseBean(Code.FAIL,Code.UNKOWN_CODE,e.getMessage());
		}
	}
	
	
	@ApiOperation(value="获取个人信息",notes="")
	@RequestMapping(value="/getUserInfo",method=RequestMethod.POST)
	public ResponseBean getUserInfo(String userId) {
		
		//1元提现
		int cnt = withdrawLogRepository.countByUserIdAndFeeAndState(userId, "1", Consts.Withdraw.State.PASS);
		
		int tag = withdrawLogRepository.countByUserIdAndState(userId, Consts.Withdraw.State.DEFAULT);
		
		return new ResponseBean(Code.SUCCESS,Code.SUCCESS_CODE,"成功", userLoginService.findByUserId(userId, cnt, tag));
	}
	
	
	@CrossOrigin(origins="*", maxAge = 3600)
	@ApiOperation(value="获取个人收益",notes="")
	@RequestMapping(value="/getUserEarnings",method=RequestMethod.POST)
	public ResponseBean getUserEarnings(String userId) {
		return new ResponseBean(Code.SUCCESS,Code.SUCCESS_CODE,"成功",userLoginService.getUserEarnings(userId));
	}
	
	
	@CrossOrigin(origins="*", maxAge = 3600)
	@ApiOperation(value="每日分享",notes="")
	@RequestMapping(value="/shareEveryDay",method=RequestMethod.POST)
	public ResponseBean shareEveryDay(String userId) {
		return new ResponseBean(Code.SUCCESS,Code.SUCCESS_CODE,"成功",userLoginService.shareEveryDay(userId));
	}
	
	@ApiOperation(value="获得文案",notes="分类(DEPOSIT:提现说明;INVITE:邀请规则;SIGN_IN:签到规则;MAKEMONEY:赚钱攻略;HELP:帮助与反馈)")
	@RequestMapping(value="/getOfficial",method=RequestMethod.POST)
	public ResponseBean  getOfficial(String type) {
		return new ResponseBean(Code.SUCCESS,Code.SUCCESS_CODE,"成功",userLoginService.getOfficial(type));
	}
}
