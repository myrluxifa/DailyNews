package com.lvmq.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lvmq.model.SysMessage;

public interface SysMessageRepository extends JpaRepository<SysMessage, String> {

	Page<SysMessage> findByFlag(Pageable pagePluginSort, int i);

	SysMessage findTop1ByFlagOrderByCreateTimeDesc(int i);

}
