package com.lvmq.util;

import java.util.ArrayList;
import java.util.List;

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
	
	public static List<String> stringToList(String s) {
		if(Util.isBlank(s)) {
			return null;
		}
		String[] ss=s.split("\\$lvmq\\$");
		int size=(s.length()-s.replace(separator, "").length())/6+1;
		List<String> sArray=new ArrayList<String>();
		for(int i=0;i<size;i++) {
			System.out.println(ss[i]);
			sArray.add(ss[i]);
		}
		return sArray;
	}
	
	public static String getNewsTypeOrAdType(String s) {
		if(Util.isBlank(s)) {
			return "0";
		}
		String[] ss=s.split("\\$lvmq\\$");
		int size=(s.length()-s.replace(separator, "").length())/6+1;
		
		if(size>=3) return "3"; 
		else return "1";
	}
	
}
