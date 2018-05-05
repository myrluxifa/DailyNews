package com.lvmq.api;

import java.io.IOException;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.lvmq.idata.IdataAPI;
import com.lvmq.idata.res.ToutiaoResponseDto;
import com.lvmq.model.UserLogin;
import com.lvmq.service.UserLoginService;

@RestController
@RequestMapping("/api/user")
public class UserAPI {
	
	@Autowired
	private UserLoginService userLoginService;
	
	
	private static final Logger log = LoggerFactory.getLogger(UserAPI.class);

	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public UserLogin login(String id) {
		try {
			// 请求示例 url 默认请求参数已经做URL编码
			String url = "http://api01.bitspaceman.com:8000/news/toutiao?apikey=np5SpQ7QGzm7HgvX8Aw8APA5NDq6Bpj5m4eo4hX5qJFLm0G0Oqt31xJzjIEeJFTv&catid=news_society";
			String json = IdataAPI.getRequestFromUrl(url);
			log.info(json);
			Gson gson=new Gson();
			ToutiaoResponseDto toutiaoResponseDto=gson.fromJson(json, ToutiaoResponseDto.class);
		}catch(Exception e) {
			log.info(e.getMessage());
			return new UserLogin();
		}
		Optional<UserLogin> userLoginOpt=userLoginService.login(new UserLogin(id));
		
		if(userLoginOpt.isPresent()) {
			UserLogin userLogin=userLoginOpt.get();
			
			return userLogin;
		}else {
			return new UserLogin();
		}
	}
}
