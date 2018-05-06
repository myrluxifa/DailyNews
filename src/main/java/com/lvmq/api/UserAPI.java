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
import com.lvmq.service.NewsService;
import com.lvmq.service.UserLoginService;
import com.lvmq.service.VideosService;

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

	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public UserLogin login(String id) {
		try {
			
			videosService.getVideosFromIDataAPI();
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
