package com.lvmq.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lvmq.model.UserLogin;
import com.lvmq.repository.base.BaseRepository;

@Repository
public interface UserLoginRepository extends BaseRepository<UserLogin> {

	UserLogin findByMyInviteCode(String myInviteCode);
	
	int countByMyInviteCode(String myInviteCode);
	
	

	int countByUserName(String userName);

	Optional<UserLogin> findByUserNameAndPasswd(String userName,String passwd);
	
	UserLogin findByUserName(String userName);
	
	List<UserLogin> findByInviteCode(String inviteCode);
}
