package com.lvmq.service;

import org.springframework.stereotype.Service;

import com.lvmq.api.res.VideosArrayRes;

@Service
public interface VideosService {

	void getVideosFromIDataAPI();
	
	VideosArrayRes getVideosArray(int page,int pageSize);

	VideosArrayRes getWonderfulVideosArray(int pageSize);
	
}
