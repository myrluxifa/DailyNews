package com.lvmq.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lvmq.api.res.MakeMoneyDetailRes;
import com.lvmq.api.res.MakeMoneyRes;

@Service
public interface MakeMoneyService {

	List<MakeMoneyRes> makeMoneyList(String userId,String page,String pageSize);
	
	void takePartIn(String userId,String id);
	
	void saveImgs(String imgUrls,String userId,String id); 
	
	void cancel(String userId,String id);
	
	MakeMoneyDetailRes detail(String userId,String id);
}
