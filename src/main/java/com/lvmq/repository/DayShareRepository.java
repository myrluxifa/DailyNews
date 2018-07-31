package com.lvmq.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lvmq.model.DayShare;

public interface DayShareRepository extends JpaRepository<DayShare, String> {

	DayShare findTop1ByUserIdAndMdateOrderByCreateTimeDesc(String userId, String today);

}
