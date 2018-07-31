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
import com.lvmq.model.DayShare;
import com.lvmq.model.GoldLog;
import com.lvmq.model.UserLogin;
import com.lvmq.repository.GoldLogRepository;
import com.lvmq.repository.UserLoginRepository;
import com.lvmq.service.DayShareService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"分享（邀请徒弟页面）"})
@RestController
@RequestMapping("api/dayshare")
public class DayShareAPI extends BaseAPI {

	@Autowired
	private DayShareService dayShareService;
	
	@Autowired
	private UserLoginRepository userLoginRepository;
	
	@Autowired
	private GoldLogRepository goldLogRepository;
	
	@ApiOperation(value="进入分享页面初始化", notes="", httpMethod = "POST")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "userId", value = "用户ID", required = true, dataType = "String")
	})
	@PostMapping("init")
	public ResponseBean<Object> init(String userId) {
		try {
			
			DayShare dayShare = dayShareService.getTimes(userId);
			
			int count = null == dayShare ? 0 : dayShare.getCnt();
			long lastTime = null == dayShare ? 0 : dayShare.getCreateTime().getTime();
			
			Map<String, Object> result = new HashMap<>();
			result.put("count", Code.SHARE.MAX_TIMES - count);
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
			DayShare dayShare = dayShareService.getTimes(userId);
			
			//分享次数判断
			if(null != dayShare && dayShare.getCnt() >= Code.SHARE.MAX_TIMES) {
				return new ResponseBean<>(Code.FAIL, Code.SHARE.MORE_THAN_LIMITED_TIMES.code, Code.SHARE.MORE_THAN_LIMITED_TIMES.msg);
			}
			
			//分享间隔判断
			if(null != dayShare && dayShare.getCnt() > 0) {
				if(Calendar.getInstance().getTimeInMillis() - dayShare.getCreateTime().getTime() < Code.SHARE.INTERVAL_TIME * 1000) {
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
			
			dayShareService.update(userId);
			
			return new ResponseBean<>(Code.SUCCESS, Code.SUCCESS_CODE, "成功");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ResponseBean<>(Code.FAIL,Code.UNKOWN_CODE,e.getMessage());
		}
	}
}
