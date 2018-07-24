package com.lvmq.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lvmq.model.NewsRemoveLog;
import com.lvmq.repository.NewsRemoveLogRepository;
import com.lvmq.service.NewsRemoveService;

@Component
public class NewsRemoveSerivceImpl implements NewsRemoveService {
	
	@Autowired
	private NewsRemoveLogRepository newsRemoveLogRepository;

	@Override
	public boolean removeNews(String id,String userId) {
		// TODO Auto-generated method stub
		try {
			NewsRemoveLog newsRemoveLog=newsRemoveLogRepository.save(new NewsRemoveLog(userId, id));
			if(newsRemoveLog==null) {
				return false;
			}
			return true;
		}catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
}
