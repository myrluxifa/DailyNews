package com.lvmq.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lvmq.model.DayMission;

public interface DayMissionRepository extends JpaRepository<DayMission, String> {

	List<DayMission> findByUserIdAndMdate(String userId, String mdate);
	
}
