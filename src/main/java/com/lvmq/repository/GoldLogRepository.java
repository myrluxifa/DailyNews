package com.lvmq.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lvmq.model.GoldLog;

public interface GoldLogRepository extends JpaRepository<GoldLog, Long> {

	int countByTypeAndUserIdAndCreateTimeBetween(String share, String userId, Date startTime, Date endTime);
	
	int countByTypeAndUserIdAndTriggerUserIdAndCreateTimeBetween(String share, String userId ,String triggerUserId,Date startTime, Date endTime);

	List<GoldLog> findByTypeAndUserIdAndCreateTimeBetweenOrderByCreateTimeDesc(String share, String userId,
			Date startTime, Date endTime);

	Page<GoldLog> findByUserId(Pageable pageable, String userId);
	
	
	@Query(value="select ifnull(sum(num),0) from t_gold_log where type=?1 and user_id=?2 and create_time>=?3 and create_time<?4",nativeQuery=true)
	int sumNumByTypeAndUserIdAndCreateTimeBetween(String share, String userId, String startTime, String endTime);
	
	
	@Query(value="select ifnull(sum(num),0) from t_gold_log where type in ?1 and user_id=?2",nativeQuery=true)
	int sumNumByTypeInAndUserId(List<String> type,String userId );

}
