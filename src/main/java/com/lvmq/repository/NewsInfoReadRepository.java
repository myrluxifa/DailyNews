package com.lvmq.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.lvmq.model.NewsInfoRead;
import com.lvmq.repository.base.BaseRepository;

@Component
public interface NewsInfoReadRepository extends CrudRepository<NewsInfoRead,String> {
	int countByuserIdAndNewsId(String userId,String newsId);
}
