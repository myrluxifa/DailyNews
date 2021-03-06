package com.lvmq.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.lvmq.api.NewerMissionAPI;
import com.lvmq.api.res.AdvertRes;
import com.lvmq.api.res.NewsByTypeRes;
import com.lvmq.api.res.NewsCommentArrayRes;
import com.lvmq.api.res.NewsCommentForDetailRes;
import com.lvmq.api.res.NewsCommentLevel2Res;
import com.lvmq.api.res.NewsCommentLevel2ResForDetail;
import com.lvmq.api.res.NewsCommentRes;
import com.lvmq.api.res.NewsInfoRes;
import com.lvmq.api.res.NewsRes;
import com.lvmq.api.res.NewsTypeArray;
import com.lvmq.api.res.NewsTypeRes;
import com.lvmq.api.res.ReadPackageNews;
import com.lvmq.api.res.RewardsRes;
import com.lvmq.api.res.VideosRes;
import com.lvmq.base.Consts;
import com.lvmq.idata.IDataAPI;
import com.lvmq.idata.res.ToutiaoDataResponseDto;
import com.lvmq.idata.res.ToutiaoResponseDto;
import com.lvmq.idata.res.VideosDataResponseDto;
import com.lvmq.idata.res.VideosResponseDto;
import com.lvmq.model.AdvertInfo;
import com.lvmq.model.BalanceLog;
import com.lvmq.model.GoldLog;
import com.lvmq.model.GoldRewards;
import com.lvmq.model.LikeLog;
import com.lvmq.model.MessageCode;
import com.lvmq.model.NewsComment;
import com.lvmq.model.NewsInfo;
import com.lvmq.model.NewsInfoRead;
import com.lvmq.model.NewsType;
import com.lvmq.model.ReadReward;
import com.lvmq.model.RecallLog;
import com.lvmq.model.UserLogin;
import com.lvmq.model.VideosInfo;
import com.lvmq.repository.AdvertInfoRepository;
import com.lvmq.repository.BalanceLogRepository;
import com.lvmq.repository.GoldLogRepository;
import com.lvmq.repository.GoldRewardsRepository;
import com.lvmq.repository.LikeCommentRepository;
import com.lvmq.repository.NewsCommentRepository;
import com.lvmq.repository.NewsInfoReadRepository;
import com.lvmq.repository.NewsInfoRepository;
import com.lvmq.repository.NewsTypeRepository;
import com.lvmq.repository.ReadRewardsRepository;
import com.lvmq.repository.RecallLogRepository;
import com.lvmq.repository.UserLoginRepository;
import com.lvmq.service.NewsService;
import com.lvmq.util.NumberUtils;
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
	
	@Autowired
	private GoldRewardsRepository goldRewardsRepository;
	
	@Autowired
	private BalanceLogRepository balanceLogRepository;
	
	@Autowired
	private RecallLogRepository recallLogRepository;
	
	@Autowired
	private NewerMissionAPI newerMission;
	
	
	@Scheduled(cron="0 0 9 * * ? ")
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
					try {
						List<NewsInfo> iter=(List<NewsInfo>) newsInfoRepository.saveAll(newsInfoArray);
					}catch (Exception e) {
						// TODO: handle exception
						log.info(e.getMessage());
						continue;
					}
					
					if("true".equals(toutiaoResponseDto.getHasNext())) {
						getNewsFromIDataAPIByPageToken(toutiaoResponseDto.getPageToken(),catId);
						
					}
				}catch(Exception e) {
					continue;
				}
			}
			
		}catch(Exception e) {
			log.info(e.getMessage());
		}
	}
	
	
	public void getNewsFromIDataAPIByPageToken(String pageToken,String catId) {
		// TODO Auto-generated method stub
				
					
					
						String url = "http://api01.bitspaceman.com:8000/news/toutiao?apikey=np5SpQ7QGzm7HgvX8Aw8APA5NDq6Bpj5m4eo4hX5qJFLm0G0Oqt31xJzjIEeJFTv&catid="+catId+"&pageToken="+pageToken;
						String json = IDataAPI.getRequestFromUrl(url);
						log.info(json);
						Gson gson=new Gson();
						
						ToutiaoResponseDto toutiaoResponseDto=gson.fromJson(json, ToutiaoResponseDto.class);
						List<NewsInfo> newsInfoArray=new ArrayList<NewsInfo>();
						for(ToutiaoDataResponseDto toutiao :toutiaoResponseDto.getData()) {
							newsInfoArray.add(new NewsInfo(toutiao,catId));
						}
						try {
							List<NewsInfo> iter=(List<NewsInfo>) newsInfoRepository.saveAll(newsInfoArray);
						}catch(Exception e) {
							log.info(e.getMessage());
							return;
						}
						if("true".equals(toutiaoResponseDto.getHasNext())) {
							getNewsFromIDataAPIByPageToken2(toutiaoResponseDto.getPageToken(),catId);
						}
						//不能批量添加还需要判断视频重复问题
						//videosInfoRepository.saveAll(videosInfoArray);
						
						
	}
	
	public void getNewsFromIDataAPIByPageToken2(String pageToken,String catId) {
		// TODO Auto-generated method stub
					
					
					String url = "http://api01.bitspaceman.com:8000/news/toutiao?apikey=np5SpQ7QGzm7HgvX8Aw8APA5NDq6Bpj5m4eo4hX5qJFLm0G0Oqt31xJzjIEeJFTv&catid="+catId+"&pageToken="+pageToken;
					String json = IDataAPI.getRequestFromUrl(url);
					log.info(json);
					Gson gson=new Gson();
					
					ToutiaoResponseDto toutiaoResponseDto=gson.fromJson(json, ToutiaoResponseDto.class);
					List<NewsInfo> newsInfoArray=new ArrayList<NewsInfo>();
					for(ToutiaoDataResponseDto toutiao :toutiaoResponseDto.getData()) {
						newsInfoArray.add(new NewsInfo(toutiao,catId));
					}
					try {
					List<NewsInfo> iter=(List<NewsInfo>) newsInfoRepository.saveAll(newsInfoArray);
					}catch(Exception e) {
						log.info(e.getMessage());
						return;
					}
					if("true".equals(toutiaoResponseDto.getHasNext())) {
						getNewsFromIDataAPIByPageToken(toutiaoResponseDto.getPageToken(),catId);
					}
						
	}
	
	
	
	@Override
	public void getNewsFromIDataAPIByCatId(String catId) {
		// TODO Auto-generated method stub
		try {
			
					String url = "http://api01.bitspaceman.com:8000/news/toutiao?apikey=np5SpQ7QGzm7HgvX8Aw8APA5NDq6Bpj5m4eo4hX5qJFLm0G0Oqt31xJzjIEeJFTv&catid="+catId;
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
			System.out.println(e.getMessage());
			log.info(e.getMessage());
		}
	}
	
	
	public NewsTypeArray types() {
		List<NewsType> newsTypeArray=newsTypeRepository.findAllByFlag(0);
		List<NewsTypeRes> typeArray=new ArrayList<NewsTypeRes>();
		newsTypeArray.forEach(x->typeArray.add(new NewsTypeRes(x.getCatId(),x.getCatValue())));
		return new NewsTypeArray(typeArray);
	}
	
	
	public static Map<String,ReadPackageNews> redPackageNews=new HashMap<String,ReadPackageNews>();
	
	public NewsRes home(String userId,int page,int pageSize,String catId,int adPage,int adPageSize){
		
		try {
			int redPackagecnt=0;
			
			//取出每小时随机红包次数
			Optional<ReadReward> opt=readRewardsRepository.findById("1");
			ReadReward r=opt.get();
			
			//加载过程中是否需要分配新的红包
			Boolean needNewRedPackage=false;
			
			Boolean needMoreRedPackage=false;
			
			int needMoreRedPackageNum=0;
			
			try {
			if(!Util.isBlank(userId)) {
				
				
				
				
				//登录奖励
				if(goldLogRepository.countByTypeAndUserIdAndCreateTimeBetween(Consts.GoldLog.Type.LOGIN, userId,TimeUtil.zeroForToday(), TimeUtil.twelveForToday())==0) {
					Optional<UserLogin> ou=userLoginRepository.findById(userId);
					if(ou.isPresent()) {
						UserLogin u=ou.get();
						long gold=Long.valueOf(goldRewardsRepository.findByType(Consts.GoldLog.Type.LOGIN).getGold());
						GoldLog goldLog=new GoldLog();
						goldLog.setNum(gold);
						goldLog.setNewNum(gold+u.getGold());
						goldLog.setOldNum(u.getGold());
						goldLog.setType(Consts.GoldLog.Type.LOGIN);
						goldLog.setUserId(userId);
						goldLog.setCreateUser(userId);
						goldLog.setCreateTime(new Date());
						goldLogRepository.save(goldLog);
						u.setGold(gold+u.getGold());
						userLoginRepository.save(u);
					}
				}
				
				//是否被召回
				if(recallLogRepository.countByRecallUserAndFlagAndCreateTimeBetween(userId, 0,TimeUtil.getHistoryDay(new Date(), 3), new Date())>0) {
					List<RecallLog> recallLogArray=recallLogRepository.findByRecallUserAndFlagAndCreateTimeBetween(com.lvmq.util.PagePlugin.pagePluginSort(0, 1,Direction.DESC, "createTime"), userId,0, TimeUtil.getHistoryDay(new Date(), 3), new Date());
					String masterId=recallLogArray.get(0).getUserId();
					//师傅获得奖励
					Optional<UserLogin> master=userLoginRepository.findById(masterId);
					if(master.isPresent()) {
						UserLogin u=master.get();
						long gold=Long.valueOf(goldRewardsRepository.findByType(Consts.GoldLog.Type.RECALL).getGold());
						GoldLog goldLog=new GoldLog();
						goldLog.setNum(gold);
						goldLog.setNewNum(gold+u.getGold());
						goldLog.setOldNum(u.getGold());
						goldLog.setType(Consts.GoldLog.Type.RECALL);
						goldLog.setUserId(userId);
						goldLog.setCreateUser(userId);
						goldLog.setCreateTime(new Date());
						goldLogRepository.save(goldLog);
						u.setGold(gold+u.getGold());
						userLoginRepository.save(u);
					}
					
					//师傅获得奖励
					Optional<UserLogin> us=userLoginRepository.findById(userId);
					if(us.isPresent()) {
						UserLogin u=us.get();
						long gold=Long.valueOf(goldRewardsRepository.findByType(Consts.GoldLog.Type.RECALL_BACK).getGold());
						GoldLog goldLog=new GoldLog();
						goldLog.setNum(gold);
						goldLog.setNewNum(gold+u.getGold());
						goldLog.setOldNum(u.getGold());
						goldLog.setType(Consts.GoldLog.Type.RECALL_BACK);
						goldLog.setUserId(userId);
						goldLog.setCreateUser(userId);
						goldLog.setCreateTime(new Date());
						goldLogRepository.save(goldLog);
						u.setGold(gold+u.getGold());
						userLoginRepository.save(u);
					}
					
					recallLogRepository.updateByRecallUser(userId);
				}
				
				int cnt=r.getHorCnt();
				
				SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				int count=balanceLogRepository.countByTypeAndUserIdAndCreateTimeBetween(Consts.BalanceLog.Type.RED_PACKAGE_BY_READ, userId, TimeUtil.zeroForHour(), TimeUtil.twelveForHour());
				if(cnt>count) {
					redPackagecnt=cnt-count;
				}
				
				int alreadyRedPackageNum=0;
				
				if(redPackageNews.containsKey(userId)) {
					//取出用户当前时间段的红包新闻
					ReadPackageNews rp=redPackageNews.get(userId);
					Date d=new Date(rp.getTime());
					
					if(d.getHours()!=new Date().getHours()) {
						//如果所在小时不同则清除红包集合
						redPackageNews.remove(userId);
						needNewRedPackage=true;
					}
					//取出当前红包新闻数量
					alreadyRedPackageNum=rp.getIds().size();
					if(redPackagecnt>alreadyRedPackageNum) {
						needMoreRedPackage=true;
						needMoreRedPackageNum=redPackagecnt-alreadyRedPackageNum;
					}
					
				}else {
					needNewRedPackage=true;
					needMoreRedPackage=true;
					needMoreRedPackageNum=redPackagecnt;
				}
				
				
			}
			}catch (Exception e) {
				// TODO: handle exception
				log.info(e.getMessage());
			}
			
				List<NewsByTypeRes> newsByTypeArray=new ArrayList<NewsByTypeRes>();
				
				List<NewsInfoRes> newsInfoResArray=new ArrayList<NewsInfoRes>();
				List<NewsInfo> newsInfoArray=new ArrayList<NewsInfo>();
				if(Util.isBlank(userId)) {
					newsInfoArray=newsInfoRepository.findByCatId(com.lvmq.util.PagePlugin.pagePluginSort(page, pageSize,Direction.DESC, "publishDate"),catId);

				}else {
					newsInfoArray=newsInfoRepository.findByCatIdByNativeQuery(userId, catId,Integer.valueOf(page) * Integer.valueOf(pageSize), pageSize);

				}
				
				
				for(NewsInfo newsInfo: newsInfoArray) {
					
					//需要从新分配红包奖励
					String redPackage="0";
					String redMoney="0";
					if(needNewRedPackage==true) {
						if(Util.isBlank(userId)==false) {
							if(newsInfoReadRepository.countByuserIdAndNewsId(userId,newsInfo.getId())==0&&needMoreRedPackageNum>0) {
								if(Util.trueOrFalse()) {
									redPackage="1";
									redMoney=r.getHorMoney();
									
									if(!redPackageNews.containsKey(userId)) {
										List<String> ids=new ArrayList<String> ();
										ids.add(newsInfo.getId());
										ReadPackageNews rpn=new ReadPackageNews(ids, new Date().getTime());
										redPackageNews.put(userId, rpn);
									}else {
										ReadPackageNews rpn=redPackageNews.get(userId);
										rpn.getIds().add(newsInfo.getId());
										rpn.setTime(new Date().getTime());
										redPackageNews.put(userId, rpn);
									}
									
									needMoreRedPackageNum=needMoreRedPackageNum-1;
									
								}
							}
						}
						
					}else {
						if(needMoreRedPackage==true) {
							if(Util.isBlank(userId)==false) {
								if(newsInfoReadRepository.countByuserIdAndNewsId(userId,newsInfo.getId())==0&&needMoreRedPackageNum>0) {
									if(Util.trueOrFalse()) {
										redPackage="1";
										redMoney=r.getHorMoney();
										redPackageNews.get(userId).getIds().add(newsInfo.getId());
										needMoreRedPackageNum=needMoreRedPackageNum-1;
										redPackageNews.get(userId).setTime(new Date().getTime());
										
									}
								}
							}
						}else {
							if(Util.isBlank(userId)==false) {
								if(redPackageNews.containsKey(userId)&&newsInfoReadRepository.countByuserIdAndNewsId(userId,newsInfo.getId())==0) {
									ReadPackageNews rpns=redPackageNews.get(userId);
									if(rpns.getIds().contains(newsInfo.getId())) {
										redPackage="1";
										redMoney=r.getHorMoney();
									}
								}
							}
						}
					}
					
					
					newsInfoResArray.add(new NewsInfoRes(newsInfo,redPackage,redMoney,Util.isBlank(userId)==true?0:newsInfoReadRepository.countByuserIdAndNewsId(userId,newsInfo.getId())));
				}
				
				
				
			newsByTypeArray.add(new NewsByTypeRes(newsInfoResArray));
			
			List<AdvertInfo> advertInfo=advertInfoRepository.findByFlag(com.lvmq.util.PagePlugin.pagePluginSort(adPage, adPageSize,Direction.DESC, "createTime"),0);
			
			List<AdvertRes> ads=new ArrayList<AdvertRes>();
			for(AdvertInfo ai:advertInfo) {
				List<String> imgs=new ArrayList<String>();
				ai.getAdvertImgs().forEach(x->imgs.add(x.getImg()));
				String adType="0";
				if(imgs.size()>3) {
					adType="3";
				}else if(imgs.size()==0) {
					adType="0";
				}else {
					adType="1";
				}
				ads.add(new AdvertRes(ai,imgs,adType));
				
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
			if(!Util.isBlank(userId)) {
				if(likeCommentRepository.countByOutIdAndUserIdAndType(necs.getId(), userId, Consts.LikeLog.Type.COMMENT)>0) {
					ilike="true";
				}
				
			}
			
			List<NewsComment> newsCommentLevel2=newsCommentRepository.findByParentIdAndFlag(com.lvmq.util.PagePlugin.pagePluginSort(pageLevel2, pageSizeLevel2,Direction.DESC, "createTime"),necs.getId(),0);
			List<NewsCommentLevel2Res> comentLevel2=new ArrayList<NewsCommentLevel2Res>();
			newsCommentLevel2.forEach(x->comentLevel2.add(new NewsCommentLevel2Res(x.getUserLogin().getName(),x.getComment(),x.getUserLogin().getUserName())));
			
			newsCommentRes.add(new NewsCommentRes(necs,comentLevel2,ilike));
		}
		
		return new NewsCommentArrayRes(newsCommentRes);
	}
	
	public NewsCommentForDetailRes getCommentDetail(String commentId,String userId,int page,int pageSize) {
		
		SimpleDateFormat formt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Optional<NewsComment> newsComment=newsCommentRepository.findById(commentId);
			
			String ilike="false";
			if(!Util.isBlank(userId)) {
				if(likeCommentRepository.countByOutIdAndUserIdAndType(commentId, userId, Consts.LikeLog.Type.COMMENT)>0) {
					ilike="true";
				}
			}
			
			
			List<NewsComment> newsCommentLevel2=newsCommentRepository.findByParentIdAndFlag(com.lvmq.util.PagePlugin.pagePluginSort(page, pageSize,Direction.DESC, "createTime"),commentId,0);
			List<NewsCommentLevel2ResForDetail> comentLevel2=new ArrayList<NewsCommentLevel2ResForDetail>();
			newsCommentLevel2.forEach(x->comentLevel2.add(new NewsCommentLevel2ResForDetail(x.getUserLogin().getName(),x.getComment(),x.getUserLogin().getUserName(),x.getUserLogin().getHeadPortrait(),formt.format(x.getUserLogin().getCreateTime()))));
			
		return new NewsCommentForDetailRes(newsComment.get(),comentLevel2,ilike);
		
	}
	
	public NewsCommentRes setComment(String newsId,String userId,String parentId,String comment,String level) {
		
		NewsComment n=newsCommentRepository.saveAndFlush(new NewsComment(newsId,userId,parentId,level,comment));
		Optional<UserLogin> u=userLoginRepository.findById(userId);		
		return new NewsCommentRes(n,u.get().getHeadPortrait(),u.get().getName(),u.get().getUserName());
	}

	public boolean readNews(String userId,String newsId){
		try {
			int count=newsInfoReadRepository.countByuserIdAndNewsId(userId, newsId);
			if(count==0) {
				newsInfoReadRepository.save(new NewsInfoRead(userId,newsId,0));
				Optional<NewsInfo> newsinfo = newsInfoRepository.findById(newsId);
				NewsInfo ni = newsinfo.get();
				ni.setViewCount(ni.getViewCount() + 1);
				newsInfoRepository.save(ni);
			}
			return true;
		}catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	
	@Transactional
	public boolean getReward(String newsId,String userId,String ifReadPackage) {
		try {
			Optional<NewsInfoRead> o=newsInfoReadRepository.findByNewsIdAndUserId(newsId, userId);
			if(o.isPresent()) {
				if(o.get().getFlag()==1) {
					return false;
				}
			}else {
				return false;
			}
			
			//当天阅读奖励
			int readGoldCnt=goldLogRepository.countByTypeAndUserIdAndCreateTimeBetween(Consts.GoldLog.Type.READ, userId, TimeUtil.zeroForToday(), TimeUtil.twelveForToday());
			//配置的阅读奖励 条数 和 奖励金币数量
			Optional<ReadReward> opt=readRewardsRepository.findById("1");
			ReadReward r=opt.get();
			int flag=0;
			
			
			
			int redCount=balanceLogRepository.countByTypeAndUserIdAndCreateTimeBetween(Consts.BalanceLog.Type.RED_PACKAGE_BY_READ, userId, TimeUtil.zeroForHour(), TimeUtil.twelveForHour());
			
			if((r.getHorCnt()-redCount)>0&&ifReadPackage.equals("1")) {
				UserLogin u=userLoginRepository.findById(userId).get();
				BalanceLog b=new BalanceLog();
				b.setType(Consts.BalanceLog.Type.RED_PACKAGE_BY_READ);
				b.setOldNum(u.getBalance());
				b.setNum(r.getHorMoney());
				b.setNewNum(NumberUtils.feeFormat(Double.parseDouble(u.getBalance())+Double.parseDouble(r.getHorMoney())));
				b.setUserId(userId);
				b.setCreateUser(userId);
				b.setCreateTime(new Date());
				balanceLogRepository.save(b);
				u.setBalance(NumberUtils.feeFormat(Double.parseDouble(u.getBalance())+Double.parseDouble(r.getHorMoney())));
				userLoginRepository.save(u);
			}else {
			
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
				//判断是否有师傅
				if(!Util.isBlank(u.getInviteCode())) {
					//师傅
					UserLogin masterUser=userLoginRepository.findByMyInviteCode(u.getInviteCode());
					if(masterUser!=null) {
						Date today=new Date();
						//注册与当前相差天数
						Long day=(today.getTime()-u.getCreateTime().getTime())/(1000*3600*24);
						
						SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						
						//判断注册时间
						if(day<8) {
							//判断当天获得阅读奖励是否超过50
							if(goldLogRepository.sumNumByTypeAndUserIdAndCreateTimeBetween(Consts.GoldLog.Type.READ, userId, dateFormat.format(TimeUtil.zeroForToday()), dateFormat.format(TimeUtil.twelveForToday()))>=50) {
								log.info(userId);
								log.info(masterUser.getId());
								log.info(dateFormat.format(TimeUtil.zeroForToday()));
								log.info(dateFormat.format(TimeUtil.twelveForToday()));
								//判断当天奖励是否已经领取过了
								if(balanceLogRepository.countByTypeAndUserIdAndTriggerUserIdAndCreateTimeBetween(Consts.BalanceLog.Type.EIGHT_DAY_REWARDS, masterUser.getId(),userId, TimeUtil.zeroForToday(), TimeUtil.twelveForToday())==0) {
									//如果在奖励范围  触发奖励机制
									String[] rewards=goldRewardsRepository.findByType(Consts.BalanceLog.Type.EIGHT_DAY_REWARDS).getMoney().split(",");
									int count=balanceLogRepository.countByTypeAndUserIdAndTriggerUserIdAndCreateTimeBetween(Consts.BalanceLog.Type.EIGHT_DAY_REWARDS, masterUser.getId(),u.getId(), u.getCreateTime(), today);
									
									String rewardsMoney="0.00";
									
									//如果注册天数应得次数-当前满足条件次数大于0则证明其为断签状态  那么则取第一天签到的奖励金额
									if((day+1-count)>0) rewardsMoney=rewards[0];
									//满签直接取当天应奖励现金
									else rewardsMoney=rewards[Integer.valueOf(day.toString())];
									
									masterUser.setBalance(NumberUtils.feeFormat(Double.parseDouble(rewardsMoney)+Double.parseDouble(masterUser.getBalance())));
									
									//插入奖励
									balanceLogRepository.save(new BalanceLog(masterUser.getId(),
											rewardsMoney,
											masterUser.getBalance(),
											String.valueOf(Double.parseDouble(rewardsMoney)+Double.parseDouble(masterUser.getBalance())),
											Consts.BalanceLog.Type.EIGHT_DAY_REWARDS,
											u.getId()
											));
								
								}
								
								
								
							}
							
							
						}else {
							//超过8天奖励
							String gold=goldRewardsRepository.findByType(Consts.GoldLog.Type.MASTER_READ_REWARDS).getGold();
							
							masterUser.setGold((masterUser.getGold()+Long.valueOf(gold)));
							userLoginRepository.save(masterUser);
							
							goldLogRepository.save(new GoldLog(masterUser.getId(),
									(masterUser.getGold()+Long.valueOf(gold)),
									Long.valueOf(gold),
									masterUser.getGold(),
									Consts.GoldLog.Type.MASTER_READ_REWARDS,
									u.getId()
									));
						}
						
						
						//判断是否有师爷
						if(!Util.isBlank(u.getMasterMaster())) {
							//师傅
							Optional<UserLogin> opMasterMasterUser=userLoginRepository.findById(u.getMasterMaster());
							if(opMasterMasterUser.isPresent()) {
								UserLogin masterMasterUser=opMasterMasterUser.get();
								if(day<5) {
									log.info(Consts.GoldLog.Type.READ);
									log.info(userId);
									log.info(dateFormat.format(TimeUtil.zeroForToday()));
									log.info(dateFormat.format(TimeUtil.twelveForToday()));
									//判断当天获得阅读奖励是否超过50
									if(goldLogRepository.sumNumByTypeAndUserIdAndCreateTimeBetween(Consts.GoldLog.Type.READ, userId, dateFormat.format(TimeUtil.zeroForToday()), dateFormat.format(TimeUtil.twelveForToday()))>=50) {
										if(goldLogRepository.countByTypeAndUserIdAndTriggerUserIdAndCreateTimeBetween(Consts.GoldLog.Type.FIVE_MASTER_MASTER_READ_REWARDS, masterMasterUser.getId(),userId, TimeUtil.zeroForToday(), TimeUtil.twelveForToday())==0) {
											//如果在奖励范围  触发奖励机制
											String[] rewards=goldRewardsRepository.findByType(Consts.GoldLog.Type.FIVE_MASTER_MASTER_READ_REWARDS).getGold().split(",");
											int count=goldLogRepository.countByTypeAndUserIdAndTriggerUserIdAndCreateTimeBetween(Consts.GoldLog.Type.FIVE_MASTER_MASTER_READ_REWARDS, masterMasterUser.getId(),u.getId(), u.getCreateTime(), today);
											
											String rewardsGold="0";
											
											//如果注册天数应得次数-当前满足条件次数大于0则证明其为断签状态  那么则取第一天签到的奖励金额
											if((day+1-count)>0) rewardsGold=rewards[0];
											//满签直接取当天应奖励现金
											else rewardsGold=rewards[Integer.valueOf(day.toString())];
											
											masterMasterUser.setGold((masterMasterUser.getGold()+Long.valueOf(rewardsGold)));
											//更新用户表
											userLoginRepository.save(masterMasterUser);
											
											//插入奖励
											goldLogRepository.save(new GoldLog(masterMasterUser.getId(),
													(masterMasterUser.getGold()+Long.valueOf(rewardsGold)),
													Long.valueOf(rewardsGold),
													masterMasterUser.getGold(),
													Consts.GoldLog.Type.FIVE_MASTER_MASTER_READ_REWARDS,
													u.getId()
													));
										}
										
									}
								}else {
									//徒孙阅读超过5天奖励
									String gold=goldRewardsRepository.findByType(Consts.GoldLog.Type.MASTER_MASTER_READ_REWARDS).getGold();
									
									masterMasterUser.setGold((masterMasterUser.getGold()+Long.valueOf(gold)));
									//更新用户表
									userLoginRepository.save(masterMasterUser);
									
									goldLogRepository.save(new GoldLog(masterMasterUser.getId(),
											(masterMasterUser.getGold()+Long.valueOf(gold)),
											Long.valueOf(gold),
											masterMasterUser.getGold(),
											Consts.GoldLog.Type.MASTER_READ_REWARDS,
											u.getId()
											));
								}
							}
							
							
						}
					}
					
					
				}
				flag=1;
			}
			}
				NewsInfoRead nr=o.get();
				nr.setFlag(1);
				newsInfoReadRepository.save(nr);
				
				//新手任务
				newerMission.entry(Consts.NewerMission.Type.READ, userId);
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
	
	
	public RewardsRes getRewardsCnt(String userId) {
		System.out.println(TimeUtil.zeroForToday());
		System.out.println(TimeUtil.twelveForToday());
		//当天阅读奖励
		int readGoldCnt=goldLogRepository.countByTypeAndUserIdAndCreateTimeBetween(Consts.GoldLog.Type.READ, userId, TimeUtil.zeroForToday(), TimeUtil.twelveForToday());
		//配置的阅读奖励 条数 和 奖励金币数量
		Optional<ReadReward> opt=readRewardsRepository.findById("1");
		ReadReward r=opt.get();
		int flag=0;
		
//		//计算当前时间
//		Calendar calendar=Calendar.getInstance();
//		int hour=calendar.get(Calendar.HOUR_OF_DAY);
		int cnt=r.getDailyCnt();
		
		Optional<ReadReward> op=readRewardsRepository.findById("1");
		
		
		return new RewardsRes(String.valueOf(readGoldCnt),String.valueOf(cnt),String.valueOf(op.get().getGold()));
	}
	
	
	public NewsRes getWanderFulNews(String newsPageSize,String adPageSize) {
		
		java.util.Random r=new java.util.Random();
		long c=newsInfoRepository.count();
		r.nextInt();
		
		List<NewsByTypeRes> newsByTypeArray=new ArrayList<NewsByTypeRes>();
		
		List<NewsInfoRes> newsInfoResArray=new ArrayList<NewsInfoRes>();
		
		for(int i=0;i<Integer.valueOf(newsPageSize);i++) {
			int p=r.nextInt(Integer.valueOf(String.valueOf(c)));
			List<NewsInfo> newsInfoArray=newsInfoRepository.findByFlag(com.lvmq.util.PagePlugin.pagePluginSort(p, 1,Direction.DESC, "publishDate"), 0);
			newsInfoResArray.add(new NewsInfoRes(newsInfoArray.get(0),"0","0",0));
		}
		newsByTypeArray.add(new NewsByTypeRes(newsInfoResArray));
		
		long adcount=advertInfoRepository.count();
		
		List<AdvertRes> ads=new ArrayList<AdvertRes>();
		for(int i=0;i<Integer.valueOf(adPageSize);i++) {
			int p=r.nextInt(Integer.valueOf(String.valueOf(adcount)));
			List<AdvertInfo> advertInfo=advertInfoRepository.findByFlag(com.lvmq.util.PagePlugin.pagePluginSort(p, 1,Direction.DESC, "createTime"), 0);
			List<String> imgs=new ArrayList<String>();
			advertInfo.get(0).getAdvertImgs().forEach(x->imgs.add(x.getImg()));
			String adType="0";
			if(imgs.size()>3) {
				adType="3";
			}else if(imgs.size()==0) {
				adType="0";
			}else {
				adType="1";
			}
			ads.add(new AdvertRes(advertInfo.get(0),imgs,adType));
		}
		
		return new NewsRes(newsByTypeArray,ads);
	}
	
	public int addViewCount(String id) {
		Optional<NewsInfo> n=newsInfoRepository.findById(id);
		if(n.isPresent()) {
			NewsInfo newsInfo=n.get();
			newsInfo.setViewCount(newsInfo.getViewCount()+1);
			newsInfoRepository.save(newsInfo);
			return newsInfo.getViewCount();
		}else {
			return 0;
		}
	}
}