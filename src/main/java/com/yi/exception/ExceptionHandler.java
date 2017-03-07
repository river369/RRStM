package com.yi.exception;

/**
 * Created by jianguog on 17/3/7.
 */
public class ExceptionHandler {
    public static String WAITING_SELECT_STOCKS_FILE = "选股没有完成，等待选股开始.";
    public static String SYSTEM_BLOCK_INFO_STOCKS_COUNT_TOO_LOW = "SystemBlockInfo股票数量异常.";
    public static String SYSTEM_BLOCK_INFO_BLOCKS_COUNT_TOO_LOW = "SystemBlockInfo板块数量异常.";
    public static String PRE_SELECTED_STOCKS_TOO_LITTLE = "选股没有完成，可选股数量太少.";
    public static String PRE_SELECTED_BLOCKS_TOO_LITTLE = "选股没有完成，可选板块量太少.";
    public static String SELECTED_BLOCKS_TOO_LITTLE = "二次板块选取没有完成，选出板块量太少.";
    public static String DFCF_REAL_TIME_STOCKS_COUNT_TOO_LOW = "DFCF实时股票数量异常.";

    public static void HandleException(YiException e){
        if (e.getMessage().equalsIgnoreCase(SYSTEM_BLOCK_INFO_STOCKS_COUNT_TOO_LOW) ||
                e.getMessage().equalsIgnoreCase(SYSTEM_BLOCK_INFO_BLOCKS_COUNT_TOO_LOW)){
            e.printStackTrace();
            System.exit(-1001);
        }
        System.out.println(e.getMessage());
    }
}
