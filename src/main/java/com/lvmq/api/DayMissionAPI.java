package com.lvmq.api;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lvmq.api.res.base.ResponseBean;
import com.lvmq.base.Code;
import com.lvmq.base.Consts;
import com.lvmq.model.DayMission;
import com.lvmq.repository.DayMissionRepository;
import com.lvmq.service.DayMissionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"日常任务"})
@RestController
@RequestMapping("api/daymission")
public class DayMissionAPI extends BaseAPI {
	
	@Autowired
	private DayMissionRepository dayMissionRepository;
	
	@Autowired
	private DayMissionService dayMissionService;
	
	@ApiOperation(value = "日常任务状态", notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, dataType = "String")
	})
	@PostMapping("state")
	public ResponseBean<Object> newerMisson(String userId){
		try {
			
			String today = DateUtils.formatDate(Calendar.getInstance().getTime(), "yyyyMMdd");
			
			DayMission dm = dayMissionRepository.findByUserIdAndMdate(userId, today);
			
			if(null == dm) {
				dm = dayMissionRepository.save(new DayMission(userId, today));
			}
			
			return new ResponseBean<Object>(Code.SUCCESS, Code.SUCCESS, dm.getParam());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ResponseBean<Object>(Code.FAIL, Code.FAIL, e.getMessage());
		}
	}
	
	@ApiOperation(value = "晒收入后调用", notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, dataType = "String")
	})
	@PostMapping("show")
	public ResponseBean<Object> show(String userId){
		try {
			
			// 日常任务 分享奖励
			DayMission dm = dayMissionService.updateDayMission(userId, Consts.DayMission.Type.SHOW);
			
			return new ResponseBean<Object>(Code.SUCCESS, Code.SUCCESS, dm.getParam());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ResponseBean<Object>(Code.FAIL, Code.FAIL, e.getMessage());
		}
	}
}
