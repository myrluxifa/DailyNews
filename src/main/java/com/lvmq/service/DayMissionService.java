package com.lvmq.service;

import com.lvmq.model.DayMission;

public interface DayMissionService {

	DayMission updateDayMission(String userId, Integer type);

	DayMission get(String userId);

}
