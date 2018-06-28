package com.lvmq.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lvmq.model.WithdrawLog;

public interface WithdrawLogRepository extends JpaRepository<WithdrawLog, String> {

	Page<WithdrawLog> findByUserId(Pageable pagePluginSort, String userId);

	int countByUserIdAndFeeAndState(String userId, String string, String pass);

	int countByUserIdAndState(String userId, String default1);

}
