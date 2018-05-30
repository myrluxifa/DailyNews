package com.lvmq.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lvmq.api.res.BannerRes;
import com.lvmq.api.res.ImgBanner;
import com.lvmq.api.res.InviteInfoRes;
import com.lvmq.base.Consts;
import com.lvmq.model.InviteCharBanner;
import com.lvmq.model.InviteImgBanner;
import com.lvmq.model.UserLogin;
import com.lvmq.repository.BalanceLogRepository;
import com.lvmq.repository.GoldLogRepository;
import com.lvmq.repository.InviteCharBannerRepository;
import com.lvmq.repository.InviteImgBannerRepository;
import com.lvmq.repository.UserLoginRepository;
import com.lvmq.service.InviteService;
import com.lvmq.util.Util;


@Component
public class InviteServiceImpl implements InviteService {
	
	@Autowired
	private InviteImgBannerRepository inviteImgBannerRepository;
	
	@Autowired
	private InviteCharBannerRepository inviteCharBannerRepository;
	
	@Autowired
	private GoldLogRepository goldLogRepository;
	
	@Autowired
	private BalanceLogRepository balanceLogRepository;
	
	@Autowired
	private UserLoginRepository userLoginRepository;
	
	
	private static final Logger log = LoggerFactory.getLogger(InviteServiceImpl.class);

	@Override
	public BannerRes getBanner() {
		// TODO Auto-generated method stub
		List<InviteImgBanner> inviteImgBannerArray=inviteImgBannerRepository.findAllByFlag(0);
		List<ImgBanner> imgBannerArray=new ArrayList<ImgBanner>();
		inviteImgBannerArray.forEach(x->imgBannerArray.add(new ImgBanner(x)));
		Optional<InviteCharBanner> opt=inviteCharBannerRepository.findById("1");
		List<String> strArray=new ArrayList<String>();
		if(opt.isPresent()) {
			String content=opt.get().getContent();
			for(int i=0;i<10;i++) {
				strArray.add(Util.getContent(content));
			}
		}
		return new BannerRes(imgBannerArray,strArray);
	}

	
	
	public InviteInfoRes getInviteInfo(String userId) {
		//金币奖励
		List<String> goldTypeList=Arrays.asList(Consts.GoldLog.Type.MASTER_READ_REWARDS,
				Consts.GoldLog.Type.MASTER_MASTER_READ_REWARDS,
				Consts.GoldLog.Type.FIVE_MASTER_MASTER_READ_REWARDS);
		int goldSum=goldLogRepository.sumNumByTypeInAndUserId(goldTypeList,userId);
		
		log.info(String.valueOf(goldSum));
		
		//现金奖励
		List<String> balanceTypeList=Arrays.asList(Consts.BalanceLog.Type.EIGHT_DAY_REWARDS,Consts.BalanceLog.Type.FIRST_INVITE);
		String balanceSum=balanceLogRepository.sumNumByTypeInAndUserId(balanceTypeList, userId);
		log.info(balanceSum);
		
		
		String  income=String.valueOf((long)Double.parseDouble(balanceSum)*Consts.GOLD_RATIO+goldSum);
		
		String inviteCount="0";
		String inviteCode="";
		Optional<UserLogin> u=userLoginRepository.findById(userId);
		if(u.isPresent()) {
			inviteCount=String.valueOf(u.get().getInviteCount());
			inviteCode=u.get().getMyInviteCode();
		}
		
		return new InviteInfoRes(income,inviteCount,inviteCode);
		
	}
	
}
