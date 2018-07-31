package com.lvmq.service.impl;

import java.util.Calendar;

import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvmq.model.DayShare;
import com.lvmq.repository.DayShareRepository;
import com.lvmq.service.DayShareService;

@Service
public class DayShareServiceImpl implements DayShareService {

	@Autowired
	private DayShareRepository dayShareRepository;
	
	@Override
	public DayShare update(String userId) {
		return update(userId, false);
	}
	
	@Override
	public DayShare getTimes(String userId) {
		return update(userId, true);
	}
	
	DayShare update(String userId, boolean getTimes) {
		String today =  DateUtils.formatDate(Calendar.getInstance().getTime(), "yyyyMMdd");
		DayShare ds = dayShareRepository.findTop1ByUserIdAndMdateOrderByCreateTimeDesc(userId, today);
		if(!getTimes) {
			DayShare ds1 = new DayShare(null == ds ? 1 : ds.getCnt() + 1, today, userId);			
			return dayShareRepository.saveAndFlush(ds1);
		} else {
			return ds;
		}
		
	}
}
