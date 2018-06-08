package com.lvmq.api;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
			
			int count = null == logs ? 0 : logs.size();
			long lastTime = null == logs ? 0 : logs.get(0).getCreateTime().getTime();
			
			Map<String, Object> result = new HashMap<>();
			result.put("count", Code.SHARE.MAX_TIMES - count);
			result.put("lastTime", lastTime);
			
			return new ResponseBean<>(Code.SUCCESS, Code.SUCCESS_CODE, result);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ResponseBean<>(Code.FAIL,Code.UNKOWN_CODE,e.getMessage());
		}
	}

	@ApiOperation(value="分享成功后调用", notes="分享成功后调用该接口，增加用户金币", httpMethod = "POST")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "userId", value = "用户ID", required = true, dataType = "String")
	})
	@PostMapping("success")
	@Transactional
	public ResponseBean<Object> success(String userId) {
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
			if(null != logs && logs.size() >= Code.SHARE.MAX_TIMES) {
				return new ResponseBean<>(Code.FAIL, Code.SHARE.MORE_THAN_LIMITED_TIMES.code, Code.SHARE.MORE_THAN_LIMITED_TIMES.msg);
			}
			
			//分享间隔判断
			if(null != logs && logs.size() > 0) {
				if(Calendar.getInstance().getTimeInMillis() - logs.get(0).getCreateTime().getTime() < Code.SHARE.INTERVAL_TIME * 1000) {
					return new ResponseBean<>(Code.FAIL, Code.SHARE.LESS_THAN_LIMITED_INTERVAL.code, Code.SHARE.LESS_THAN_LIMITED_INTERVAL.msg);
				}
			}
			
			Optional<UserLogin> opt = userLoginRepository.findById(userId);
			//存入日志
			GoldLog gl = new GoldLog();
			
			UserLogin user = opt.get();
			gl.setOldNum(user.getGold());
			
			user.setGold(user.getGold() + Code.SHARE.REWARD_NUMBER);
			userLoginRepository.save(user);
			
			gl.setCreateTime(Calendar.getInstance().getTime());
			gl.setCreateUser(userId);
			gl.setNewNum(user.getGold());
			gl.setNum(Code.SHARE.REWARD_NUMBER);
			gl.setType(Consts.GoldLog.Type.SHARE);
			gl.setUserId(userId);
			goldLogRepository.save(gl);
			
			//新手任务
			newerMission.entry(Consts.NewerMission.Type.SHARE, userId);
			
			return new ResponseBean<>(Code.SUCCESS, Code.SUCCESS_CODE, null);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ResponseBean<>(Code.FAIL,Code.UNKOWN_CODE,e.getMessage());
		}
	}
}
