package com.lvmq.service;

import org.springframework.stereotype.Service;

import com.lvmq.api.res.NewsCommentArrayRes;
import com.lvmq.api.res.NewsCommentRes;
import com.lvmq.api.res.NewsRes;
import com.lvmq.model.NewsComment;

@Service
public interface NewsService {
	
	void getNewsFromIDataAPI();
	
	NewsRes home(String userId);
	
	
	NewsCommentArrayRes getComment(String newsId,String userId,int page,int pageSize,int pageLevel2,int pageSizeLevel2);
	
	NewsCommentRes setComment(String newsId,String userId,String parentId,String comment,String level);

}
