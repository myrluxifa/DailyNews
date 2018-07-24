package com.lvmq.service;

import org.springframework.stereotype.Service;

@Service
public interface NewsRemoveService {
	boolean removeNews(String id,String userId);
}
