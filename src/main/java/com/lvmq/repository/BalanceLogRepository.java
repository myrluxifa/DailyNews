package com.lvmq.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.lvmq.model.BalanceLog;


public interface BalanceLogRepository extends CrudRepository<com.lvmq.model.BalanceLog, String> {
	int countByTypeAndUserIdAndCreateTimeBetween(String share, String userId, Date startTime, Date endTime);

	List<BalanceLog> findByTypeAndUserIdAndCreateTimeBetweenOrderByCreateTimeDesc(String share, String userId,
			Date startTime, Date endTime);

	Page<BalanceLog> findByUserId(Pageable pageable, String userId);
}
