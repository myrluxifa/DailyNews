package com.lvmq.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.lvmq.model.RecallLog;

public interface RecallLogRepository extends CrudRepository<RecallLog, String> {
	RecallLog save(RecallLog recallLog);
	
	int countByUserIdAndRecallUserAndCreateTimeBetween(String userId,String recallUserId,Date startTime,Date endTime);
	
	int countByRecallUserAndFlagAndCreateTimeBetween(String recallUserId,int flag,Date startTime,Date endTime);
	
	List<RecallLog> findByRecallUserAndFlagAndCreateTimeBetween(Pageable pageable,String recallUserId,int flag, Date startTime,Date endTime);
	
	@Modifying
	@Query(value="update t_recall_log set flag=1 where recall_user=?1",nativeQuery=true)
	RecallLog updateByRecallUser(String recallUserId);
}
