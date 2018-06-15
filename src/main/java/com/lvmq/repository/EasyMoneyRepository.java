package com.lvmq.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.lvmq.model.EasyMoney;

public interface EasyMoneyRepository extends CrudRepository<EasyMoney, String> {
	List<EasyMoney> findByFlag(Pageable pageable,int flag);
	
}
