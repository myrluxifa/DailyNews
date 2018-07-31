package com.lvmq.service;

import com.lvmq.model.DayShare;

public interface DayShareService {

	DayShare update(String userId);

	DayShare getTimes(String userId);

}
