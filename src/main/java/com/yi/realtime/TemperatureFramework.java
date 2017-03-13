package com.yi.realtime;

import com.yi.base.CommonFramework;
import com.yi.db.*;
import com.yi.exception.ExceptionHandler;
import com.yi.exception.YiException;
import com.yi.utils.DateUtils;

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
        // Start to iterate the run every 5 seconds
        while (true){
            checkTime();
            Map<Integer, Integer> stocksDistribution = new TreeMap<Integer, Integer>();
            try {
                // 1. Get all stock infor from DFCF
                System.out.println("Calculating temperature at " + DateUtils.getCurrentTimeToSecondString());
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

                Temperature temperature = new Temperature();
                temperature.setD1(stocksDistribution.containsKey(-1) ? stocksDistribution.get(-1) : 0);
                temperature.setD2(stocksDistribution.containsKey(-2) ? stocksDistribution.get(-2) : 0);
                temperature.setD3(stocksDistribution.containsKey(-3) ? stocksDistribution.get(-3) : 0);
                temperature.setD4(stocksDistribution.containsKey(-4) ? stocksDistribution.get(-4) : 0);
                temperature.setD5(stocksDistribution.containsKey(-5) ? stocksDistribution.get(-5) : 0);
                temperature.setD6(stocksDistribution.containsKey(-6) ? stocksDistribution.get(-6) : 0);
                temperature.setD7(stocksDistribution.containsKey(-7) ? stocksDistribution.get(-7) : 0);
                temperature.setD8(stocksDistribution.containsKey(-8) ? stocksDistribution.get(-8) : 0);
                temperature.setD9(stocksDistribution.containsKey(-9) ? stocksDistribution.get(-9) : 0);
                temperature.setD10(stocksDistribution.containsKey(-10) ? stocksDistribution.get(-10) : 0);
                temperature.setD11(stocksDistribution.containsKey(-11) ? stocksDistribution.get(-11) : 0);
                temperature.setI1(stocksDistribution.containsKey(1) ? stocksDistribution.get(1) : 0);
                temperature.setI2(stocksDistribution.containsKey(2) ? stocksDistribution.get(2) : 0);
                temperature.setI3(stocksDistribution.containsKey(3) ? stocksDistribution.get(3) : 0);
                temperature.setI4(stocksDistribution.containsKey(4) ? stocksDistribution.get(4) : 0);
                temperature.setI5(stocksDistribution.containsKey(5) ? stocksDistribution.get(5) : 0);
                temperature.setI6(stocksDistribution.containsKey(6) ? stocksDistribution.get(6) : 0);
                temperature.setI7(stocksDistribution.containsKey(7) ? stocksDistribution.get(7) : 0);
                temperature.setI8(stocksDistribution.containsKey(8) ? stocksDistribution.get(8) : 0);
                temperature.setI9(stocksDistribution.containsKey(9) ? stocksDistribution.get(9) : 0);
                temperature.setI10(stocksDistribution.containsKey(10) ? stocksDistribution.get(10) : 0);
                temperature.setI11(stocksDistribution.containsKey(11) ? stocksDistribution.get(11) : 0);
                System.out.println(stocksDistribution);
                TemperatureDao TemperatureDao = new TemperatureDao();
                TemperatureDao.insertTemperature(temperature);

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
