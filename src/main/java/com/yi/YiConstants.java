package com.yi;

import com.yi.utils.DateUtils;

/**
 * Created by jianguog on 17/2/18.
 */
public class YiConstants {
    public static String blockInfoFileString = "/Users/jianguog/other/yistock/SystemBlockInfo.new.txt";
    public static String newsCountsFilePrefix = "/Users/jianguog/other/yistock/newsCount";
    public static String newsCountsFile = YiConstants.newsCountsFilePrefix + DateUtils.getTodayString() + ".txt";
    public static String sinaSearchURL = "http://search.sina.com.cn/";
}
