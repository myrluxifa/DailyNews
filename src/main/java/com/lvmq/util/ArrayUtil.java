package com.lvmq.util;

public class ArrayUtil {

	public static String separator="$lvmq$";
	
	public static String arrayToString(String[] t) {
		String tempStr="";
		for(String s:t) {
			if(tempStr=="")tempStr=tempStr+s;
			else tempStr=tempStr+separator+s;
		}
		return tempStr;
	}
}
