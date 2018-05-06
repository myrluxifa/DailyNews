package com.lvmq.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lvmq.model.NewsInfo;
import com.lvmq.repository.base.BaseRepository;

@Repository
public interface NewsInfoRepository extends BaseRepository<NewsInfo> {
	
}
