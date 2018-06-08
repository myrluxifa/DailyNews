package com.lvmq.service;

import org.springframework.stereotype.Service;

import com.lvmq.api.res.BannerRes;
import com.lvmq.api.res.InviteInfoRes;
import com.lvmq.api.res.RecallRes;

@Service
public interface InviteService {
	BannerRes getBanner();
	
	InviteInfoRes getInviteInfo(String userId) ;
	
	 RecallRes  recallList(String userId);
	 
	 boolean recall(String userId,String recallUser);
	 
	 int setInviteCode(String userId,String inviteCode);
}
