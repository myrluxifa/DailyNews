package com.lvmq.api;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lvmq.api.res.base.ResponseBean;
import com.lvmq.base.Code;
import com.lvmq.base.Consts;
import com.lvmq.model.NewerMission;
import com.lvmq.repository.NewerMissonRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"新手"})
@RestController
@RequestMapping("api/newer")
public class NewerMissionAPI extends BaseAPI {

	@Autowired
	private NewerMissonRepository newerMissonRepository;
	
	@ApiOperation(value = "新手任务状态", notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, dataType = "String")
	})
	@PostMapping("mission")
	public ResponseBean<Object> newerMisson(String userId){
		try {
			NewerMission mission = newerMissonRepository.findByUserId(userId);
			
			if(null == mission) { //没有记录新建
				mission = new NewerMission();
				mission.setCreateTime(new Date());
				mission.setRead(0);
				mission.setSearch(0);
				mission.setShare(0);
				mission.setSign(0);
				mission.setUserId(userId);
				newerMissonRepository.save(mission);
			}
			Map<String, Object> result = new HashMap<>();
			result.put("sign", mission.getSign());
			result.put("share", mission.getShare());
			result.put("read", mission.getRead());
			result.put("search", mission.getSearch());
			
			return new ResponseBean<Object>(Code.SUCCESS, Code.SUCCESS, result);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ResponseBean<Object>(Code.FAIL, Code.FAIL, e.getMessage());
		}
	}
	
	public NewerMission entry(String type, String userId) {
		//参数最大值
		int maxRead = 10; int maxSign = 1; int maxShare = 1; int maxSearch = 2;
		
		NewerMission mission = newerMissonRepository.findByUserId(userId);
		if(null == mission) { //没有记录新建
			mission = new NewerMission();
			mission.setRead(0);
			mission.setSearch(0);
			mission.setShare(0);
			mission.setSign(0);
			mission.setCreateTime(new Date());
			mission.setUserId(userId);
		}
		
		switch(type) {
		case Consts.NewerMission.Type.READ: 
			mission.setRead(mission.getRead() >= maxRead ? maxRead : mission.getRead() + 1);
			break;
		case Consts.NewerMission.Type.SEARCH:
			mission.setSearch(mission.getSearch() >= maxSearch ? maxSearch : mission.getSearch() + 1);
			break;
		case Consts.NewerMission.Type.SHARE:
			mission.setShare(mission.getShare() >= maxShare ? maxShare : mission.getShare() + 1);
			break;
		case Consts.NewerMission.Type.SIGN:
			mission.setSign(mission.getSign() >= maxSign ? maxSign : mission.getSign() + 1);
			break;
		}
		
		newerMissonRepository.saveAndFlush(mission);
		
		return mission;
	}
}
