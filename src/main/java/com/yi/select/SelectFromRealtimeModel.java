package com.yi.select;

import com.yi.YiConstants;
import com.yi.blocks.BlockData;
import com.yi.blocks.BlockInfoReader;
import com.yi.exception.YiException;
import com.yi.realtime.DFCFRealTimeReader;
import com.yi.realtime.RealTimeData;
import com.yi.stocks.AllStocksReader;

import java.util.*;

/**
 * Created by jianguog on 17/3/7.
 */
public class SelectFromRealtimeModel {

    /**
     *
     * @param allStocksMap key is stock code, value is stock name
     * @return
     * @throws YiException
     */
    public List<StockOutput> select(Map<String, String> allStocksMap ) throws YiException {
        // 0 Build common data. read common stock-block mapping from local static file
        BlockInfoReader commonBlockInfoReader = new BlockInfoReader(YiConstants.getSelectorPath() + YiConstants.localBlockInfoFileName);
        BlockData commonBlockData = commonBlockInfoReader.getBlockData();
        TreeMap<String, HashSet<String>> commonStocksToBlocksMap = commonBlockData.getDistinctStocksToBlocksMap(allStocksMap);
        TreeMap<String, HashSet<String>> commonBlocksToStocksMap = commonBlockData.getDistinctBlocksToStocksMap(allStocksMap);
        // 0 Get real time data
        DFCFRealTimeReader dfcfRealTimeReader = new DFCFRealTimeReader();
        Map<String, RealTimeData> dfcfRealTimeDataMap = dfcfRealTimeReader.getDFCFRealTimeData();

        // 1 select best stock then select best block
        SelectFromRealtimeStrongStocksModel selectFromRealtimeStrongStocksModel =
                new SelectFromRealtimeStrongStocksModel(allStocksMap, commonStocksToBlocksMap, dfcfRealTimeDataMap);
        SelectFromRealTimeBlockModel selectFromRealTimeBlockModel = new SelectFromRealTimeBlockModel(commonStocksToBlocksMap, commonBlocksToStocksMap);

        System.out.println("Getting Major increase Stocks!");
        List<Map.Entry<String, StockValues>> majorIncreaseStockList = selectFromRealtimeStrongStocksModel.select(true);
        System.out.println("Getting Major increase Blocks!");
        Map<String, BlockValues> majorIncreaseBlockMap = selectFromRealTimeBlockModel.select(true, majorIncreaseStockList);

        System.out.println("Getting Major decrease Stocks!");
        List<Map.Entry<String, StockValues>> majorDecreaseStockList = selectFromRealtimeStrongStocksModel.select(false);
        System.out.println("Getting Major dncrease Blocks!");
        Map<String, BlockValues> majorDecreaseBlockMap = selectFromRealTimeBlockModel.select(false, majorDecreaseStockList);

        System.out.println("Major increase Blocks - Major decrease Blocks!");
        Map<String, BlockValues> pureMajorIncreaseBlockMap = new HashMap<String, BlockValues>();
        for (String block : majorIncreaseBlockMap.keySet()) {
            if (!majorDecreaseBlockMap.containsKey(block)) {
                pureMajorIncreaseBlockMap.put(block, majorIncreaseBlockMap.get(block));
            }
        }
        System.out.println("Remaining Major increase Blocks");
        for (String block : pureMajorIncreaseBlockMap.keySet()) {
            System.out.println("Got Remaining block, " + block + ","+pureMajorIncreaseBlockMap.get(block));
        }

        List<Map.Entry<String, BlockValues>> selectedBlockToStockCountList =
                new ArrayList<Map.Entry<String, BlockValues>>(pureMajorIncreaseBlockMap.entrySet());

        // 2. Select the best stocks in the best blocks
        SelectStocksInBlocksModel selectStocksInBlocksModel = new SelectStocksInBlocksModel(dfcfRealTimeDataMap, commonStocksToBlocksMap);
        List<Map.Entry<String, StockValues>> selectedStockList = selectStocksInBlocksModel.select(selectedBlockToStockCountList);

        // 3 orgnize the output data
        List<StockOutput> stockOutputsList = new ArrayList<StockOutput>();
        for (int i = 0 ; i < selectedStockList.size(); i++) {
            String stockId = selectedStockList.get(i).getKey();
            StockOutput stockOutput = new StockOutput(stockId, allStocksMap.get(stockId.substring(2)),
                    selectedStockList.get(i).getValue());
            stockOutputsList.add(stockOutput);
        }
        return stockOutputsList;
    }
    public static void main(String[] args) {
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        AllStocksReader allStocksReader = new AllStocksReader();
        Map<String, String> allStocksMap = allStocksReader.getStocksMap();
        SelectFromRealtimeModel selectFromRealtimeModel = new SelectFromRealtimeModel();

        try {
            selectFromRealtimeModel.select(allStocksMap);
        } catch (YiException e) {
            e.printStackTrace();
        }
    }

}
