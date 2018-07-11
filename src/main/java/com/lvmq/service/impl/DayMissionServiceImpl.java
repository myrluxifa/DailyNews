package com.lvmq.service.impl;

import java.util.Calendar;
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
		default: return "ttglt";
		}
	}
	
	@Override
	public DayMission get(String userId) {
		String today =  DateUtils.formatDate(Calendar.getInstance().getTime(), "yyyyMMdd");
		
		DayMission dm = dayMissionRepository.findByUserIdAndMdate(userId, today);
		
		if(null == dm) {
			dm = dayMissionRepository.save(new DayMission(userId, today));
		}
		
		return dm;
	}
	
	// 更新日常任务状态
	@Override
	@Transactional
	public DayMission updateDayMission(String userId, Integer type) {
		
		String today =  DateUtils.formatDate(Calendar.getInstance().getTime(), "yyyyMMdd");
		
		DayMission dm = dayMissionRepository.findByUserIdAndMdate(userId, today);
		
		if(null == dm) {
			dm = dayMissionRepository.save(new DayMission(userId, today, assemble(type)));
			Optional<UserLogin> ouser = userLoginRepository.findById(userId);
			
			UserLogin user = ouser.get();
			
			GoldLog gl = new GoldLog(user.getId(), user.getGold() + Consts.DayMission.REWARD[type], Consts.DayMission.REWARD[type], user.getGold(), ttglt(type));
			gl = goldLogRepository.save(gl);
			user.setGold(gl.getNewNum());
			userLoginRepository.save(user);
		}else if("0".equals(paramValue(dm.getParam(), type))) {
			Optional<UserLogin> ouser = userLoginRepository.findById(userId);
			
			UserLogin user = ouser.get();
			
			GoldLog gl = new GoldLog(user.getId(), user.getGold() + Consts.DayMission.REWARD[type], Consts.DayMission.REWARD[type], user.getGold(), ttglt(type));
			gl = goldLogRepository.save(gl);
			user.setGold(gl.getNewNum());
			userLoginRepository.save(user);
		}
		
		dm.setParam(assemble(dm.getParam(), type));
		
		dayMissionRepository.saveAndFlush(dm);
		
		
	
		return dm;
	}
	
}
