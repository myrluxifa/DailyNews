package com.lvmq.util;

public class Util {

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
}
