package com.lvmq.api;

import java.util.Calendar;
import java.util.List;
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
import com.lvmq.model.SignLog;
import com.lvmq.model.UserLogin;
import com.lvmq.repository.GoldLogRepository;
import com.lvmq.repository.SignLogRepository;
import com.lvmq.repository.UserLoginRepository;
import com.lvmq.util.TimeUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api("签到相关")
@RestController
@RequestMapping("api/sign")
public class SignAPI extends BaseAPI {
	
	@Autowired
	private SignLogRepository signLogRepository;
	
	@Autowired
	private UserLoginRepository userLogRepository;
	
	@Autowired
	private GoldLogRepository goldLogRepository;

	@ApiOperation(value = "签到", notes = "", httpMethod = "POST")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "userId", value = "用户ID", required = true, dataType = "String")
			})
	@PostMapping("do")
	@Transactional
	public ResponseBean<Object> done(String userId){
		try {
			Calendar cal = Calendar.getInstance();
			
			String today = TimeUtil.format(cal, "yyyyMMdd");
			cal.add(Calendar.DAY_OF_MONTH, -1);
			String yesterday = TimeUtil.format(cal, "yyyyMMdd");
			
			List<SignLog> logs = signLogRepository.findByCreateDateInAndUserIdOrderByCreateDateDesc(new String[] {today, yesterday}, userId);
			//是否连续签到
			boolean yes = false;
			//计算连续时间
			int coun = 1;
			if(null != logs) {
				for (SignLog sl : logs) {
					if(sl.getCreateDate().equals(today)) {
						return new ResponseBean<>(Code.FAIL, Code.FAIL, "请明天再来签到吧~");
					}
					if(sl.getCreateDate().equals(yesterday)) {
						yes = true;
						if(sl.getCountinuous() + 1 > 7) {
							coun = 7;
						}else {
							coun = sl.getCountinuous() + 1;
						}
					}
				}
			}
			
			//保存签到信息
			SignLog s = new SignLog();
			//昨天是否签到
			if(yes) {
				s.setCountinuous(coun);
			}else {
				s.setCountinuous(1);
			}
			//金额增加日志
			GoldLog gl = new GoldLog();
			
			//增加用户金币
			Optional<UserLogin> ul = userLogRepository.findById(userId);
			UserLogin user = ul.get();
			gl.setOldNum(user.getGold());
			
			user.setGold(user.getGold() + Integer.valueOf(Consts.Sign.COIN.split(",")[coun-1]));
			user = userLogRepository.save(user);
			gl.setNewNum(user.getGold());
			
			//保存签到信息
			s.setCreateDate(today);
			s.setUserId(userId);
			signLogRepository.save(s);
			
			//写入金币流水
			gl.setCreateTime(Calendar.getInstance().getTime());
			gl.setCreateUser(userId);
			gl.setNum(Long.valueOf(Consts.Sign.COIN.split(",")[coun-1]));
			gl.setType(Consts.GoldLog.Type.SIGN);
			gl.setUserId(userId);
			goldLogRepository.save(gl);
			
			return new ResponseBean<>(Code.SUCCESS, Code.SUCCESS_CODE, null);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ResponseBean<>(Code.FAIL, Code.FAIL, e.getMessage());
		}
	}
}
