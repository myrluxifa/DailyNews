package com.lvmq.repository;

import org.springframework.data.repository.CrudRepository;

import com.lvmq.model.LikeLog;

public interface LikeCommentRepository extends CrudRepository<LikeLog,String> {
	int countByOutIdAndUserIdAndType(String outId,String userId,String type);
	
}
