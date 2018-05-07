package com.lvmq.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lvmq.base.Argument;
import com.lvmq.model.UserLogin;
import com.lvmq.repository.UserLoginRepository;
import com.lvmq.service.UserLoginService;
import com.lvmq.util.MD5;
import com.lvmq.util.Util;

@Component
public class UserLoginServiceImpl implements UserLoginService{
	
	@Autowired
	private UserLoginRepository userLoginRepository;
	
	public int countByUserName(String userName) {
		return userLoginRepository.countByUserName(userName);
	}

	@Override
	public Optional<UserLogin> login(UserLogin userLogin) {
		// TODO Auto-generated method stub
		return userLoginRepository.findByUserNameAndPasswd(userLogin.getUserName(),userLogin.getPasswd());
	}
	
	public UserLogin save(UserLogin userLogin) {
		userLogin.setFlag(0);
		//新手注册
		userLogin.setGold(Argument.NEW_USER_GOLD);
		//邀请人数
		userLogin.setInviteCount(0);
		//余额
		userLogin.setBalance(0);
		//收益
		userLogin.setEarnings(0);
		
		String _myInviteCode=Util.getRandomReferralCode();
		while(userLoginRepository.countByMyInviteCode(_myInviteCode)>0) {
			_myInviteCode=Util.getRandomReferralCode();
		}
		
		userLogin.setMyInviteCode(_myInviteCode);
		
		UserLogin user=userLoginRepository.save(userLogin);
		
		if(!Util.isBlank(userLogin.getInviteCode())) {
			//给邀请人添加邀请数量
			UserLogin inviteUser=userLoginRepository.findByMyInviteCode(userLogin.getInviteCode());
			inviteUser.setInviteCount(inviteUser.getInviteCount()+1);
			userLoginRepository.save(inviteUser);
		}
		
		return user;
	}

	@Override
	public UserLogin updatePasswd(String userName,String passwd) {
		// TODO Auto-generated method stub
		UserLogin userLogin=userLoginRepository.findByUserName(userName);
		userLogin.setPasswd(MD5.getMD5(passwd));
		userLogin=userLoginRepository.save(userLogin);
		return userLogin;
	}
}
