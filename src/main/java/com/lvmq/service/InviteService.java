package com.lvmq.service;

import org.springframework.stereotype.Service;

import com.lvmq.api.res.BannerRes;

@Service
public interface InviteService {
	BannerRes getBanner();
}
