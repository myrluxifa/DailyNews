package com.lvmq.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lvmq.model.UserLogin;
import com.lvmq.repository.UserLoginRepository;
import com.lvmq.service.UserLoginService;

@Component
public class UserLoginServiceImpl implements UserLoginService{
	
	@Autowired
	private UserLoginRepository userLoginRepository;

	@Override
	public Optional<UserLogin> login(UserLogin userLogin) {
		// TODO Auto-generated method stub
		return userLoginRepository.findById(userLogin.getId());
	}
}
