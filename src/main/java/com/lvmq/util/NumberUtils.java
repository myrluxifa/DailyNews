package com.lvmq.util;

import java.text.DecimalFormat;

import org.springframework.util.StringUtils;

public class NumberUtils {
	private static final String DEFAULT_PARTTERN = "#.##";
	private static final String DEFAULT_FEE = "0.00";
	private static DecimalFormat decimalFormat = new DecimalFormat(DEFAULT_PARTTERN);
	
	/**
	 * 金额，精确两位小数
	 * 
	 * @param fee
	 * @return
	 */
	public static String feeFormat(Double fee) {
		return format(fee, DEFAULT_FEE);
	}
	/**
	 * 只保留两位不为0的小数位
	 * 
	 * @param num
	 * @return
	 */
	public static String format(Double num) {
		return format(num, null);
	}
	
	/**
	 * parttern 0.00 #.## 文字##等
	 * 
	 * @param num
	 * @param parttern
	 * @return
	 */
	public static String format(Double num, String parttern) {
		if(!StringUtils.isEmpty(parttern)) {
			decimalFormat = new DecimalFormat(parttern);
		}else {
			decimalFormat = new DecimalFormat(DEFAULT_PARTTERN);			
		}
		return decimalFormat.format(num);
	}
	
	/**
	 * 距离转千米
	 * 
	 * @param meters
	 * @return
	 */
	public static String distance(long meters) {
		if(meters > 1000) {
			return format((double)meters / 1000, "#.#") + "km";
		}else if(meters < 500) {
			return "<500m";
		}else {
			return meters + "m";
		}
	}
	
	public static void main(String[] args) {
		System.out.println(distance(400));
	}
}
