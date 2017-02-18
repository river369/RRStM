package com.yi.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by jianguog on 17/2/18.
 */
public class CharsetUtils {

    public static String unicodeToGBK(String str){
        String returnStr = null;
        try {
            returnStr = URLEncoder.encode(str,"gbk");
            //returnStr = new String(str.getBytes(),"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return returnStr;
    }

}
