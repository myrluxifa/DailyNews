package com.lvmq.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lvmq.model.MakeMoneyLog;
import java.lang.String;
import java.util.List;
import java.util.Optional;

@Repository
public interface MakeMoneyLogRepository  extends CrudRepository<MakeMoneyLog, String>{
	Optional<MakeMoneyLog> findByMakeMoneyIdAndUserId(String makemoneyid,String userId);
	
	List<MakeMoneyLog> findByUserId(Pageable pageable,String userId);
}
