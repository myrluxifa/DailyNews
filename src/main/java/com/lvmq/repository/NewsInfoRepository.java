package com.lvmq.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lvmq.model.NewsInfo;
import com.lvmq.model.VideosInfo;
import com.lvmq.repository.base.BaseRepository;

@Repository
public interface NewsInfoRepository extends BaseRepository<NewsInfo> {
	List<NewsInfo> findByCatId(Pageable pageable,String catId);
	
	List<NewsInfo>	findByFlag(Pageable pageable,int flag);

	@Query(value = "select title from t_news_info order by publish_date desc limit 100" , nativeQuery = true)
	List<String> findTop100TitleOrderByPublishDateDesc();
}
