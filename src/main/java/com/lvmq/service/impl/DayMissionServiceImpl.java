package com.lvmq.service.impl;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.lvmq.base.Consts;
import com.lvmq.model.DayMission;
import com.lvmq.model.GoldLog;
import com.lvmq.model.UserLogin;
import com.lvmq.repository.DayMissionRepository;
import com.lvmq.repository.GoldLogRepository;
import com.lvmq.repository.UserLoginRepository;
import com.lvmq.service.DayMissionService;

@Service
public class DayMissionServiceImpl implements DayMissionService {

	@Autowired
	private DayMissionRepository dayMissionRepository;

	@Autowired
	private UserLoginRepository userLoginRepository;
	
	@Autowired
	private GoldLogRepository goldLogRepository;
	/**
	 * 处理状态串
	 * 
	 * @param value
	 * @param index
	 * @return
	 */
	public String assemble(String value, int index) {
		
		String[] vs = value.split("\\|");
		StringBuffer ns = new StringBuffer("");
		
		for (int i = 0; i < vs.length; i++) {
			if(!StringUtils.isEmpty(ns.toString())) {
				ns.append("|");
			}
			if(index == i) {
				ns.append(Integer.valueOf(vs[i]) + 1);				
			}else {
				ns.append(vs[i]);	
			}
		}
		
		return ns.toString();
	}
	
	
	public String assemble(String value) {
		return assemble(value, -1);
	}
	
	public String assemble(Integer index) {
		return assemble("0|0|0|0|0", index);
	}
	
	public String paramValue(String param, Integer index) {
		return param.split("\\|")[index];
	}
	
	public String ttglt(Integer type){
		switch(type) {
		case 0: return Consts.GoldLog.Type.INVITE;
		case 1: return Consts.GoldLog.Type.DAY_MISSION_READ;
		case 2: return Consts.GoldLog.Type.DAY_MISSION_SHARE;
		case 3: return Consts.GoldLog.Type.SHOW;
		case 4: return Consts.GoldLog.Type.COMMENT;
		case 5: return Consts.GoldLog.Type.DAY_MISSION_OTHER_INVITE;
		default: return "ttglt";
		}
	}
	
	@Override
	public DayMission get(String userId) {
		String today =  DateUtils.formatDate(Calendar.getInstance().getTime(), "yyyyMMdd");
		
		List<DayMission> dms = dayMissionRepository.findByUserIdAndMdate(userId, today);
		
		if(dms.size() == 0) {
			dms.set(0, dayMissionRepository.save(new DayMission(userId, today)));
		}
		
		return dms.get(0);
	}
	
	// 更新日常任务状态
	@Override
	@Transactional
	public DayMission updateDayMission(String userId, Integer type) {
		
		String today =  DateUtils.formatDate(Calendar.getInstance().getTime(), "yyyyMMdd");
		
		List<DayMission> dms = dayMissionRepository.findByUserIdAndMdate(userId, today);
		
		if(dms.size() == 0) {
			dms.set(0, dayMissionRepository.save(new DayMission(userId, today, assemble(type))));
			Optional<UserLogin> ouser = userLoginRepository.findById(userId);
			
			UserLogin user = ouser.get();
			
			GoldLog gl = new GoldLog(user.getId(), user.getGold() + Consts.DayMission.REWARD[type], Consts.DayMission.REWARD[type], user.getGold(), ttglt(type));
			gl = goldLogRepository.save(gl);
			user.setGold(gl.getNewNum());
			userLoginRepository.save(user);
		}else if("0".equals(paramValue(dms.get(0).getParam(), type)) || Consts.DayMission.Type.INVITE == type) {
			if(type == Consts.DayMission.Type.INVITE) {
				type = Consts.DayMission.Type.OTHER_INVITE;
			}
			Optional<UserLogin> ouser = userLoginRepository.findById(userId);
			
			UserLogin user = ouser.get();
			
			GoldLog gl = new GoldLog(user.getId(), user.getGold() + Consts.DayMission.REWARD[type], Consts.DayMission.REWARD[type], user.getGold(), ttglt(type));
			gl = goldLogRepository.save(gl);
			user.setGold(gl.getNewNum());
			userLoginRepository.save(user);
		}
		
		dms.get(0).setParam(assemble(dms.get(0).getParam(), type));
		
		dayMissionRepository.saveAndFlush(dms.get(0));
		
		
	
		return dms.get(0);
	}
	
	@Override
	public void reward(String userId, String type, long reward) {
		Optional<UserLogin> ouser = userLoginRepository.findById(userId);
		UserLogin user = ouser.get();
		GoldLog gl = new GoldLog(user.getId(), user.getGold() + reward, reward, user.getGold(), type);
		gl = goldLogRepository.save(gl);
		user.setGold(gl.getNewNum());
		userLoginRepository.save(user);
	}
}
