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
public class SelectStocksInBlocksModel {

    //final String[] attributeNames = {"priceRateToYesterdayFinish", "priceRateToTodayStart", "volumeRatio",  "turnOver"};
    final String[] attributeNames = {"priceRateToTodayStart", "volumeRatio",  "turnOver"};
    double[] attributeValues = new double[attributeNames.length];
    // Key is stock name, values are the blocks that contain the stock
    TreeMap<String, HashSet<String>> commonStocksToBlocksMap;
    // Key is stock
    Map<String, RealTimeData> dfcfRealTimeDataMap;

    public SelectStocksInBlocksModel(Map<String, RealTimeData> dfcfRealTimeDataMap, TreeMap<String, HashSet<String>> commonStocksToBlocksMap) {
        this.commonStocksToBlocksMap = commonStocksToBlocksMap;
        this.dfcfRealTimeDataMap = dfcfRealTimeDataMap;
    }

    public List<Map.Entry<String, StockValues>> select(List<Map.Entry<String, BlockValues>> topBlockList) throws YiException{
        //1. get distinct blocks. Key is stock name, values are stock real time values
        System.out.println("Getting all the stocks in the Major increase Blocks.");
        Map<String, StockValues> distinctStocks = getDistinctStocksWithRealtimeValues(topBlockList);
        for (String stock : distinctStocks.keySet()) {
            System.out.println("Got stocks in block, " + stock + "," + distinctStocks.get(stock));
        }
        //2. select the top stocks with all of 3 kinds values in top 10%
        System.out.println("Getting best the stocks in the Major increase Blocks.");
        List<Map.Entry<String, StockValues>> selectedStockList = selectBlocksWithRealtimeData(distinctStocks);
        for (Map.Entry<String, StockValues> stockValuesEntry : selectedStockList) {
            System.out.println("Got best stocks in block, " + stockValuesEntry.getKey() + "," + stockValuesEntry.getValue());
        }
        return selectedStockList;
    }

    /**
     * Get distinct stocks and it real time values
     * @param topBlockList topBlockListkey is block name, values are blockvalues
     * @return key is stock name, values are stock values
     * @throws YiException
     */
    Map<String, StockValues> getDistinctStocksWithRealtimeValues(List<Map.Entry<String, BlockValues>> topBlockList) throws YiException {
        Map<String, StockValues> distinctStocks = new HashMap<String, StockValues>();
        for (Map.Entry<String, BlockValues> block : topBlockList) {
            //System.out.println(blockName + "," + block.getValue());
            HashSet<String> stockSet = block.getValue().getStocksSet();
            for (String stockName : stockSet) {
                if (!distinctStocks.containsKey(stockName) && dfcfRealTimeDataMap.containsKey(stockName.substring(2))) {
                    RealTimeData realTimeData = dfcfRealTimeDataMap.get(stockName.substring(2));
                    StockValues stockValues = new StockValues(realTimeData.getPrice(), realTimeData.getYesterdayFinishPrice(),
                            realTimeData.getTodayStartPrice(), realTimeData.getVolumeRatio(), realTimeData.getTurnOver());
                    stockValues.setBelongToBlocks(commonStocksToBlocksMap.get(stockName));
                    distinctStocks.put(stockName, stockValues);
                }
                StockValues stockValues = distinctStocks.get(stockName);
                if (stockValues != null) {
                    stockValues.getDrivenByBlocks().add(block.getKey());
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
                if ((stockEntry.getValue().getPriceRateToYesterdayFinish() - 1) > YiConstants.filterOutRateForAlreadyIncreasedTooMuch ||
                        (stockEntry.getValue().getPriceRateToTodayStart() - 1) > YiConstants.filterOutRateForAlreadyIncreasedTooMuch) {
                    System.out.println("Skip best stock that increased too much," + stockEntry.getKey() + "," + stockEntry.getValue() );
                } else {
                    //System.out.println(stockEntry.getKey() + "," + stockEntry.getValue());
                    selectedStocksList.add(stockEntry);
                }
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
