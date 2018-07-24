package com.lvmq.api;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lvmq.api.res.NewsCommentArrayRes;
import com.lvmq.api.res.NewsCommentForDetailRes;
import com.lvmq.api.res.NewsCommentRes;
import com.lvmq.api.res.NewsInfoRes;
import com.lvmq.api.res.NewsRes;
import com.lvmq.api.res.NewsTypeArray;
import com.lvmq.api.res.RewardsRes;
import com.lvmq.api.res.VideosArrayRes;
import com.lvmq.api.res.base.ResponseBean;
import com.lvmq.base.Code;
import com.lvmq.base.Consts;
import com.lvmq.model.DayMission;
import com.lvmq.model.NewsComment;
import com.lvmq.model.NewsInfo;
import com.lvmq.model.NewsRemoveLog;
import com.lvmq.model.UserLogin;
import com.lvmq.repository.NewsInfoRepository;
import com.lvmq.service.DayMissionService;
import com.lvmq.service.NewsRemoveService;
import com.lvmq.service.NewsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


@Api(tags = {"新闻"})
@RestController
@RequestMapping("/api/news")
public class NewsAPI extends BaseAPI {
	
	@Autowired
	private NewsService newsService;
	
	@Autowired
	private NewsInfoRepository newsInfoRepository;
	
	@Autowired
	private DayMissionService dayMissionService;
	
	@Autowired
	private NewsRemoveService newsRemoveService;
	
	private static boolean freshKeyWordsTag = true;
	private static long lastFreshTime = Calendar.getInstance().getTimeInMillis();
	private static List<String> keywords = new ArrayList<>();
	
	@Scheduled(cron = "0 0 0/2 * * ?")
	public void freshKeyWords() {
		if(freshKeyWordsTag) {
			freshKeywords();
		}
	}
	
	@Autowired
	public void freshKeywords() {
		List<String> nis = newsInfoRepository.findTop100TitleOrderByPublishDateDesc();
		keywords.clear();
		Random random = new Random();
		for (int i = 0; i < nis.size(); i++) {
			if(i > 9) break;
			keywords.add(nis.get(random.nextInt(nis.size())));
		}
		freshKeyWordsTag = false;
	}
	
	@Scheduled(cron = "0 0 * * * ?")
	public void resetFreshTag() {
		if(!freshKeyWordsTag && Calendar.getInstance().getTimeInMillis() - lastFreshTime > 2 * 60 * 60 * 1000) {
			freshKeyWordsTag = true;
		}
	}
	
	@ApiOperation(value = "热词", notes = "")
	@PostMapping("keywords")
	public ResponseBean<Object> keywords() {
		try {
			return new ResponseBean<Object>(Code.SUCCESS, Code.SUCCESS, "成功", keywords);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ResponseBean<Object>(Code.FAIL, Code.FAIL, e.getMessage());
		}
	}
	
	@ApiOperation(value = "下一组热词", notes = "")
	@PostMapping("nextkeywords")
	public ResponseBean<Object> nextkeywords() {
		try {
			freshKeywords();
			return new ResponseBean<Object>(Code.SUCCESS, Code.SUCCESS, "成功", keywords);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ResponseBean<Object>(Code.FAIL, Code.FAIL, e.getMessage());
		}
	}
	
	@ApiOperation(value = "新闻分类", notes = "")
	@RequestMapping(value="/types",method=RequestMethod.POST)
	public ResponseBean<NewsTypeArray> types() {
		return new ResponseBean<NewsTypeArray>(Code.SUCCESS, Code.SUCCESS_CODE, "成功", newsService.types());
	}
	

