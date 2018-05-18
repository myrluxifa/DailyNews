package com.lvmq.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.lvmq.model.ReadReward;

public interface ReadRewardsRepository extends CrudRepository<ReadReward, String> {
	Optional<ReadReward> findById(String id);
}
