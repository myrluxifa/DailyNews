package com.lvmq.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.lvmq.model.NewsInfoRead;
import com.lvmq.repository.base.BaseRepository;

@Component
public interface NewsInfoReadRepository extends CrudRepository<NewsInfoRead,String> {
	int countByuserIdAndNewsId(String userId,String newsId);
	
	Optional<NewsInfoRead> findByNewsIdAndUserId(String newsId,String userId);
	
	int countByNewsIdAndUserId(String newsId,String userId);
}
