package com.lvmq.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lvmq.model.UserLogin;

@Service
public interface UserLoginService {
	Optional<UserLogin> login(UserLogin userLogin);
}
