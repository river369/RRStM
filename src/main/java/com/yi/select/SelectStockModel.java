package com.yi.select;

import com.yi.YiConstants;
import com.yi.exception.ExceptionHandler;
import com.yi.exception.YiException;
import com.yi.realtime.DFCFRealTimeReader;
import com.yi.realtime.RealTimeData;

import java.util.*;

/**
 * Created by jianguog on 17/3/5.
 */
public class SelectStockModel {

    final String[] attributeNames = {"priceRateToYesterdayFinish", "priceRateToTodayStart", "volumeRatio",  "turnOver"};
    double[] attributeValues = new double[attributeNames.length];

    public List<Map.Entry<String, StockValues>> select(List<Map.Entry<String, BlockValues>> topBlockList) throws YiException{
        //1. get distinct blocks. Key is stock name, values are stock real time values
        Map<String, StockValues> distinctStocks = getDistinctStocks(topBlockList);
        //2. select the top stocks with all of 4 kinds values in top 10%
        List<Map.Entry<String, StockValues>> selectedStockList = selectBlocksWithRealtimeData(distinctStocks);
        return selectedStockList;
    }

    /**
     * @param topBlockList topBlockListkey is block name, values are blockvalues
     * @return key is stock name, values are stock values
     * @throws YiException
     */
    Map<String, StockValues> getDistinctStocks(List<Map.Entry<String, BlockValues>> topBlockList) throws YiException {
        // 1. Get all stock infor from DFCF
        DFCFRealTimeReader dfcfRealTimeReader = new DFCFRealTimeReader();
        Map<String, RealTimeData> dfcfRealTimeDataMap= dfcfRealTimeReader.getDFCFRealTimeData();

        // 2. Get distinct stock maps
        Map<String, StockValues> distinctStocks = new HashMap<String, StockValues>();
        for (Map.Entry<String, BlockValues> block : topBlockList) {
            //System.out.println(blockName + "," + block.getValue());
            HashSet<String> blockSet = block.getValue().getStocksSet();
            for (String stockName : blockSet) {
                if (!distinctStocks.containsKey(stockName) && dfcfRealTimeDataMap.containsKey(stockName.substring(2))) {
                    RealTimeData realTimeData = dfcfRealTimeDataMap.get(stockName.substring(2));
                    StockValues stockValues = new StockValues(realTimeData.getPrice(), realTimeData.getYesterdayFinishPrice(),
                            realTimeData.getTodayStartPrice(), realTimeData.getVolumeRatio(), realTimeData.getTurnOver());
                    distinctStocks.put(stockName, stockValues);
                }
            }
        }
        if (distinctStocks.size() <= YiConstants.minAvaialbeStockCount) {
            throw new YiException(ExceptionHandler.REALTIME_AVAILAVLE_BLOCKS_TOO_LITTLE);
        }
        //System.out.println(distinctStocks.size());
        return distinctStocks;
    }

    List<Map.Entry<String, StockValues>> selectBlocksWithRealtimeData(Map<String, StockValues> stocks) {
        // 1. set attributeValues[] values with top bestRealTimeStocksByRatio 10 percent
        List<Map.Entry<String, StockValues>> stockEntryList = new ArrayList<Map.Entry<String, StockValues>>(stocks.entrySet());
        for (int i = 0; i < attributeNames.length; i++) {
            final String attributeName = attributeNames[i];
            //降序排序
            Collections.sort(stockEntryList, new Comparator<Map.Entry<String, StockValues>>() {
                public int compare(Map.Entry<String, StockValues> o1, Map.Entry<String, StockValues> o2) {
                    return o1.getValue().getValueByType(attributeName) < o2.getValue().getValueByType(attributeName) ? 1 : -1;
                }
            });
            int selectBlockCount = (int)(stockEntryList.size() * YiConstants.bestRealTimeStocksByRatio);
            attributeValues[i] = stockEntryList.get(selectBlockCount).getValue().getValueByType(attributeName);
//            System.out.println(attributeNames[i] + "selectBlockValue="+attributeValues[i] + "selectBlockCount="+selectBlockCount);
//            for (String stock : stocks.keySet()){
//                System.out.println(stock+","+stocks.get(stock));
//            }
        }
        // 2. select best stocks with all of the column are better than values in attributeValues[]
        List<Map.Entry<String, StockValues>> selectedStocksList = new ArrayList<Map.Entry<String, StockValues>>();
        for (Map.Entry<String, StockValues> stockEntry : stockEntryList){
            boolean isAllMatch = true;
            for (int i = 0; i < attributeNames.length; i++) {
                final String attributeName = attributeNames[i];
                if (stockEntry.getValue().getValueByType(attributeName) < attributeValues[i]){
                    isAllMatch = false;
                }
            }
            if (isAllMatch) {
                //System.out.println(stockEntry.getKey() + "," + stockEntry.getValue());
                selectedStocksList.add(stockEntry);
            }
        }

        Collections.sort(selectedStocksList, new Comparator<Map.Entry<String, StockValues>>() {
            public int compare(Map.Entry<String, StockValues> o1, Map.Entry<String, StockValues> o2) {
                if (o1.getValue().getValueByType(attributeNames[0]) == o2.getValue().getValueByType(attributeNames[0]) ) {
                    return o1.getValue().getValueByType(attributeNames[1]) < o2.getValue().getValueByType(attributeNames[1]) ? 1 : -1;
                }
                return o1.getValue().getValueByType(attributeNames[0]) < o2.getValue().getValueByType(attributeNames[0]) ? 1 : -1;
            }
        });

        return selectedStocksList;
    }

}
