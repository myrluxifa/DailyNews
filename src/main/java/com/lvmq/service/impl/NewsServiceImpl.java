package com.lvmq.service.impl;

import java.util.ArrayList;
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
import com.lvmq.idata.IdataAPI;
import com.lvmq.idata.res.ToutiaoDataResponseDto;
import com.lvmq.idata.res.ToutiaoResponseDto;
import com.lvmq.model.AdvertInfo;
import com.lvmq.model.NewsComment;
import com.lvmq.model.NewsInfo;
import com.lvmq.model.NewsInfoRead;
import com.lvmq.model.NewsType;
import com.lvmq.model.UserLogin;
import com.lvmq.repository.AdvertInfoRepository;
import com.lvmq.repository.NewsCommentRepository;
import com.lvmq.repository.NewsInfoReadRepository;
import com.lvmq.repository.NewsInfoRepository;
import com.lvmq.repository.NewsTypeRepository;
import com.lvmq.repository.UserLoginRepository;
import com.lvmq.service.NewsService;

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
	
	@Override
	public void getNewsFromIDataAPI() {
		// TODO Auto-generated method stub
		try {
			
			List<NewsType> newsTypeArray=newsTypeRepository.findAllByFlag(0);
			
			for(NewsType n: newsTypeArray) {
				try {
					String catId=n.getCatId();
					String url = "http://api01.bitspaceman.com:8000/news/toutiao?apikey=np5SpQ7QGzm7HgvX8Aw8APA5NDq6Bpj5m4eo4hX5qJFLm0G0Oqt31xJzjIEeJFTv&catid="+n.getCatId();
					String json = IdataAPI.getRequestFromUrl(url);
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
	
	
	public NewsRes home(String userId){
		try {
			List<NewsType> newsTypeArray=newsTypeRepository.findAllByFlag(0);
			
			List<NewsByTypeRes> newsByTypeArray=new ArrayList<NewsByTypeRes>();
			
			for(NewsType n:newsTypeArray) {
				
				List<NewsInfoRes> newsInfoResArray=new ArrayList<NewsInfoRes>();
				
				List<NewsInfo> newsInfoArray=newsInfoRepository.findByCatId(com.lvmq.util.PagePlugin.pagePluginSort(1, 10,Direction.DESC, "publishDate"),n.getCatId());
				
				newsInfoArray.forEach(x->newsInfoResArray.add(new NewsInfoRes(x,newsInfoReadRepository.countByuserIdAndNewsId(userId,x.getId()))));
				
				newsByTypeArray.add(new NewsByTypeRes(n.getCatId(),n.getCatValue(),newsInfoResArray));
			}
			
			List<AdvertInfo> advertInfo=advertInfoRepository.findByFlag(com.lvmq.util.PagePlugin.pagePluginSort(1, 10,Direction.DESC, "createTime"),0);
			
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
	
	public NewsCommentArrayRes getComment(String newsId,String loginId,int page,int pageSize,int pageLevel2,int pageSizeLevel2) {
		
		List<NewsComment> newsComments=newsCommentRepository.findByNewsIdAndFlagAndLevel(com.lvmq.util.PagePlugin.pagePluginSort(page, pageSize,Direction.DESC, "createTime") , newsId, 0, 1);
		List<NewsCommentRes> newsCommentRes=new ArrayList<NewsCommentRes>();
		for(NewsComment necs:newsComments) {
			
			
			List<NewsComment> newsCommentLevel2=newsCommentRepository.findByParentIdAndFlag(com.lvmq.util.PagePlugin.pagePluginSort(pageLevel2, pageSizeLevel2,Direction.DESC, "createTime"),necs.getId(),0);
			List<NewsCommentLevel2Res> comentLevel2=new ArrayList<NewsCommentLevel2Res>();
			newsCommentLevel2.forEach(x->comentLevel2.add(new NewsCommentLevel2Res(x.getUserLogin().getName(),x.getComment())));
			
			newsCommentRes.add(new NewsCommentRes(necs,comentLevel2));
		}
		
		return new NewsCommentArrayRes(newsCommentRes);
	}
	
	public NewsCommentRes setComment(String newsId,String loginId,String parentId,String comment,String level) {
		
		NewsComment n=newsCommentRepository.saveAndFlush(new NewsComment(newsId,loginId,parentId,level,comment));
		Optional<UserLogin> u=userLoginRepository.findById(loginId);		
		return new NewsCommentRes(n,u.get().getHeadPortrait(),u.get().getName());
	}

}
