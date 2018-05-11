package com.lvmq.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lvmq.api.res.NewsCommentArrayRes;
import com.lvmq.api.res.NewsRes;
import com.lvmq.api.res.base.ResponseBean;
import com.lvmq.base.Code;
import com.lvmq.model.NewsComment;
import com.lvmq.service.NewsService;

@RestController
@RequestMapping("/api/news")
public class NewsAPI {
	
	@Autowired
	private NewsService newsService;

	@RequestMapping(value="/home",method=RequestMethod.POST)
	public ResponseBean<NewsRes> home(String userId) {
		return new ResponseBean<NewsRes>(Code.SUCCESS, Code.SUCCESS_CODE, "成功", newsService.home(userId));
	}
	
	
	@RequestMapping(value="/getNewsFromIDataAPI",method=RequestMethod.POST)
	public void getNewsFromIDataAPI(){
		newsService.getNewsFromIDataAPI();
	}
	
	@RequestMapping(value="/getComment",method=RequestMethod.POST)
	public ResponseBean<NewsCommentArrayRes> getComment(String newsId,String loginId,String page,String pageSize,String pageLevel2,String pageSizeLevel2) {
	
		return new ResponseBean<NewsCommentArrayRes>(Code.SUCCESS,Code.SUCCESS_CODE,"成功",newsService.getComment(newsId,
				loginId, Integer.valueOf(page),Integer.valueOf( pageSize),Integer.valueOf( pageLevel2), Integer.valueOf(pageSizeLevel2)));
	}
	
	//第一层评论parentId=0
	@RequestMapping(value="/setComment",method=RequestMethod.POST)
	public ResponseBean setComment(String newsId,String loginId,String parentId,String level,String comment) {
			 return new ResponseBean<>(Code.SUCCESS,Code.SUCCESS_CODE,"成功",newsService.setComment(newsId,loginId,parentId,comment,level)); 
	}
}
