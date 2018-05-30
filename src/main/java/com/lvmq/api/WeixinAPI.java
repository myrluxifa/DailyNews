package com.lvmq.api;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lvmq.api.res.base.ResponseBean;
import com.lvmq.base.Code;
import com.lvmq.model.UserLogin;
import com.lvmq.repository.UserLoginRepository;
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

	@ApiOperation(value = "绑定微信", notes = "", httpMethod = "POST")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "code", value = "用户授权后返回的Code", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "userId", value = "用户Id", required = true, dataType = "String")
			})
	@PostMapping("bind")
	public ResponseBean<Object> login(String[] code, String[] state, String userId) throws UnsupportedEncodingException {
		
		Map<String, Object> map = Weixin.getAccessToken(code[0]);
		
		Optional<UserLogin> user = userRepository.findById(userId);
		
		if(user.isPresent()) {
			if(org.springframework.util.StringUtils.isEmpty(user.get().getOpenid())) {
				UserLogin uu = user.get();
				uu.setOpenid(map.get("openid").toString());
				userRepository.save(uu);
				//FIXME 完成增加金币功能
				
				return new ResponseBean<Object>(Code.SUCCESS, Code.SUCCESS, "微信绑定成功~");
			}else {
				return new ResponseBean<Object>(Code.FAIL, Code.FAIL, "您已经绑定过微信~");				
			}
		}else {
			return new ResponseBean<Object>(Code.FAIL, Code.FAIL, "用户不存在~");
		}
	}
}
