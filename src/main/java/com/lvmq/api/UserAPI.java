package com.lvmq.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.lvmq.api.res.LoginRes;
import com.lvmq.api.res.base.ResponseBean;
import com.lvmq.base.Code;
import com.lvmq.idata.IdataAPI;
import com.lvmq.idata.res.ToutiaoResponseDto;
import com.lvmq.model.MessageCode;
import com.lvmq.model.UserLogin;
import com.lvmq.service.NewsService;
import com.lvmq.service.UserLoginService;
import com.lvmq.service.VideosService;
import com.lvmq.util.MD5;
import com.lvmq.util.TimeUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/user")
public class UserAPI {
	
	@Autowired
	private UserLoginService userLoginService;
	
	@Autowired
	private NewsService newsService;
	
	@Autowired
	private VideosService videosService;
	
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
		
			return new ResponseBean<LoginRes>(Code.SUCCESS,Code.SUCCESS_CODE,"成功",new LoginRes(userLogin));
		}else {
			return new ResponseBean(Code.FAIL,Code.UNKOWN_CODE,"账号或密码错误"); 
		}
	}
	
	
	@RequestMapping(value="/sendRegisterCode",method=RequestMethod.POST)
	public ResponseBean sendRegisterCode(String phone) {
		try {
			if(userLoginService.countByUserName(phone)>0) return new ResponseBean(Code.FAIL,Code.USER_ALREADY_EXISTS,"账号已存在"); 
			
			messageCodeMap.put(phone, new MessageCode("66666"));
			return new ResponseBean(Code.SUCCESS,Code.SUCCESS_CODE,"成功");
		}catch(Exception e) {
			return new ResponseBean(Code.FAIL,Code.UNKOWN_CODE,"失败");
		}
	}
	
	
	@RequestMapping(value="/sendForgetCode",method=RequestMethod.POST)
	public ResponseBean sendForgetCode(String phone) {
		try {
			if(userLoginService.countByUserName(phone)==0) return new ResponseBean(Code.FAIL,Code.MESSAGE_CODE_UNFINDABLE,"账号不存在"); 
			
			messageCodeMap.put(phone, new MessageCode("66666"));
			return new ResponseBean(Code.SUCCESS,Code.SUCCESS_CODE,"成功");
		}catch(Exception e) {
			return new ResponseBean(Code.FAIL,Code.UNKOWN_CODE,"失败");
		}
	}
	
	
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
	
	
}
