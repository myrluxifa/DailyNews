package com.lvmq.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.lvmq.model.SignLog;

public interface SignLogRepository extends CrudRepository<SignLog, String> {

	List<SignLog> findByCreateDateInAndUserIdOrderByCreateDateDesc(String[] strings, String userId);

}
