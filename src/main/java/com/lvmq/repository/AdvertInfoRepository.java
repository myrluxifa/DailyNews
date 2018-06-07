package com.lvmq.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.lvmq.model.AdvertInfo;
import com.lvmq.model.NewsInfo;
import com.lvmq.repository.base.BaseRepository;

@Repository
public interface AdvertInfoRepository extends BaseRepository<AdvertInfo> {
	List<AdvertInfo> findByFlag(Pageable pageable,int flag);
	
}
