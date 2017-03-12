package com.yi.base;

import com.yi.EnvConstants;
import com.yi.YiConstants;
import com.yi.db.Selection;
import com.yi.db.SelectionDao;
import com.yi.db.SelectionItem;
import com.yi.exception.ExceptionHandler;
import com.yi.exception.YiException;
import com.yi.select.SelectModel;
import com.yi.select.StockOutput;
import com.yi.select.StockValues;
import com.yi.stocks.AllStocksReader;
import com.yi.utils.DateUtils;
import com.yi.utils.OSSUtil;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by jianguog on 17/3/7.
 */
public class CommonFramework {
    public boolean alwayRun;

    public CommonFramework(boolean alwayRun) {
        this.alwayRun = alwayRun;
    }

    public void checkTime(){
        if (alwayRun) return;
        int hour = DateUtils.getCurrentHour();
        while (hour < 9 || hour > 15 ) {
            System.out.println("The job only run between 9:00 and 15:00");
            sleep(60000);
        }
    }

    public void sleep(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
