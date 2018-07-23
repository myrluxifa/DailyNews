package com.lvmq.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lvmq.api.res.EasyMoneyListRes;
import com.lvmq.api.res.EasyMoneyShareRes;
import com.lvmq.api.res.EasyMoneyTaskRes;
import com.lvmq.api.res.MakeMoneyDetailRes;
import com.lvmq.api.res.MakeMoneyImgsRes;
import com.lvmq.api.res.MakeMoneyRes;
import com.lvmq.api.res.MakeMoneyTaskRes;

@Service
public interface MakeMoneyService {

	List<MakeMoneyRes> makeMoneyList(String userId,String page,String pageSize);
	
	void takePartIn(String userId,String id);
	
	void saveImgs(String imgUrls,String userId,String id); 
	
	void cancel(String userId,String id);
	
	MakeMoneyDetailRes detail(String userId,String id);
	
	 List<EasyMoneyListRes> easyMoneyList(String userId,String page,String pageSize);
	 
	 List<MakeMoneyTaskRes> makeMoneyTask(String userId,String page,String pageSize);

	 EasyMoneyShareRes easyMoneyShare(String userId,String id);
	 
	 List<EasyMoneyTaskRes> easyMoneyTask(String userId,Pageable pageable);
	 
	 String readEasyMoneyShare(String token,String read);
	 
	 List<MakeMoneyImgsRes> getMakeMoneyExample(String id);
}
