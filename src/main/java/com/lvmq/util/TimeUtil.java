package com.lvmq.util;

import java.util.Date;

public class TimeUtil {

	//判断当前时间和入参时间差是否超过5分钟
	public static Boolean ifPastDue(Long time) {
		Long now=new Date().getTime();
		//2764860000l是5分钟😸
		return (now-time)>2764860000l; 
	}
}
