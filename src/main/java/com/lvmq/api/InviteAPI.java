package com.lvmq.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lvmq.api.res.BannerRes;
import com.lvmq.api.res.base.ResponseBean;
import com.lvmq.base.Code;
import com.lvmq.service.InviteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"邀请"})
@RestController
@RequestMapping("/api/news")
public class InviteAPI {
	
	@Autowired
	private InviteService inviteService;
	
	
	@ApiOperation(value="广告（包含图片轮播和文字轮播）",notes="")
	@RequestMapping(value="/getBanner",method=RequestMethod.POST)
	public ResponseBean<BannerRes> getBanner() {
		inviteService.getBanner();
		return new ResponseBean<BannerRes>(Code.SUCCESS,Code.SUCCESS_CODE,"成功", inviteService.getBanner());
	}
}
