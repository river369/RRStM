package com.yi.temperature;

import com.yi.EnvConstants;
import com.yi.YiConstants;
import com.yi.base.CommonFramework;
import com.yi.db.Selection;
import com.yi.db.SelectionDao;
import com.yi.db.SelectionItem;
import com.yi.exception.ExceptionHandler;
import com.yi.exception.YiException;
import com.yi.realtime.DFCFRealTimeReader;
import com.yi.realtime.RealTimeData;
import com.yi.select.SelectModel;
import com.yi.select.StockOutput;
import com.yi.select.StockValues;
import com.yi.stocks.AllStocksReader;
import com.yi.utils.DateUtils;
import com.yi.utils.OSSUtil;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by jianguog on 17/3/7.
 */
public class TemperatureFramework  extends CommonFramework {

    public TemperatureFramework(boolean alwayRun) {
        super(alwayRun);
    }

    public void run(){
        checkTime();
//        // 1. Load all stocks from website
//        long start = System.currentTimeMillis();
//        // Read all stocks from website
//        AllStocksReader allStocksReader = new AllStocksReader();
//        // key is stock code, value is stock name
//        Map<String, String> allStocksMap = allStocksReader.getStocksMap();
//        if (allStocksMap.size() < 3000) {
//            System.out.println("Exit since stock size is abnormal. The value is " + allStocksMap.size());
//            System.exit(-1000);
//        }

        // Start to iterate the run every 5 seconds
        while (true){
            checkTime();
            Map<Integer, Integer> stocksDistribution = new TreeMap<Integer, Integer>();
            try {
                // 1. Get all stock infor from DFCF
                DFCFRealTimeReader dfcfRealTimeReader = new DFCFRealTimeReader();
                Map<String, RealTimeData> dfcfRealTimeDataMap = dfcfRealTimeReader.getDFCFRealTimeData();

                for (RealTimeData realTimeData : dfcfRealTimeDataMap.values()){
                    float change = ((realTimeData.getPrice()/realTimeData.getTodayStartPrice()) - 1 ) * 100;
                    Integer range = (int)(change > 0 ? Math.ceil(change) : Math.floor(change));
                    //if (allStocksMap.containsKey(realTimeData.getId())) {
                        //System.out.println(realTimeData.getPrice() + "    " + realTimeData.getTodayStartPrice() + "  "+ change+ "  " + range);
                        Integer count = stocksDistribution.get(range);
                        if (count == null) {
                            stocksDistribution.put(range, new Integer(1));
                        } else {
                            stocksDistribution.put(range, new Integer(count + 1));
                        }
                    //}
                }
                System.out.println(stocksDistribution);

            } catch (YiException e) {
                ExceptionHandler.HandleException(e);
            }
            sleep(5000);
        }

    }

    public static void main(String[] args) {
        boolean alwaysRun = true;
        //System.out.println(args[0]);
        if (args != null && args.length > 0 && null != args[0] && "0".equalsIgnoreCase(args[0])){
            alwaysRun = false;
        }
        //System.out.println(alwaysRun);
        TemperatureFramework temperatureFramework = new TemperatureFramework(alwaysRun);
        temperatureFramework.run();
    }
}
