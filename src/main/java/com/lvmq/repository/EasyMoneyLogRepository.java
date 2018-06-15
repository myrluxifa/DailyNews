package com.lvmq.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.lvmq.model.EasyMoneyLog;

public interface EasyMoneyLogRepository extends CrudRepository<EasyMoneyLog, String> {
	
  	List<EasyMoneyLog> findByUserId(String userId);
}
