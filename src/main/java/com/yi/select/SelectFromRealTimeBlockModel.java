package com.yi.select;

import com.yi.YiConstants;
import com.yi.blocks.BlockData;
import com.yi.blocks.BlockInfoReader;
import com.yi.exception.YiException;
import com.yi.stocks.AllStocksReader;

import java.util.*;

/**
 * Created by jianguog on 17/3/5.
 *
 选出领涨板块，条件（该版块总股数目大于67个时，领涨股要占3%以上。不足67个时候，领涨股要>=2)
 选出领跌板块，条件（该版块总股数目大于20个时，领跌股要占10%以上。不足20个时候，领跌股要>=2)
 *
 */
public class SelectFromRealTimeBlockModel {
    // Key is stock name, values are the blocks that contain the stock
    TreeMap<String, HashSet<String>> commonStocksToBlocksMap;
    // Key is block name, values are stocks that belong to the block
    TreeMap<String, HashSet<String>> commonBlocksToStocksMap;

    public SelectFromRealTimeBlockModel(TreeMap<String, HashSet<String>> commonStocksToBlocksMap,
                                        TreeMap<String, HashSet<String>> commonBlocksToStocksMap) {
        this.commonStocksToBlocksMap = commonStocksToBlocksMap;
        this.commonBlocksToStocksMap = commonBlocksToStocksMap;
    }

    /**
     *
     * @param isStrongGrow
     * @param stockList
     * @return key is block name, value is BlockValues
     * @throws YiException
     *
     */
    public Map<String, BlockValues> select(boolean isStrongGrow, List<Map.Entry<String, StockValues>> stockList) throws YiException {
        // Select top blocks base on filter files
        Map<String, BlockValues> topBlockMap = selectTopBlocksList(isStrongGrow, stockList);
        for (String block: topBlockMap.keySet()) {
            topBlockMap.get(block).setStocksSet(commonBlocksToStocksMap.get(block));
            System.out.println("Got block," + block + "," + topBlockMap.get(block));
        }
        return topBlockMap;
    }

    /**
     *
     * @return key is block name, value is active stock quantity
     */
    Map<String, BlockValues> selectTopBlocksList(boolean isStrongGrow, List<Map.Entry<String, StockValues>> stockList) throws YiException {
        //1. get the block map with block name as key and related stock count as value
        Map<String, BlockValues> preSelectedBlockToStockCountMap = prepareBlockstWithStocks(isStrongGrow, stockList);
        Map<String, BlockValues> selectedBlockToStockCountMap = new HashMap<String, BlockValues>();
        for (String block : preSelectedBlockToStockCountMap.keySet()) {
            BlockValues blockValues = preSelectedBlockToStockCountMap.get(block);
            if (isStrongGrow) {
                if (blockValues.getAllBlockCount() > YiConstants.increaseBlockStockCountCondition){
                    if (blockValues.getActiveRatioInBlock() > YiConstants.increaseActiveStockRatio){
                        selectedBlockToStockCountMap.put(block, blockValues);
                    }
                } else {
                    if (blockValues.getActiveBlockCount() >= YiConstants.increaseActiveStockCount){
                        selectedBlockToStockCountMap.put(block, blockValues);
                    }
                }
            } else {
                if (blockValues.getAllBlockCount() > YiConstants.decreaseBlockStockCountCondition){
                    if (blockValues.getActiveRatioInBlock() > YiConstants.decreaseActiveStockRatio){
                        selectedBlockToStockCountMap.put(block, blockValues);
                    }
                } else {
                    if (blockValues.getActiveBlockCount() >= YiConstants.decreaseActiveStockCount){
                        selectedBlockToStockCountMap.put(block, blockValues);
                    }
                }
            }
        }
        //List<Map.Entry<String, BlockValues>> selectedBlockToStockCountList = new ArrayList<Map.Entry<String, BlockValues>>(selectedBlockToStockCountMap.entrySet());

        return selectedBlockToStockCountMap;
    }

