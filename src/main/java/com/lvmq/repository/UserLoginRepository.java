package com.lvmq.repository;


import org.springframework.stereotype.Repository;

import com.lvmq.model.UserLogin;
import com.lvmq.repository.base.BaseRepository;

@Repository
public interface UserLoginRepository extends BaseRepository<UserLogin> {
	
}
