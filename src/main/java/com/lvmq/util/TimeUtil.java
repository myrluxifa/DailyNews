package com.lvmq.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

import org.springframework.format.datetime.DateFormatter;

public class TimeUtil {
	
	private static String DEFAULT_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	//判断当前时间和入参时间差是否超过5分钟
	public static Boolean ifPastDue(Long time) {
		Long now=new Date().getTime();
		//2764860000l是5分钟😸
		return (now-time)>2764860000l; 
	}
	
	public static String format(Date date, String pattern) {
		pattern = Optional.ofNullable(pattern).orElse(DEFAULT_TIME_PATTERN);
		DateFormatter formatter = new DateFormatter(pattern);
		return formatter.print(date, Locale.CHINA);
	}
	
	public static String format(Date date) {
		return format(date, null);
	}
	
	public static String format(long date, String pattern) {
		return format(new Date(date), pattern);
	}
	
	public static String format(long date) {
		return format(new Date(date), null);
	}

	public static String format(Calendar instance, String pattern) {
		return format(instance.getTime(), pattern);
	}
	
	public static String format(Calendar instance) {
		return format(instance.getTime(), null);
	}
}
