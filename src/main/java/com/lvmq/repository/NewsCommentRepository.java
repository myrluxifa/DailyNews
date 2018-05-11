package com.lvmq.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.lvmq.model.NewsComment;
import com.lvmq.repository.base.BaseRepository;

public interface NewsCommentRepository extends BaseRepository<NewsComment> {
	List<NewsComment> findByNewsIdAndFlagAndLevel(Pageable pageable,String newsId,int flag,int level);
	
	List<NewsComment> findByParentIdAndFlag(Pageable pageable,String parentId,int flag);

	NewsComment saveAndFlush(NewsComment newsComment);
}
