package com.lvmq.api;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lvmq.api.res.LoginRes;
import com.lvmq.api.res.base.ResponseBean;
import com.lvmq.base.Code;
import com.lvmq.base.Consts;
import com.lvmq.model.GoldLog;
import com.lvmq.model.UserLogin;
import com.lvmq.repository.GoldLogRepository;
import com.lvmq.repository.UserLoginRepository;
import com.lvmq.service.UserLoginService;
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

	@ApiOperation(value = "绑定微信", notes = "", httpMethod = "POST")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "code", value = "用户授权后返回的Code", required = true, dataType = "String[]"),
			@ApiImplicitParam(paramType = "query", name = "userId", value = "用户Id", required = true, dataType = "String")
			})
	@PostMapping("bind")
	public ResponseBean<Object> bind(String[] code, String[] state, String userId) throws UnsupportedEncodingException {
		
		Map<String, Object> map = Weixin.getAccessToken(code[0]);
		
		Optional<UserLogin> user = userRepository.findById(userId);
		
		if(user.isPresent()) {
			if(org.springframework.util.StringUtils.isEmpty(user.get().getOpenid())) {
				UserLogin uu = user.get();
				uu.setOpenid(map.get("openid").toString());
				
				String[] states = uu.getNewerMission().split("\\|");
				uu.setNewerMission((Integer.valueOf(states[0]) + 1) + "|" + states[1] + "|" + states[2] + "|" + states[3]);
				
				userRepository.save(uu);
				
				//增加金币
				GoldLog gl = new GoldLog(userId, uu.getGold() + 100, 100, uu.getGold(), Consts.GoldLog.Type.BIND_WEIXIN);
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
			@ApiImplicitParam(paramType = "query", name = "code", value = "用户授权后返回的Code", required = true, dataType = "String[]")
			})
	@PostMapping("login")
	public ResponseBean<LoginRes> login(String[] code, String[] state, String userId) throws UnsupportedEncodingException {
		
		Map<String, Object> map = Weixin.getAccessToken(code[0]);
		
		Optional<UserLogin> user = userRepository.findById(userId);
		
		UserLogin userLogin;
		if(user.isPresent()) {
			userLogin = user.get();			
		}else {
			userLogin = userLoginService.save(new UserLogin(map.get("openid").toString()));		
			
			//增加金币
			GoldLog gl = new GoldLog(userLogin.getId(), userLogin.getGold() + 100, 100, userLogin.getGold(), Consts.GoldLog.Type.BIND_WEIXIN);
			goldLogRepository.save(gl);
		}
		
		return new ResponseBean<>(Code.SUCCESS,Code.SUCCESS_CODE,"成功",new LoginRes(userLogin));
	}
}
