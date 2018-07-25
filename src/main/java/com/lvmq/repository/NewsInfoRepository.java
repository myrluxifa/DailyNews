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
	
	@Query(value="select * from t_news_info where id not in (select news_id from t_news_remove_log where user_id=?1) and cat_id=?2 order by publish_date desc limit ?3, ?4",nativeQuery=true)
	List<NewsInfo> findByCatIdByNativeQuery(String userId,String catId,Integer page,Integer pageSize);
	
	List<NewsInfo>	findByFlag(Pageable pageable,int flag);

	@Query(value = "select title from t_news_info order by publish_date desc limit 100" , nativeQuery = true)
	List<String> findTop100TitleOrderByPublishDateDesc();
}
