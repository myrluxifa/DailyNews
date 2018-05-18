package com.lvmq.service;

import org.springframework.stereotype.Service;

import com.lvmq.api.res.NewsCommentArrayRes;
import com.lvmq.api.res.NewsCommentRes;
import com.lvmq.api.res.NewsRes;
import com.lvmq.api.res.NewsTypeArray;
import com.lvmq.model.NewsComment;

@Service
public interface NewsService {
	
	void getNewsFromIDataAPI();
	
	NewsTypeArray types();
	
	NewsRes home(String userId,int page,int pageSize,String catId,int adPage,int adPageSize);
	
	
	NewsCommentArrayRes getComment(String newsId,String userId,int page,int pageSize,int pageLevel2,int pageSizeLevel2);
	
	NewsCommentRes setComment(String newsId,String userId,String parentId,String comment,String level);
	
	
	boolean readNews(String userId,String newsId);

	
	boolean getReward(String newsId,String userId);
	
	 boolean likeComment(String newsId,String userId) ;
}
