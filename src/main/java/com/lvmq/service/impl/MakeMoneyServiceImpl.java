package com.lvmq.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

import com.lvmq.api.res.MakeMoneyDetailRes;
import com.lvmq.api.res.MakeMoneyRes;
import com.lvmq.model.MakeMoney;
import com.lvmq.model.MakeMoneyLog;
import com.lvmq.repository.MakeMoneyLogRepository;
import com.lvmq.repository.MakeMoneyRepository;
import com.lvmq.service.MakeMoneyService;

@Component
public class MakeMoneyServiceImpl implements MakeMoneyService {
	
	@Autowired
	private MakeMoneyRepository makeMoneyRepository;
	
	@Autowired
	private MakeMoneyLogRepository makeMoneyLogRepository;
	
	public List<MakeMoneyRes> makeMoneyList(String userId,String page,String pageSize) {
		List<MakeMoney> makeMoneyList=makeMoneyRepository.findByFlag(com.lvmq.util.PagePlugin.pagePluginSort(Integer.valueOf(page),Integer.valueOf(pageSize),Direction.DESC),0);
		List<MakeMoneyRes> makeMoneyResList=new ArrayList<MakeMoneyRes>();
		for(MakeMoney m:makeMoneyList) {
			
			Optional<MakeMoneyLog> makeMoneyLog=makeMoneyLogRepository.findByMakeMoneyIdAndUserId(m.getId(), userId);
			if(makeMoneyLog.isPresent()) {
				makeMoneyResList.add(new MakeMoneyRes(m,makeMoneyLog.get().getStatus()));
			}else {
				makeMoneyResList.add(new MakeMoneyRes(m));
			}
		}
		return makeMoneyResList;
	}
	
	public	void takePartIn(String userId,String id) {
		Optional<MakeMoney> makeMoney=makeMoneyRepository.findById(id);
		if(makeMoney.isPresent()) {
			Optional<MakeMoneyLog> makeMoneyLog=makeMoneyLogRepository.findByMakeMoneyIdAndUserId(id, userId);
			if(!makeMoneyLog.isPresent()) {
				Calendar cal = Calendar.getInstance();   
		        cal.setTime(makeMoneyLog.get().getCreateTime());
		        cal.add(Calendar.MINUTE, makeMoney.get().getTimeLimit());
				makeMoneyLogRepository.save(new MakeMoneyLog(userId,id,1,cal.getTime()));
			}
		}
	}
	
	
	public	void saveImgs(String imgUrls,String userId,String id) {
		Optional<MakeMoneyLog> makeMoneyLog=makeMoneyLogRepository.findByMakeMoneyIdAndUserId(id, userId);
		if(makeMoneyLog.isPresent()) {
			MakeMoneyLog m=makeMoneyLog.get();
			m.setImgs(imgUrls);
			m.setStatus(2);
			makeMoneyLogRepository.save(m);
		}
	}
	
	public	void cancel(String userId,String id) {
		Optional<MakeMoneyLog> makeMoneyLog=makeMoneyLogRepository.findByMakeMoneyIdAndUserId(id, userId);
		if(makeMoneyLog.isPresent()) {
			MakeMoneyLog m=makeMoneyLog.get();
			m.setStatus(5);
			makeMoneyLogRepository.save(m);
		}
	}
	
	public MakeMoneyDetailRes detail(String userId,String id) {
		Optional<MakeMoney> makeMoney=makeMoneyRepository.findById(id);
		if(makeMoney.isPresent()) {
			Optional<MakeMoneyLog> makeMoneyLog=makeMoneyLogRepository.findByMakeMoneyIdAndUserId(id, userId);
			SimpleDateFormat dateFormate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(makeMoneyLog.isPresent()) {
				return new MakeMoneyDetailRes(makeMoney.get(), makeMoneyLog.get().getStatus(),dateFormate.format(makeMoneyLog.get().getEndTime()));
			}else {
				return new MakeMoneyDetailRes(makeMoney.get(), 0,"");
			}
		}else {
			return new MakeMoneyDetailRes();
		}
	}

}
