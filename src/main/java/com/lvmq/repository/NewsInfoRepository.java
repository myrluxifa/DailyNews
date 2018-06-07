package com.lvmq.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.lvmq.model.NewsInfo;
import com.lvmq.model.VideosInfo;
import com.lvmq.repository.base.BaseRepository;

@Repository
public interface NewsInfoRepository extends BaseRepository<NewsInfo> {
	List<NewsInfo> findByCatId(Pageable pageable,String catId);
	
	List<NewsInfo>	findByFlag(Pageable pageable,int flag);
}
