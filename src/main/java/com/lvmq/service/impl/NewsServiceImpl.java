package com.lvmq.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.lvmq.idata.IdataAPI;
import com.lvmq.idata.res.ToutiaoDataResponseDto;
import com.lvmq.idata.res.ToutiaoResponseDto;
import com.lvmq.model.NewsInfo;
import com.lvmq.model.NewsType;
import com.lvmq.repository.NewsInfoRepository;
import com.lvmq.repository.NewsTypeRepository;
import com.lvmq.service.NewsService;

@Component
public class NewsServiceImpl implements NewsService {
	
	private static final Logger log = LoggerFactory.getLogger(NewsServiceImpl.class);

	@Autowired
	private NewsInfoRepository newsInfoRepository;
	
	@Autowired
	private NewsTypeRepository newsTypeRepository;

	@Override
	public void getNewsFromIDataAPI() {
		// TODO Auto-generated method stub
		try {
			
			List<NewsType> newsTypeArray=newsTypeRepository.findAllByFlag(0);
			
			for(NewsType n: newsTypeArray) {
				String catValue=n.getCatValue();
				String url = "http://api01.bitspaceman.com:8000/news/toutiao?apikey=np5SpQ7QGzm7HgvX8Aw8APA5NDq6Bpj5m4eo4hX5qJFLm0G0Oqt31xJzjIEeJFTv&catid="+catValue;
				String json = IdataAPI.getRequestFromUrl(url);
				log.info(json);
				Gson gson=new Gson();
				ToutiaoResponseDto toutiaoResponseDto=gson.fromJson(json, ToutiaoResponseDto.class);
				
				List<NewsInfo> newsInfoArray=new ArrayList<NewsInfo>();
				for(ToutiaoDataResponseDto toutiao :toutiaoResponseDto.getData()) {
					newsInfoArray.add(new NewsInfo(toutiao,catValue));
				}
				List<NewsInfo> iter=(List<NewsInfo>) newsInfoRepository.saveAll(newsInfoArray);
			}
			
		}catch(Exception e) {
			log.info(e.getMessage());
		}
	}
	

}
