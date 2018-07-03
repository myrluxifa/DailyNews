package com.lvmq.util;

import java.util.Random;

public class Util {
	
	
	public static String getRandom6() {
		java.util.Random r=new java.util.Random();
		String R="";
		for(int i=0;i<4;i++){
			R=R+String.valueOf(r.nextInt(10));
		}
		return R;
	}
	
	public static String getRandom(int length) {
		java.util.Random r=new java.util.Random();
		String R="";
		for(int i=0;i<length;i++){
			R=R+String.valueOf(r.nextInt(10));
		}
		return R;
	}

	public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }
	
	public static String getRandomReferralCode() {
		java.util.Random r=new java.util.Random();
		String R="";
		for(int i=0;i<6;i++){
			R=R+String.valueOf(r.nextInt(10));
		}
		while(R.matches("^(.)\\1+$")) {
			R="";
			for(int i=0;i<6;i++){
				R=R+String.valueOf(r.nextInt(10));
			}
		}
		return R;
	}
	
	 public static String decodeUnicode(final String dataStr) {     
         int start = 0;     
         int end = 0;     
         final StringBuffer buffer = new StringBuffer();     
         try {
         while (start > -1) {     
             end = dataStr.indexOf("\\u", start + 2);     
             String charStr = "";     
             if (end == -1) {     
                 charStr = dataStr.substring(start + 2, dataStr.length());     
             } else {     
                 charStr = dataStr.substring(start + 2, end);     
             }     
             char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。     
             buffer.append(new Character(letter).toString());     
             start = end;     
         }     
         return buffer.toString();     
         }  catch (Exception e) {
			// TODO: handle exception
        	 return dataStr;
		}
       }
	 
	 //文字轮播根据（<[]>获取随机数）
	 public static String getContent(String str) {
			Random rand = new Random();
			while(str.indexOf("<[")>0) {
				String _oldChar=str.substring(str.indexOf("<["),str.indexOf("]>")+2);
				String rule=str.substring(str.indexOf("<[")+2,str.indexOf("]>"));
				int num=Integer.valueOf(rule.substring(0,rule.indexOf("#(")));
				String section=rule.substring(rule.indexOf("#(")+2,rule.indexOf(")"));
				int max=Integer.valueOf(section.substring(section.indexOf("-")+1,section.length()));
				int min=Integer.valueOf(section.substring(0,section.indexOf("-")));
				String newChar="";
				for(int i=0;i<num;i++) {
					newChar=newChar+String.valueOf(rand.nextInt(max-min+1)+min);
				}
				
				str=str.replace(_oldChar, newChar);
			}
			return str;
		} 

}
