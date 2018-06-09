package com.lvmq.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lvmq.model.GoldRewards;

@Repository
public interface GoldRewardsRepository extends CrudRepository<GoldRewards, String> {
	GoldRewards findByType(String type);
	
}
