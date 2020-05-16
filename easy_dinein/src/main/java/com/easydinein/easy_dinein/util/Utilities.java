package com.easydinein.easy_dinein.util;


public class Utilities {
    private static final String ALPHA_NUM ="0123456789abcdef";

    public static String getAuthKey(int len) {
        StringBuffer sb = new StringBuffer(len);

        for (int i=0; i<len; i++) {
            int num = (int)(Math.random()*ALPHA_NUM.length());
            sb.append(ALPHA_NUM.charAt(num));
        }
        return sb.toString();
    }
}
