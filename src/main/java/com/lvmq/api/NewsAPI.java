package com.lvmq.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lvmq.api.res.NewsCommentArrayRes;
import com.lvmq.api.res.NewsCommentRes;
import com.lvmq.api.res.NewsRes;
import com.lvmq.api.res.NewsTypeArray;
import com.lvmq.api.res.base.ResponseBean;
import com.lvmq.base.Code;
import com.lvmq.model.NewsComment;
import com.lvmq.service.NewsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value = "/api/news", description = "新闻")
@RestController
@RequestMapping("/api/news")
public class NewsAPI {
	
	@Autowired
	private NewsService newsService;
	
	
	
	@ApiOperation(value = "新闻分类", notes = "")
	@RequestMapping(value="/types",method=RequestMethod.POST)
	public ResponseBean<NewsTypeArray> types() {
		return new ResponseBean<NewsTypeArray>(Code.SUCCESS, Code.SUCCESS_CODE, "成功", newsService.types());
	}
	

	@ApiOperation(value = "首页新闻列表", notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "userId", value = "用户编号", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "catId", value = "分类编号", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "page", value = "分页", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页数量", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "adPage", value = "广告分页", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "adPageSize", value = "广告分页每页数量", required = true, dataType = "String")
			})
	@RequestMapping(value="/home",method=RequestMethod.POST)
	public ResponseBean<NewsRes> home(String userId,String catId,String page,String pageSize,String adPage,String adPageSize) {
		return new ResponseBean<NewsRes>(Code.SUCCESS, Code.SUCCESS_CODE, "成功", newsService.home(userId,Integer.valueOf(page)
				,Integer.valueOf(pageSize),catId,Integer.valueOf(adPage),Integer.valueOf(adPageSize)));
	}
	
	
	
//	@RequestMapping(value="/getNewsFromIDataAPI",method=RequestMethod.POST)
//	public void getNewsFromIDataAPI(){
//		newsService.getNewsFromIDataAPI();
//	}
	
	
	@ApiOperation(value = "获得评论列表", notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "newsId", value = "新闻编号", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "page", value = "页数", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页条数", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "pageLevel2", value = "二级评论页数", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "pageSizeLevel2", value = "二级评论每页条数", required = true, dataType = "String")
			})
	@RequestMapping(value="/getComment",method=RequestMethod.POST)
	public ResponseBean<NewsCommentArrayRes> getComment(String newsId,String userId,String page,String pageSize,String pageLevel2,String pageSizeLevel2) {
	
		return new ResponseBean<NewsCommentArrayRes>(Code.SUCCESS,Code.SUCCESS_CODE,"成功",newsService.getComment(newsId,userId,
				 Integer.valueOf(page),Integer.valueOf( pageSize),Integer.valueOf( pageLevel2), Integer.valueOf(pageSizeLevel2)));
	}
	
	
	@ApiOperation(value = "添加评论", notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "newsId", value = "新闻编号", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "page", value = "页数", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "parentId", value = "父级评论（一级评论时写0, 二级则写上一级的id）", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "level", value = "评论等级（1:一级,2:二级）", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "comment", value = "评论", required = true, dataType = "String")
			})
	//第一层评论parentId=0
	@RequestMapping(value="/setComment",method=RequestMethod.POST)
	public ResponseBean<NewsCommentRes> setComment(String newsId,String userId,String parentId,String level,String comment) {
			 return new ResponseBean<NewsCommentRes>(Code.SUCCESS,Code.SUCCESS_CODE,"成功",newsService.setComment(newsId,userId,parentId,comment,level)); 
	}
	
//	@RequestMapping(value="/readNews",method=RequestMethod.POST)
//	public ResponseBean readNews(String userId,String newsId) {
//		
//	}
}
