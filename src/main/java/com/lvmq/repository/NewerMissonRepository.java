package com.lvmq.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lvmq.model.NewerMission;

public interface NewerMissonRepository extends JpaRepository<NewerMission, Long> {

	NewerMission findByUserId(String userId);

}
