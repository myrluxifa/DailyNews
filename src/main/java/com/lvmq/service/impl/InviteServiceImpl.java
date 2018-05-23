package com.lvmq.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lvmq.api.res.BannerRes;
import com.lvmq.api.res.ImgBanner;
import com.lvmq.model.InviteCharBanner;
import com.lvmq.model.InviteImgBanner;
import com.lvmq.repository.InviteCharBannerRepository;
import com.lvmq.repository.InviteImgBannerRepository;
import com.lvmq.service.InviteService;
import com.lvmq.util.Util;

@Component
public class InviteServiceImpl implements InviteService {
	
	@Autowired
	private InviteImgBannerRepository inviteImgBannerRepository;
	
	@Autowired
	private InviteCharBannerRepository inviteCharBannerRepository;

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

}
