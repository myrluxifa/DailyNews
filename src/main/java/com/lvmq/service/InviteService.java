package com.lvmq.service;

import org.springframework.stereotype.Service;

import com.lvmq.api.res.BannerRes;
import com.lvmq.api.res.InviteInfoRes;

@Service
public interface InviteService {
	BannerRes getBanner();
	
	InviteInfoRes getInviteInfo(String userId) ;
}
