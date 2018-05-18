package com.lvmq.repository;

import com.lvmq.model.LikeLog;
import com.lvmq.repository.base.BaseRepository;

public interface LikeCommentRepository extends BaseRepository<LikeLog> {
	int countByOutIdAndUserIdAndType(String outId,String userId,String type);
	
}
