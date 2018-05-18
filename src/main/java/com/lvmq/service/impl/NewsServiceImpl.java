package com.lvmq.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.lvmq.api.res.AdvertRes;
import com.lvmq.api.res.NewsByTypeRes;
import com.lvmq.api.res.NewsCommentArrayRes;
import com.lvmq.api.res.NewsCommentLevel2Res;
import com.lvmq.api.res.NewsCommentRes;
import com.lvmq.api.res.NewsInfoRes;
import com.lvmq.api.res.NewsRes;
import com.lvmq.api.res.NewsTypeArray;
import com.lvmq.api.res.NewsTypeRes;
import com.lvmq.base.Consts;
import com.lvmq.idata.IDataAPI;
import com.lvmq.idata.res.ToutiaoDataResponseDto;
import com.lvmq.idata.res.ToutiaoResponseDto;
import com.lvmq.model.AdvertInfo;
import com.lvmq.model.GoldLog;
import com.lvmq.model.LikeLog;
import com.lvmq.model.NewsComment;
import com.lvmq.model.NewsInfo;
import com.lvmq.model.NewsInfoRead;
import com.lvmq.model.NewsType;
import com.lvmq.model.ReadReward;
import com.lvmq.model.UserLogin;
import com.lvmq.repository.AdvertInfoRepository;
import com.lvmq.repository.GoldLogRepository;
import com.lvmq.repository.LikeCommentRepository;
import com.lvmq.repository.NewsCommentRepository;
import com.lvmq.repository.NewsInfoReadRepository;
import com.lvmq.repository.NewsInfoRepository;
import com.lvmq.repository.NewsTypeRepository;
import com.lvmq.repository.ReadRewardsRepository;
import com.lvmq.repository.UserLoginRepository;
import com.lvmq.service.NewsService;
import com.lvmq.util.TimeUtil;
import com.lvmq.util.Util;

@Component
public class NewsServiceImpl implements NewsService {
	
	private static final Logger log = LoggerFactory.getLogger(NewsServiceImpl.class);

	@Autowired
	private NewsInfoRepository newsInfoRepository;
	
	@Autowired
	private NewsTypeRepository newsTypeRepository;
	
	@Autowired
	private NewsInfoReadRepository newsInfoReadRepository;
	
	@Autowired
	private AdvertInfoRepository advertInfoRepository;
	
	@Autowired
	private NewsCommentRepository newsCommentRepository;

	@Autowired
	private UserLoginRepository userLoginRepository;
	
	@Autowired
	private GoldLogRepository goldLogRepository;
	
	@Autowired
	private ReadRewardsRepository readRewardsRepository;
	
	@Autowired
	private LikeCommentRepository likeCommentRepository;
	
	@Override
	public void getNewsFromIDataAPI() {
		// TODO Auto-generated method stub
		try {
			
			List<NewsType> newsTypeArray=newsTypeRepository.findAllByFlag(0);
			
			for(NewsType n: newsTypeArray) {
				try {
					String catId=n.getCatId();
					String url = "http://api01.bitspaceman.com:8000/news/toutiao?apikey=np5SpQ7QGzm7HgvX8Aw8APA5NDq6Bpj5m4eo4hX5qJFLm0G0Oqt31xJzjIEeJFTv&catid="+n.getCatId();
					String json = IDataAPI.getRequestFromUrl(url);
					log.info(json);
					Gson gson=new Gson();
					ToutiaoResponseDto toutiaoResponseDto=gson.fromJson(json, ToutiaoResponseDto.class);
					
					List<NewsInfo> newsInfoArray=new ArrayList<NewsInfo>();
					for(ToutiaoDataResponseDto toutiao :toutiaoResponseDto.getData()) {
						newsInfoArray.add(new NewsInfo(toutiao,catId));
					}
					List<NewsInfo> iter=(List<NewsInfo>) newsInfoRepository.saveAll(newsInfoArray);
				}catch(Exception e) {
					continue;
				}
			}
			
		}catch(Exception e) {
			log.info(e.getMessage());
		}
	}
	
	
	public NewsTypeArray types() {
		List<NewsType> newsTypeArray=newsTypeRepository.findAllByFlag(0);
		List<NewsTypeRes> typeArray=new ArrayList<NewsTypeRes>();
		newsTypeArray.forEach(x->typeArray.add(new NewsTypeRes(x.getCatId(),x.getCatValue())));
		return new NewsTypeArray(typeArray);
	}
	
	
	public NewsRes home(String userId,int page,int pageSize,String catId,int adPage,int adPageSize){
		try {
//			List<NewsType> newsTypeArray=newsTypeRepository.findAllByFlag(0);
//			
			List<NewsByTypeRes> newsByTypeArray=new ArrayList<NewsByTypeRes>();
//			
//			for(NewsType n:newsTypeArray) {
				
				List<NewsInfoRes> newsInfoResArray=new ArrayList<NewsInfoRes>();
				
				List<NewsInfo> newsInfoArray=newsInfoRepository.findByCatId(com.lvmq.util.PagePlugin.pagePluginSort(page, pageSize,Direction.DESC, "publishDate"),catId);
				
				newsInfoArray.forEach(x->newsInfoResArray.add(new NewsInfoRes(x,newsInfoReadRepository.countByuserIdAndNewsId(userId,x.getId()))));
				
				newsByTypeArray.add(new NewsByTypeRes(newsInfoResArray));
//			}
			
			List<AdvertInfo> advertInfo=advertInfoRepository.findByFlag(com.lvmq.util.PagePlugin.pagePluginSort(adPage, adPageSize,Direction.DESC, "createTime"),0);
			
			List<AdvertRes> ads=new ArrayList<AdvertRes>();
			for(AdvertInfo ai:advertInfo) {
				List<String> imgs=new ArrayList<String>();
				ai.getAdvertImgs().forEach(x->imgs.add(x.getImg()));
				ads.add(new AdvertRes(ai,imgs));
			}
			
			return new NewsRes(newsByTypeArray,ads);
		}catch (Exception e) {
			// TODO: handle exception
			log.info(e.getMessage());
			return new NewsRes();
		}
	}
	
