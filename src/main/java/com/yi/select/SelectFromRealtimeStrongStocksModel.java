package com.yi.select;

import com.yi.YiConstants;
import com.yi.exception.ExceptionHandler;
import com.yi.exception.YiException;
import com.yi.realtime.DFCFRealTimeReader;
import com.yi.realtime.RealTimeData;

import java.util.*;

/**
 * Created by jianguog on 17/3/5.
 根据实时数据四个参数中的后三个选出领涨股（涨幅排名为前10％，turnover，volumeratio也前10%大），
 选出领跌股（跌幅排名为后10％，turnover，volumeratio也前10%大））
 */
public class SelectFromRealtimeStrongStocksModel {

    final String[] attributeNames = { "priceRateToTodayStart", "volumeRatio",  "turnOver"};
    double[] attributeValues = new double[attributeNames.length];
    // key is stock code, value is stock name
    Map<String, String> allStocksMap;
    // key is stock, value is real time data
    Map<String, RealTimeData> dfcfRealTimeDataMap;

    public SelectFromRealtimeStrongStocksModel(Map<String, String> allStocksMap, Map<String, RealTimeData> dfcfRealTimeDataMap) {
        this.allStocksMap = allStocksMap;
        this.dfcfRealTimeDataMap = dfcfRealTimeDataMap;
    }

    public List<Map.Entry<String, StockValues>> select(boolean isStrongGrow) throws YiException{
        Map<String, StockValues> allRealtimeStockMap = prepareStockMap(isStrongGrow);

        // 1. set attributeValues[] values with top bestRealTimeStocksByRatio 10 percent
        //    if strong decrease, use 90 percent
        List<Map.Entry<String, StockValues>> stockEntryList = new ArrayList<Map.Entry<String, StockValues>>(allRealtimeStockMap.entrySet());
        for (int i = 0; i < attributeNames.length; i++) {
            final String attributeName = attributeNames[i];
            //降序排序
            Collections.sort(stockEntryList, new Comparator<Map.Entry<String, StockValues>>() {
                public int compare(Map.Entry<String, StockValues> o1, Map.Entry<String, StockValues> o2) {
                    return o1.getValue().getValueByType(attributeName) < o2.getValue().getValueByType(attributeName) ? 1 : -1;
                }
            });

            int selectBlockCount = (int)(stockEntryList.size() * YiConstants.bestRealTimeStocksByRatio);
            if (!isStrongGrow && (i == 0)){
                selectBlockCount = (int)(stockEntryList.size() * ( 1 - YiConstants.bestRealTimeStocksByRatio));
            }
            attributeValues[i] = stockEntryList.get(selectBlockCount).getValue().getValueByType(attributeName);
//            System.out.println(attributeNames[i] + "selectBlockValue="+attributeValues[i] + "selectBlockCount="+selectBlockCount);
//            for (String stock : stocks.keySet()){
//                System.out.println(stock+","+stocks.get(stock));
//            }
        }

        // 2. select best stocks with all of the column are better than values in attributeValues[],
        //    if strong decrease, use 90 percent, get the smallest values which is the most decrease
        List<Map.Entry<String, StockValues>> selectedStocksList = new ArrayList<Map.Entry<String, StockValues>>();
        for (Map.Entry<String, StockValues> stockEntry : stockEntryList){
            boolean isAllMatch = true;
            for (int i = 0; i < attributeNames.length; i++) {
                final String attributeName = attributeNames[i];
                if (isStrongGrow || (i > 0)){
                    if (stockEntry.getValue().getValueByType(attributeName) < attributeValues[i]){
                        isAllMatch = false;
                    }
                } else {
                    if (stockEntry.getValue().getValueByType(attributeName) > attributeValues[i]){
                        isAllMatch = false;
                    }
                }
            }
            if (isAllMatch) {
                //System.out.println(stockEntry.getKey() + "," + stockEntry.getValue());
                selectedStocksList.add(stockEntry);
            }
        }

        for (Map.Entry<String, StockValues> stockEntry : selectedStocksList) {
            System.out.println("Got stock," + stockEntry.getKey() + "," + stockEntry.getValue());
        }
        return selectedStocksList;
    }

    /**
     *
     * @return key is stock, value is stock values
     * @throws YiException
     */
    Map<String, StockValues> prepareStockMap(boolean isStrongGrow) throws YiException {
        // 1. Get distinct stock maps
        Map<String, StockValues> distinctStocks = new HashMap<String, StockValues>();
        for (String stock: dfcfRealTimeDataMap.keySet()) {
            if (!allStocksMap.containsKey(stock)) continue;
            RealTimeData realTimeData = dfcfRealTimeDataMap.get(stock);
            StockValues stockValues = new StockValues(realTimeData.getPrice(), realTimeData.getYesterdayFinishPrice(),
                    realTimeData.getTodayStartPrice(), realTimeData.getVolumeRatio(), realTimeData.getTurnOver());
            if (isStrongGrow && (realTimeData.getPrice() > realTimeData.getTodayStartPrice())) {
                //System.out.println(stock + "," + stockValues);
                distinctStocks.put(stock, stockValues);
            } else if(!isStrongGrow && (realTimeData.getPrice() < realTimeData.getTodayStartPrice())) {
                //System.out.println(stock + "," + stockValues);
                distinctStocks.put(stock, stockValues);
            }
        }
        return distinctStocks;
    }

    public static void main(String[] args) {
        Map<String, String> allStocksMap = new HashMap<String, String>();
        Map<String, RealTimeData> dfcfRealTimeDataMap = null;
        try {
            DFCFRealTimeReader dfcfRealTimeReader = new DFCFRealTimeReader();
            dfcfRealTimeDataMap = dfcfRealTimeReader.getDFCFRealTimeData();
            for (String stock : dfcfRealTimeDataMap.keySet()){
                allStocksMap.put(stock, "");
            }
            //System.out.println(allStocksMap.size());
            SelectFromRealtimeStrongStocksModel selectFromRealtimeStrongStocksModel = new SelectFromRealtimeStrongStocksModel(
                    allStocksMap, dfcfRealTimeDataMap);
            List<Map.Entry<String, StockValues>> stockList = selectFromRealtimeStrongStocksModel.select(true);
            System.out.println("Major increase!");
            for (Map.Entry<String, StockValues> stockEntry : stockList) {
                System.out.println(stockEntry.getKey() + "," + stockEntry.getValue());
            }
            stockList = selectFromRealtimeStrongStocksModel.select(false);
            System.out.println("Major decrease!");
            for (Map.Entry<String, StockValues> stockEntry : stockList) {
                System.out.println(stockEntry.getKey() + "," + stockEntry.getValue());
            }

        } catch (YiException e) {
            ExceptionHandler.HandleException(e);
        }
    }
}