	@ApiOperation(value = "首页新闻列表", notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "userId", value = "用户编号", required = false, dataType = "String"),
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
//	
//	@RequestMapping(value="/getNewsFromIDataAPIByCatId",method=RequestMethod.POST)
//	public void getNewsFromIDataAPIByCatId(String catId){
//		newsService.getNewsFromIDataAPIByCatId(catId);
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
	
	
	@ApiOperation(value = "评论详情", notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "commentId", value = "评论编号", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "userId", value = "用户编号", required = false, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "page", value = "页数", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页条数", required = true, dataType = "String")
			})
	@RequestMapping(value="/getCommentDetail",method=RequestMethod.POST)
	public ResponseBean<NewsCommentForDetailRes> getCommentDetail(String commentId,String userId,String page,String pageSize){
		return new ResponseBean<NewsCommentForDetailRes>(Code.SUCCESS,Code.SUCCESS_CODE,"成功",newsService.getCommentDetail(commentId, userId, Integer.valueOf(page), Integer.valueOf(pageSize)));
	}
	
	@ApiOperation(value = "添加评论", notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "newsId", value = "新闻编号", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "parentId", value = "父级评论（一级评论时写0, 二级则写上一级的id）", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "level", value = "评论等级（1:一级,2:二级）", required = true, dataType = "String"),
			@ApiImplicitParam(paramType = "query", name = "comment", value = "评论", required = true, dataType = "String")
			})
	//第一层评论parentId=0
	@RequestMapping(value="/setComment",method=RequestMethod.POST)
	public ResponseBean<NewsCommentRes> setComment(String newsId,String userId,String parentId,String level,String comment) {
		
		// 日常任务 评论奖励
		DayMission dm = dayMissionService.updateDayMission(userId, Consts.DayMission.Type.COMMENT);
			 return new ResponseBean<NewsCommentRes>(Code.SUCCESS,Code.SUCCESS_CODE,"成功",newsService.setComment(newsId,userId,parentId,comment,level)); 
	}
	
	
	
	
	@ApiOperation(value="点赞",notes="")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "commentId", value = "评论编号", required = true, dataType = "String"),
		@ApiImplicitParam(paramType = "query", name = "userId", value = "用户编号", required = true, dataType = "String")
	})
	@RequestMapping(value="/likeComment",method=RequestMethod.POST)
	public ResponseBean likeComment(String commentId,String userId){
		if(newsService.likeComment(commentId, userId)) {
			return new ResponseBean(Code.SUCCESS,Code.SUCCESS_CODE,"成功");
		}else {
			return new ResponseBean(Code.FAIL,Code.LIKE_FAIL,"已点赞");
		}
	}
	
	
	
	@ApiOperation(value="当前奖励数量",notes="")
	@RequestMapping(value="/getRewardsCnt",method=RequestMethod.POST)
	public ResponseBean getRewardsCnt(String userId) {
		try {
			return new ResponseBean(Code.SUCCESS, Code.SUCCESS_CODE, "成功",newsService.getRewardsCnt(userId));
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseBean(Code.FAIL,Code.REWARDS_CNT_FAIL,"失败",new RewardsRes("0","0","0"));
		}
		
	}
	
	
	@ApiOperation(value="阅读新闻",notes="")
	@RequestMapping(value="/readNews",method=RequestMethod.POST)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "newsId", value = "新闻编号", required = true, dataType = "String"),
		@ApiImplicitParam(paramType = "query", name = "userId", value = "登陆人编号", required = true, dataType = "String")
		})
	public ResponseBean readNews(String userId,String newsId) {
		
		// 日常任务 阅读奖励
		DayMission dm = dayMissionService.updateDayMission(userId, Consts.DayMission.Type.READ);
		
		if(newsService.readNews(userId, newsId)) {
			return new ResponseBean(Code.SUCCESS, Code.SUCCESS_CODE, "成功");
		}else {
			return new ResponseBean(Code.FAIL,Code.READ_CODE_FAIL,"失败");
		}
	}
	
	@ApiOperation(value="获得奖励",notes="")
	@RequestMapping(value="/getReward",method=RequestMethod.POST)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "newsId", value = "新闻编号", required = true, dataType = "String"),
		@ApiImplicitParam(paramType = "query", name = "userId", value = "登陆人编号", required = true, dataType = "String"),
		@ApiImplicitParam(paramType = "query", name = "ifReadPackage", value = "是否是红包新闻", required = true, dataType = "String")
		})
	public ResponseBean getReward(String userId,String newsId,String ifReadPackage) {
		if(newsService.getReward(newsId, userId,ifReadPackage)) {
			return new ResponseBean(Code.SUCCESS, Code.SUCCESS_CODE, "成功");
		}else {
			return new ResponseBean(Code.FAIL,Code.READ_CODE_FAIL,"失败");
		}
	}
	

	
	@ApiOperation(value = "新闻推荐", notes = "")
	@RequestMapping(value="/getWonderfulNews",method=RequestMethod.POST)
	public ResponseBean<NewsRes> getWonderfulNews(String newsPageSize,String adPageSize) {
		return new ResponseBean<NewsRes>(Code.SUCCESS, Code.SUCCESS_CODE, "成功", newsService.getWanderFulNews(newsPageSize,adPageSize));
	}
	
	
	@ApiOperation(value = "删除新闻", notes = "")
	@RequestMapping(value="/removeNews",method=RequestMethod.POST)
	public ResponseBean removeNews(String id,String userId) {
		if(newsRemoveService.removeNews(id, userId)==true) {
			return new ResponseBean(Code.SUCCESS, Code.SUCCESS_CODE, "成功");
		}else {
			return new ResponseBean(Code.FAIL, Code.FAIL, "失败");
		}
	}
	
	
}
