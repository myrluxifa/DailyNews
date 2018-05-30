package com.lvmq.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;

import org.springframework.format.datetime.DateFormatter;

public class TimeUtil {
	
	private static String DEFAULT_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	//判断当前时间和入参时间差是否超过5分钟
	public static Boolean ifPastDue(Long time) {
		Long now=new Date().getTime();
		//2764860000l是5分钟😸
		return (now-time)>2764860000l; 
	}
	
	
	
	
	
	public static Date zeroForToday() {
		long current=System.currentTimeMillis();
		long zero=current/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset();
		return new Timestamp(zero);
	}
	
	public static Date twelveForToday() {
		long current=System.currentTimeMillis();
		long twelve=current/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset()+24*60*60*1000-1;
		return new Timestamp(twelve);
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
	
	
	public static void main(String[] args) {
		SimpleDateFormat date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(date.format(zeroForToday()));
	}
}
