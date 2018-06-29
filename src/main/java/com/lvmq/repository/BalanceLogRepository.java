package com.lvmq.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.lvmq.model.BalanceLog;


public interface BalanceLogRepository extends CrudRepository<com.lvmq.model.BalanceLog, String> {
	int countByTypeAndUserIdAndCreateTimeBetween(String share, String userId, Date startTime, Date endTime);
	
	int countByTypeAndUserIdAndTriggerUserIdAndCreateTimeBetween(String share, String userId ,String triggerUserId,Date startTime, Date endTime);

	List<BalanceLog> findByTypeAndUserIdAndCreateTimeBetweenOrderByCreateTimeDesc(String share, String userId,
			Date startTime, Date endTime);

	Page<BalanceLog> findByUserId(Pageable pageable, String userId);
	
	BalanceLog save(BalanceLog balanceLog);
	
	@Query(value="select ifnull(sum(num),0) from t_balance_log where type in ?1 and user_id=?2",nativeQuery=true)
	String sumNumByTypeInAndUserId(List<String> type,String userId );
	
	@Query(value="select ifnull(sum(num),0) from t_balance_log where type in ?1 and trigger_user_id=?2 and user_id=?3",nativeQuery=true)
	String sumByTypeAndTriggerUserIdAndUserId(List<String> type,String triggerUserId,String userId);

	List<BalanceLog> findByUserId(String userId);
	
	@Query(value="select ifnull(sum(num),0) from t_balance_log where user_id=?1",nativeQuery=true)
	String sumNumByUserId(String userId );

}
