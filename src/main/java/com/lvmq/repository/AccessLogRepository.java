package com.lvmq.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.lvmq.model.AccessLog;

public interface AccessLogRepository extends CrudRepository<AccessLog, String> {

	Page<AccessLog> findByUserId(Pageable pagePluginSort, String userId);

}
