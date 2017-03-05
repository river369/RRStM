package com.yi;

import com.yi.utils.DateUtils;

/**
 * Created by jianguog on 17/2/18.
 */
public class YiConstants {
    //public static String blockInfoFileString = "/Users/jianguog/other/yistock/SystemBlockInfo.new.txt";
    public static String blockInfoFileString = "/Users/jianguog/other/yistock/SystemBlockInfo.txt";
    //public static String blockInfoFileWithFilterString = "/Users/jianguog/other/yistock/UserBlockInfo.txt";
    public static String preSelectedBlockFileString = "/Users/jianguog/other/yistock/UserBlockInfo-orig.txt";

    public static String newsCountsFilePrefix = "/Users/jianguog/other/yistock/newsCount";
    public static String newsCountsFile = YiConstants.newsCountsFilePrefix + DateUtils.getTodayString() + ".txt";
    public static String sinaSearchURL = "http://search.sina.com.cn/";
    public static String eastMoneyUrl = "http://quote.eastmoney.com/stocklist.html";

    public static String dfcfURL = "http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx";
    public static String dfcfParameter = "type=CT&cmd=C._A&sty=FCOIATA&sortType=A&sortRule=1&page=1&pageSize=10000&js=var%20quote_123%3d{rank:[(x)],pages:(pc)}&token=7bc05d0d4c3c22ef9fca8c2a912d779c&jsName=quote_123&_g=0.8386441415641457";

    public static String gbkCharset = "GBK";
}
