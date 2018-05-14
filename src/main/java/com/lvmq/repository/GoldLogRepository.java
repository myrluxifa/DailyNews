package com.lvmq.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lvmq.model.GoldLog;

public interface GoldLogRepository extends JpaRepository<GoldLog, Long> {

	int countByTypeAndUserIdAndCreateTimeBetween(String share, String userId, Date startTime, Date endTime);

	List<GoldLog> countByTypeAndUserIdAndCreateTimeBetweenOrderByCreateTimeDesc(String share, String userId, Date startTime,
			Date endTime);

}