    /**
     * get the block map with block name as key and related stock count as value
     * @return key is block name, value is active stock quantity
     */
    Map<String, BlockValues> prepareBlockstWithStocks(boolean isStrongGrow, List<Map.Entry<String, StockValues>> stockList) throws YiException {

        //Key is block name, Values are the stocks count that the block contains
        HashMap<String, BlockValues> preSelectedBlockToStockCountMap = new HashMap<String, BlockValues>();
        for(Map.Entry<String, StockValues> stockValuesEntry : stockList){
            //System.out.println(stock + " belong to " + preSelectedStocksToBlocksMap.get(stock));
            //Get all the blocks that contain the block
            String stockId = stockValuesEntry.getKey();
            if (commonStocksToBlocksMap.containsKey("SH" + stockId)){
                stockId = "SH" + stockId;
            } else if (commonStocksToBlocksMap.containsKey("SZ" + stockId)){
                stockId = "SZ" + stockId;
            } else {
                continue;
            }
            HashSet<String> blocks = commonStocksToBlocksMap.get(stockId);

            for (String block : blocks){
                BlockValues blockValues = preSelectedBlockToStockCountMap.get(block);
                if (blockValues == null) {
                    blockValues = new BlockValues();
                    blockValues.setActiveBlockCount(1);
                    blockValues.setAllBlockCount(commonBlocksToStocksMap.get(block).size());
                    preSelectedBlockToStockCountMap.put(block, blockValues);
                } else {
                    blockValues.setActiveBlockCount(blockValues.getActiveBlockCount() + 1);
                }

            }
        }
//        // set to 2 now. if stock is less than PRE_SELECTED_STOCKS_TOO_LITTLE
//        if (preSelectedBlockToStockCountMap.size() <= YiConstants.minValidPreSelectedBlockCount) {
//            throw new YiException(ExceptionHandler.PRE_SELECTED_BLOCKS_TOO_LITTLE);
//        }
        for (String block : preSelectedBlockToStockCountMap.keySet()){
            System.out.println("Got origin block,"+ block + "," + preSelectedBlockToStockCountMap.get(block));
        }
        return preSelectedBlockToStockCountMap;
    }

    public static void main(String[] args) {
        AllStocksReader allStocksReader = new AllStocksReader();
        Map<String, String> allStocksMap = allStocksReader.getStocksMap();
        try {
            BlockInfoReader commonBlockInfoReader = new BlockInfoReader(YiConstants.getSelectorPath() + YiConstants.localBlockInfoFileName);
            BlockData commonBlockData = commonBlockInfoReader.getBlockData();
            TreeMap<String, HashSet<String>> commonStocksToBlocksMap = commonBlockData.getDistinctStocksToBlocksMap(allStocksMap);
            TreeMap<String, HashSet<String>> commonBlocksToStocksMap = commonBlockData.getDistinctBlocksToStocksMap(allStocksMap);
            SelectFromRealTimeBlockModel SelectFromRealTimeBlockModel = new SelectFromRealTimeBlockModel(commonStocksToBlocksMap, commonBlocksToStocksMap);

            List<Map.Entry<String, StockValues>> stockList = new ArrayList<Map.Entry<String, StockValues>>();
            Map<String, StockValues> iM = new HashMap<String, StockValues>();
            iM.put("002761", null);
            iM.put("002307", null);
            List<Map.Entry<String, StockValues>> iL = new ArrayList<Map.Entry<String, StockValues>>(iM.entrySet());
            SelectFromRealTimeBlockModel.select(true, iL);

            Map<String, StockValues> dM = new HashMap<String, StockValues>();
            dM.put("603881", null);
            dM.put("002846", null);
            dM.put("603637", null);
            dM.put("603089", null);
            dM.put("601128", null);
            dM.put("603626", null);
            dM.put("300581", null);
            dM.put("603518", null);
            dM.put("300525", null);
            dM.put("002797", null);
            dM.put("000905", null);
            List<Map.Entry<String, StockValues>> dL = new ArrayList<Map.Entry<String, StockValues>>(dM.entrySet());
            SelectFromRealTimeBlockModel.select(false, dL);
        } catch (YiException e) {
            e.printStackTrace();
        }
    }
}
