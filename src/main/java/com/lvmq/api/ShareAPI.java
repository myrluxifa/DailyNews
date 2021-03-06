package com.lvmq.api;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lvmq.api.res.base.ResponseBean;
import com.lvmq.base.Code;
import com.lvmq.base.Consts;
import com.lvmq.model.GoldLog;
import com.lvmq.model.UserLogin;
import com.lvmq.repository.GoldLogRepository;
import com.lvmq.repository.UserLoginRepository;
import com.lvmq.service.DayMissionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author Easy
 *
 */
@Api(tags = {"分享"})
@RestController
@RequestMapping("api/share")
public class ShareAPI extends BaseAPI{
	
	@Autowired
	private UserLoginRepository userLoginRepository;
	
	@Autowired
	private GoldLogRepository goldLogRepository;
	
	@Autowired
	private DayMissionService dayMissionService;
	
	@Autowired
	private NewerMissionAPI newerMission;
	
	@ApiOperation(value="进入分享页面初始化", notes="", httpMethod = "POST")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "userId", value = "用户ID", required = true, dataType = "String")
	})
	@PostMapping("init")
	public ResponseBean<Object> init(String userId) {
		try {
			//截止时间
			Calendar calendar = Calendar.getInstance();
			Date endTime = calendar.getTime();
			
			//设置当天00:00:00点
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			Date startTime = calendar.getTime();
			
			//获取当天分享次数
			List<GoldLog> logs = goldLogRepository.findByTypeAndUserIdAndCreateTimeBetweenOrderByCreateTimeDesc(Consts.GoldLog.Type.SHARE, userId, startTime, endTime);
			
			int count = null == logs || logs.size() == 0 ? 0 : logs.size();
			long lastTime = null == logs || logs.size() == 0 ? 0 : logs.get(0).getCreateTime().getTime();
			
			Map<String, Object> result = new HashMap<>();
			result.put("count", 1 - count);
			long tt = Code.SHARE.INTERVAL_TIME - (Calendar.getInstance().getTimeInMillis() / 1000 - lastTime / 1000 );
			result.put("lastTime", tt);
			
			return new ResponseBean<>(Code.SUCCESS, Code.SUCCESS_CODE, result);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ResponseBean<>(Code.FAIL,Code.UNKOWN_CODE,e.getMessage());
		}
	}

	@ApiOperation(value="分享成功后调用", notes="分享成功后调用该接口，增加用户金币", httpMethod = "POST")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "userId", value = "用户ID", required = true, dataType = "String"),
		@ApiImplicitParam(paramType = "query", name = "fo", value = "分享到朋友圈N否Y是", required = true, dataType = "String")
	})
	@PostMapping("success")
	@Transactional
	public ResponseBean<Object> success(String userId, String fo) {
		try {
			//截止时间
			Calendar calendar = Calendar.getInstance();
			Date endTime = calendar.getTime();
			
			//设置当天00:00:00点
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			Date startTime = calendar.getTime();
			//获取当天分享次数
			List<GoldLog> logs = goldLogRepository.findByTypeAndUserIdAndCreateTimeBetweenOrderByCreateTimeDesc(Consts.GoldLog.Type.SHARE, userId, startTime, endTime);
			
			//分享次数判断
			if(null != logs && logs.size() >= 1) {
				return new ResponseBean<>(Code.FAIL, Code.SHARE.MORE_THAN_LIMITED_TIMES.code, "每天只能分享1次");
			}
			
			if("Y".equalsIgnoreCase(fo)) {
				//新手任务
				newerMission.entry(Consts.NewerMission.Type.SHARE, userId);				
			} else {
				// 日常任务 分享奖励
				dayMissionService.updateDayMission(userId, Consts.DayMission.Type.SHARE);				
			}
			
			
			return new ResponseBean<>(Code.SUCCESS, Code.SUCCESS_CODE, null);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ResponseBean<>(Code.FAIL,Code.UNKOWN_CODE,e.getMessage());
		}
	}
}
