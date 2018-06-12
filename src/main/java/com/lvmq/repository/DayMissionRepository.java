package com.lvmq.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lvmq.model.DayMission;

public interface DayMissionRepository extends JpaRepository<DayMission, String> {

	DayMission findByUserIdAndMdate(String userId, String mdate);
	
}
