package com.lvmq.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lvmq.model.UserReadMessage;

public interface UserReadMessageRepository extends JpaRepository<UserReadMessage, String> {

	UserReadMessage findTop1ByUserIdOrderByCreateTimeDesc(String userId);

}