	public NewsCommentArrayRes getComment(String newsId,String userId,int page,int pageSize,int pageLevel2,int pageSizeLevel2) {
		
		List<NewsComment> newsComments=newsCommentRepository.findByNewsIdAndFlagAndLevel(com.lvmq.util.PagePlugin.pagePluginSort(page, pageSize,Direction.DESC, "createTime") , newsId, 0, 1);
		List<NewsCommentRes> newsCommentRes=new ArrayList<NewsCommentRes>();
		for(NewsComment necs:newsComments) {
			
			String ilike="false";
			if(Util.isBlank(userId)) {
				if(likeCommentRepository.countByOutIdAndUserIdAndType(newsId, userId, Consts.LikeLog.Type.COMMENT)>0) {
					ilike="true";
				}
				
			}
			
			List<NewsComment> newsCommentLevel2=newsCommentRepository.findByParentIdAndFlag(com.lvmq.util.PagePlugin.pagePluginSort(pageLevel2, pageSizeLevel2,Direction.DESC, "createTime"),necs.getId(),0);
			List<NewsCommentLevel2Res> comentLevel2=new ArrayList<NewsCommentLevel2Res>();
			newsCommentLevel2.forEach(x->comentLevel2.add(new NewsCommentLevel2Res(x.getUserLogin().getName(),x.getComment())));
			
			newsCommentRes.add(new NewsCommentRes(necs,comentLevel2,ilike));
		}
		
		return new NewsCommentArrayRes(newsCommentRes);
	}
	
	public NewsCommentRes setComment(String newsId,String userId,String parentId,String comment,String level) {
		
		NewsComment n=newsCommentRepository.saveAndFlush(new NewsComment(newsId,userId,parentId,level,comment));
		Optional<UserLogin> u=userLoginRepository.findById(userId);		
		return new NewsCommentRes(n,u.get().getHeadPortrait(),u.get().getName());
	}

	public boolean readNews(String userId,String newsId){
		try {
			int count=newsInfoReadRepository.countByuserIdAndNewsId(userId, newsId);
			if(count==0) {
				newsInfoReadRepository.save(new NewsInfoRead(userId,newsId,0));
			}
			return true;
		}catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	
	public boolean getReward(String newsId,String userId) {
		try {
			
			int readGoldCnt=goldLogRepository.countByTypeAndUserIdAndCreateTimeBetween(Consts.GoldLog.Type.READ, userId, TimeUtil.zeroForToday(), TimeUtil.twelveForToday());
			Optional<ReadReward> opt=readRewardsRepository.findById("1");
			ReadReward r=opt.get();
			int flag=0;
			if((r.getDailyCnt()-readGoldCnt)>0) {
				UserLogin u=userLoginRepository.findById(userId).get();
				GoldLog g=new GoldLog();
				g.setType(Consts.GoldLog.Type.READ);
				g.setOldNum(u.getGold());
				g.setNum(r.getGold());
				g.setNewNum(r.getGold()+u.getGold());
				g.setUserId(userId);
				g.setCreateUser(userId);
				g.setCreateTime(new Date());
				goldLogRepository.save(g);
				
				u.setGold(r.getGold()+u.getGold());
				userLoginRepository.save(u);
				flag=1;
			}
			Optional<NewsInfoRead> o=newsInfoReadRepository.findByNewsIdAndUserId(newsId, userId);
			if(o.isPresent()) {
				NewsInfoRead nr=o.get();
				nr.setFlag(1);
				newsInfoReadRepository.save(nr);
			}
			return true;
		}catch (Exception e) {
			// TODO: handle exception
			log.info(e.getMessage());
			return false;
		}
	}
	
	
	public boolean likeComment(String commentId,String userId) {
			if(likeCommentRepository.countByOutIdAndUserIdAndType(commentId, userId, Consts.LikeLog.Type.COMMENT)==0) {
				likeCommentRepository.save(new LikeLog(commentId,userId,Consts.LikeLog.Type.COMMENT));
				Optional<NewsComment> opt=newsCommentRepository.findById(commentId);
				if(opt.isPresent()) {
					Long likeCnt=opt.get().getLikeCnt()+1;
					NewsComment newsComment=opt.get();
					newsComment.setLikeCnt(likeCnt);
					newsCommentRepository.save(newsComment);
				}
				return true;
			}else {
				return false;
			}
		
	}
}