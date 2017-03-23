package com.yi;

import com.yi.utils.DateUtils;

/**
 * Created by jianguog on 17/2/18.
 */
public class YiConstants {
    //=======================================
    //  Common parameters
    //=======================================
    // kt files
    public static String localBlockInfoFileName = "SystemBlockInfo.data";
    // realtime data from dfcf
    public static String dfcfURL = "http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx";
    public static String dfcfParameter = "type=CT&cmd=C._A&sty=FCOIATA&sortType=A&sortRule=1&page=1&pageSize=10000&js=var%20quote_123%3d{rank:[(x)],pages:(pc)}&token=7bc05d0d4c3c22ef9fca8c2a912d779c&jsName=quote_123&_g=0.8386441415641457";
    public static String gbkCharset = "GBK";

    // service files path
    public static String getSelectorPath() {
        String path = "/home/stock/input/";
        String os = System.getProperty("os.name");
        if(os.toLowerCase().startsWith("mac")){
            path = "/Users/jianguog/other/yistock/server/";
        }
        return path;
    }

    //=======================================
    //  KT file Solution parameters
    //=======================================
    public static String localPreSelectedBlockFileName = "UserBlockInfo1390944714278353.data";

    // local files and fitler from kt 交易师
    public static String getKTPath() {
        String path = "D:\\KT\\User\\BlockInfo\\";
        String os = System.getProperty("os.name");
        if(os.toLowerCase().startsWith("mac")){
            path = "/Users/jianguog/other/yistock/";
        }
        return path;
    }

//    public static String majorIncrease = "MIDD主升.";
//    public static String followIncrease = "股池2：量单.";
    public static String majorIncrease = "主升.";
    public static String followIncrease = "MIDD主升.";

    // Stock select parameter
    public static int minValidPreSelectedStockCount = 10;
    public static int minValidPreSelectedBlockCount = 2;
    public static int minSelectedBlockCount = 0;

    public static int minAvaialbeStockCount = 2;

    public static double bestSelectedStocksByRatio = 0.1; //better than other 10%, top 90%
    public static double bestSelectedStocksByMinCount = 2;
    public static double bestSelectedStocksByActiveRatio = 0.6; //better than other 40%, top 60%
    public static double bestSelectedStocksByActiveRatioMin = 0.2;

    public static double bestRealTimeStocksByRatio = 0.1;

    // Monitor threshhold
    public static int minCommonStockCount = 2000;
    public static int minCommonBlockCount = 150;
    public static int minTechValidePreSelectedBlockCount = 0;
    public static int minDFCFRealTimeStockCount = 1000;
    public static int maxDFCFRealTimeStockSkipCount = 1000;

    public static double filterOutRateForAlreadyIncreasedTooMuch = 0.05;

    //=======================================
    //  KT file Solution parameters
    //=======================================
    public static int increaseBlockStockCountCondition = 67;
    public static double increaseActiveStockRatio = 0.03;
    public static int increaseActiveStockCount = 2;
    public static int decreaseBlockStockCountCondition = 20;
    public static double decreaseActiveStockRatio = 0.1;
    public static int decreaseActiveStockCount = 2;

//    public static int increaseBlockStockCountCondition = 67;
//    public static double increaseActiveStockRatio = 0;
//    public static int increaseActiveStockCount = 0;
//    public static int decreaseBlockStockCountCondition = 20;
//    public static double decreaseActiveStockRatio = 0;
//    public static int decreaseActiveStockCount = 0;

    //=======================================
    // News Solution parameters
    //=======================================
    // News related
    public static String newsCountsFilePrefix = "/Users/jianguog/other/yistock/newsCount";
    public static String newsCountsFile = YiConstants.newsCountsFilePrefix + DateUtils.getTodayString() + ".txt";
    public static String sinaSearchURL = "http://search.sina.com.cn/";
    public static String eastMoneyUrl = "http://quote.eastmoney.com/stocklist.html";
}
