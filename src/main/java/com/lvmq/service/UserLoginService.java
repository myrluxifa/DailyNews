package com.lvmq.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lvmq.api.res.EveryDayShareRes;
import com.lvmq.api.res.LoginRes;
import com.lvmq.model.UserLogin;

@Service
public interface UserLoginService {
	
	LoginRes findByUserId(String userId);
	
	LoginRes findByUserId(String userId, int cnt, int tag);
	
	Optional<UserLogin> login(UserLogin userLogin);
	
	UserLogin save(UserLogin userLogin);
	
	int countByUserName(String userName);
	
	UserLogin updatePasswd(String userName,String passwd);
	
	List<UserLogin> findByInviteCode(String inviteCode);
	
	String getUserEarnings(String userId);
	
	EveryDayShareRes shareEveryDay(String userId);
	
	String getOfficial(String type);

}
