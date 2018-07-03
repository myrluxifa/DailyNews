package com.lvmq.service;

import org.springframework.stereotype.Service;

import com.lvmq.api.res.NewsCommentArrayRes;
import com.lvmq.api.res.NewsCommentForDetailRes;
import com.lvmq.api.res.NewsCommentRes;
import com.lvmq.api.res.NewsInfoRes;
import com.lvmq.api.res.NewsRes;
import com.lvmq.api.res.NewsTypeArray;
import com.lvmq.api.res.RewardsRes;
import com.lvmq.model.NewsComment;

@Service
public interface NewsService {
	
	void getNewsFromIDataAPI();
	
	NewsTypeArray types();
	
	NewsRes home(String userId,int page,int pageSize,String catId,int adPage,int adPageSize);
	
	
	NewsCommentArrayRes getComment(String newsId,String userId,int page,int pageSize,int pageLevel2,int pageSizeLevel2);
	
	NewsCommentRes setComment(String newsId,String userId,String parentId,String comment,String level);
	
	
	boolean readNews(String userId,String newsId);

	
	boolean getReward(String newsId,String userId,String ifReadPackage);
	
	 boolean likeComment(String newsId,String userId) ;
	 
	 RewardsRes getRewardsCnt(String userId);
	 
	 NewsRes getWanderFulNews(String newsPageSize,String adPageSize);
	 
	 NewsCommentForDetailRes getCommentDetail(String commentId,String userId,int page,int pageSize);

	void getNewsFromIDataAPIByCatId(String catId);
}
