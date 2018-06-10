package com.lvmq.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lvmq.model.MakeMoney;

@Repository
public interface MakeMoneyRepository extends CrudRepository<MakeMoney, String> {
	
	
	List<MakeMoney> findByFlag(int flag);
}
