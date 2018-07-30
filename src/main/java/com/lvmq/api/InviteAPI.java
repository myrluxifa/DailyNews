package com.lvmq.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lvmq.api.res.BannerRes;
import com.lvmq.api.res.InviteInfoRes;
import com.lvmq.api.res.RecallRes;
import com.lvmq.api.res.base.ResponseBean;
import com.lvmq.base.Code;
import com.lvmq.base.Consts;
import com.lvmq.model.DayMission;
import com.lvmq.model.UserLogin;
import com.lvmq.repository.UserLoginRepository;
import com.lvmq.service.DayMissionService;
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
	
	@Autowired
	private UserLoginRepository userLoginRepository;
	
	@Autowired
	private DayMissionService dayMissionService;
	
	@ApiOperation(value="广告（包含图片轮播和文字轮播）",notes="")
	@RequestMapping(value="/getBanner",method=RequestMethod.POST)
	public ResponseBean<BannerRes> getBanner() {
		inviteService.getBanner();
		return new ResponseBean<BannerRes>(Code.SUCCESS,Code.SUCCESS_CODE,"成功", inviteService.getBanner());
	}
	
	@ApiOperation(value="轮播下面邀请信息（所获得收益，邀请人数，我的邀请码）",notes="")
	@RequestMapping(value="/getInviteInfo",method=RequestMethod.POST)
	public ResponseBean<InviteInfoRes> getInviteInfo(String userId){
		return new ResponseBean<InviteInfoRes>(Code.SUCCESS,Code.SUCCESS_CODE,"成功", inviteService.getInviteInfo(userId));
	}
	
	
	@ApiOperation(value="召回徒弟列表")
	@RequestMapping(value="/recallList",method=RequestMethod.POST)
	public ResponseBean<RecallRes> recallList(String userId){
		return new ResponseBean<RecallRes>(Code.SUCCESS,Code.SUCCESS_CODE,"成功", inviteService.recallList(userId));
	}
	
	
	@ApiOperation(value="召回")
	@RequestMapping(value="/recall",method=RequestMethod.POST)
	public ResponseBean recall(String userId,String recallUser){
		return new ResponseBean(Code.SUCCESS,Code.SUCCESS_CODE,"成功", inviteService.recall(userId,recallUser));
	}
	
	
	@ApiOperation(value="填写邀请码")
	@RequestMapping(value="/setInviteCode",method=RequestMethod.POST)
	public ResponseBean setInviteCode(String userId,String inviteCode) {
		
		UserLogin uin = userLoginRepository.findByMyInviteCode(inviteCode);
		
		if(null == uin) {
			return new ResponseBean(Code.FAIL,Code.FAIL,"失败","邀请码不存在");
		}
		
		int flag=inviteService.setInviteCode(userId, inviteCode);
		
		if(flag==-1) {
			return new ResponseBean(Code.FAIL,Code.SET_INVITE_FAIL,"邀请码不存在");
		}else if(flag==0) {
//			UserLogin user = userLoginRepository.findByMyInviteCode(inviteCode);
//			if(null != user) {
//				DayMission dm = dayMissionService.updateDayMission(user.getId(), Consts.DayMission.Type.INVITE);					
//			}
			return new ResponseBean(Code.SUCCESS,Code.SUCCESS_CODE,"成功");
		}else if(flag==-4) {
			return new ResponseBean(Code.FAIL,Code.SET_INVITE_FAIL,"已填写邀请码");
		}
		else if(flag==-3) {
			return new ResponseBean(Code.FAIL,Code.SET_INVITE_FAIL,"不能填写本人邀请码");
		}
		else {
			return new ResponseBean(Code.FAIL,Code.SET_INVITE_FAIL,"失败");
		}
		
	}
	
	@ApiOperation(value="徒弟列表")
	@RequestMapping(value="/tudiList",method=RequestMethod.POST)
	public ResponseBean tudiList(String userId) {
		return new ResponseBean(Code.SUCCESS,Code.SUCCESS_CODE,"成功",inviteService.tudiList(userId));
	}
}
