package com.lvmq.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.lvmq.api.res.VideosArrayRes;
import com.lvmq.api.res.VideosRes;
import com.lvmq.idata.IDataAPI;
import com.lvmq.idata.res.ToutiaoDataResponseDto;
import com.lvmq.idata.res.ToutiaoResponseDto;
import com.lvmq.idata.res.VideosDataResponseDto;
import com.lvmq.idata.res.VideosResponseDto;
import com.lvmq.model.NewsInfo;
import com.lvmq.model.NewsType;
import com.lvmq.model.VideosInfo;
import com.lvmq.repository.VideosInfoRepository;
import com.lvmq.service.VideosService;

@Configuration
@Component
public class VideosServiceImpl implements VideosService {
	
	
	private static final Logger log = LoggerFactory.getLogger(VideosServiceImpl.class);

	@Autowired
	private VideosInfoRepository videosInfoRepository;
	
	//@Scheduled(cron="0 0 0/6 * * ? ")
	@Scheduled(cron="0 58 10 * * ? ")
	@SuppressWarnings("unchecked")
	@Override
	public void getVideosFromIDataAPI() {
		// TODO Auto-generated method stub
		try {
			
			
				String url = "http://120.76.205.241:8000/video/le?apikey=np5SpQ7QGzm7HgvX8Aw8APA5NDq6Bpj5m4eo4hX5qJFLm0G0Oqt31xJzjIEeJFTv&kw=头条";
				String json = IDataAPI.getRequestFromUrl(url);
				log.info(json);
				Gson gson=new Gson();
				
				VideosResponseDto videosResponseDto=gson.fromJson(json, VideosResponseDto.class);
				List<VideosDataResponseDto> videosDataResponseDto=videosResponseDto.getData();
				
				List<VideosInfo> videosInfoArray=new ArrayList<VideosInfo>();
				
				for(VideosDataResponseDto dto: videosDataResponseDto) {
					try{
						videosInfoRepository.save(new VideosInfo(dto));
					}catch (Exception e) {
						// TODO: handle exception
						log.info(e.getMessage());
					}
				}

				if("true".equals(videosResponseDto.getHasNext())) {
					getVideosFromIDataAPIByPageToken(videosResponseDto.getPageToken());
					
				}
				//不能批量添加还需要判断视频重复问题
				//videosInfoRepository.saveAll(videosInfoArray);
				
				
				
		}catch(Exception e) {
			log.info(e.getMessage());
		}
	}
	
	public void getVideosFromIDataAPIByPageToken(String pageToken) {
		// TODO Auto-generated method stub
				try {
					
					
						String url = "http://120.76.205.241:8000/video/le?apikey=np5SpQ7QGzm7HgvX8Aw8APA5NDq6Bpj5m4eo4hX5qJFLm0G0Oqt31xJzjIEeJFTv&kw=头条"+"&pageToken="+pageToken;
						String json = IDataAPI.getRequestFromUrl(url);
						log.info(json);
						Gson gson=new Gson();
						
						VideosResponseDto videosResponseDto=gson.fromJson(json, VideosResponseDto.class);
						List<VideosDataResponseDto> videosDataResponseDto=videosResponseDto.getData();
						
						List<VideosInfo> videosInfoArray=new ArrayList<VideosInfo>();
						
						for(VideosDataResponseDto dto: videosDataResponseDto) {
							try{
								videosInfoRepository.save(new VideosInfo(dto));
							}catch (Exception e) {
								// TODO: handle exception
								log.info(e.getMessage());
							}
						}

						if("true".equals(videosResponseDto.getHasNext())) {
							getVideosFromIDataAPIByPageToken2(videosResponseDto.getPageToken());
						}
						//不能批量添加还需要判断视频重复问题
						//videosInfoRepository.saveAll(videosInfoArray);
						
						
				}catch(Exception e) {
					log.info(e.getMessage());
				}
	}
	
	public void getVideosFromIDataAPIByPageToken2(String pageToken) {
		// TODO Auto-generated method stub
				try {
					
					
						String url = "http://120.76.205.241:8000/video/le?apikey=np5SpQ7QGzm7HgvX8Aw8APA5NDq6Bpj5m4eo4hX5qJFLm0G0Oqt31xJzjIEeJFTv&kw=头条"+"&pageToken="+pageToken;
						String json = IDataAPI.getRequestFromUrl(url);
						log.info(json);
						Gson gson=new Gson();
						
						VideosResponseDto videosResponseDto=gson.fromJson(json, VideosResponseDto.class);
						List<VideosDataResponseDto> videosDataResponseDto=videosResponseDto.getData();
						
						List<VideosInfo> videosInfoArray=new ArrayList<VideosInfo>();
						
						for(VideosDataResponseDto dto: videosDataResponseDto) {
							try{
								videosInfoRepository.save(new VideosInfo(dto));
							}catch (Exception e) {
								// TODO: handle exception
								log.info(e.getMessage());
							}
						}

						if("true".equals(videosResponseDto.getHasNext())) {
							getVideosFromIDataAPIByPageToken(videosResponseDto.getPageToken());
						}
						//不能批量添加还需要判断视频重复问题
						//videosInfoRepository.saveAll(videosInfoArray);
						
						
				}catch(Exception e) {
					log.info(e.getMessage());
				}
	}
	
	
	
	
	@Override
	public VideosArrayRes getVideosArray(int page,int pageSize) {
		try {
			List<VideosInfo> videosArray=videosInfoRepository.findByFlag(com.lvmq.util.PagePlugin.pagePluginSort(page, pageSize,Direction.DESC, "publishDate"), 0);
			List<VideosRes> videosResArray=new ArrayList<VideosRes>();
			videosArray.forEach(x->videosResArray.add(new VideosRes(x)));
			return new VideosArrayRes(videosResArray);
		}catch(Exception e) {
			return new VideosArrayRes();
		}
	}
	
	@Override
	public VideosArrayRes getWonderfulVideosArray(int pageSize) {
		try {
			java.util.Random r=new java.util.Random();
			long c=videosInfoRepository.count();
			r.nextInt();
			List<VideosRes> videosResArray=new ArrayList<VideosRes>();
			for(int i=0;i<pageSize;i++) {
				int p=r.nextInt(Integer.valueOf(String.valueOf(c)));
				List<VideosInfo> videosArray=videosInfoRepository.findByFlag(com.lvmq.util.PagePlugin.pagePluginSort(p, 1,Direction.DESC, "publishDate"), 0);
				videosResArray.add(new VideosRes(videosArray.get(0)));
			}
			return new VideosArrayRes(videosResArray);
		}catch(Exception e) {
			return new VideosArrayRes();
		}
	}
	
	
	@Override
	public int addAmountOfPlay(String id) {
		try {
			
			Optional<VideosInfo> ov=videosInfoRepository.findById(id);
			if(ov.isPresent()) {
				VideosInfo v=ov.get();
				v.setViewCount(v.getViewCount()+1);
				videosInfoRepository.save(v);
				return v.getViewCount();
			}else {
				return 0;
			}
		}catch(Exception e) {
			return 0;
		}
	}
}
