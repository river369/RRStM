package com.yi.select;

import com.yi.YiConstants;
import com.yi.blocks.BlockData;
import com.yi.blocks.BlockInfoReader;
import com.yi.exception.ExceptionHandler;
import com.yi.exception.YiException;
import com.yi.stocks.AllStocksReader;

import java.util.*;

/**
 * Created by jianguog on 17/3/5.
 */
public class SelectBlockModel {
    // key is stock code, value is stock name
    Map<String, String> allStocksMap;
    // Key is stock name, values are the blocks that contain the stock
    TreeMap<String, HashSet<String>> commonStocksToBlocksMap;
    // Key is block name, values are stocks that belong to the block
    TreeMap<String, HashSet<String>> commonBlocksToStocksMap;

    public SelectBlockModel(Map<String, String> allStocksMap,
                            TreeMap<String, HashSet<String>> commonStocksToBlocksMap,
                            TreeMap<String, HashSet<String>> commonBlocksToStocksMap) {
        this.allStocksMap = allStocksMap;
        this.commonStocksToBlocksMap = commonStocksToBlocksMap;
        this.commonBlocksToStocksMap = commonBlocksToStocksMap;
    }

    public List<Map.Entry<String, BlockValues>> select() throws YiException{

        // Select top blocks base on filter files
        List<Map.Entry<String, BlockValues>> topBlockList = selectTopBlocksList();
        for (Map.Entry<String, BlockValues> mapping : topBlockList) {
            mapping.getValue().setStocksSet(commonBlocksToStocksMap.get(mapping.getKey()));
            //System.out.println(mapping.getKey() + "," + mapping.getValue());
        }

        return topBlockList;
    }

    /**
     *
     * @return key is block name, value is active stock quantity
     */
    List<Map.Entry<String, BlockValues>> selectTopBlocksList() throws YiException {
        //1. get the block map with block name as key and related stock count as value
        HashMap<String, BlockValues> preSelectedBlockToStockCountMap = selectBlocksListWithPreSelectedStocks();

        //2. select the top 10% item and stock count should be greater than 2
        // sort the map to list, //降序排序
        List<Map.Entry<String, BlockValues>> preSelectedBlockToStockCountList =
                new ArrayList<Map.Entry<String, BlockValues>>(preSelectedBlockToStockCountMap.entrySet());
        Collections.sort(preSelectedBlockToStockCountList, new Comparator<Map.Entry<String, BlockValues>>() {
            public int compare(Map.Entry<String, BlockValues> o1, Map.Entry<String, BlockValues> o2) {
                return o1.getValue().getActiveBlockCount() < o2.getValue().getActiveBlockCount() ? 1 : -1;
            }
        });
        // select top bestSelectedStocksByRatio 10 percent now, and bestSelectedStocksByMinCount is 2
        List<Map.Entry<String, BlockValues>> selectedBlockToStockCountListWithCount = new ArrayList<Map.Entry<String, BlockValues>>();
        int selectBlockCount = (int)(preSelectedBlockToStockCountList.size() * YiConstants.bestSelectedStocksByRatio);
        for (int i = 0; i < selectBlockCount; i++) {
            if (preSelectedBlockToStockCountList.get(i).getValue().getActiveBlockCount() > YiConstants.bestSelectedStocksByMinCount) {
                selectedBlockToStockCountListWithCount.add(preSelectedBlockToStockCountList.get(i));
            }
        }

        //3. continue select the top 60% item and active ratio is greater than 20%
        List<Map.Entry<String, BlockValues>> selectedBlockToStockCountListWithActiveRatio = new ArrayList<Map.Entry<String, BlockValues>>();
        //降序排序
        Collections.sort(selectedBlockToStockCountListWithCount, new Comparator<Map.Entry<String, BlockValues>>() {
            public int compare(Map.Entry<String, BlockValues> o1, Map.Entry<String, BlockValues> o2) {
                return o1.getValue().getActiveRatioInBlock() < o2.getValue().getActiveRatioInBlock() ? 1 : -1;
            }
        });
        selectBlockCount = (int)(selectedBlockToStockCountListWithCount.size() * YiConstants.bestSelectedStocksByActiveRatio);
        for (int i = 0; i < selectBlockCount; i++) {
            if (selectedBlockToStockCountListWithCount.get(i).getValue().getActiveRatioInBlock() > YiConstants.bestSelectedStocksByActiveRatioMin) {
                selectedBlockToStockCountListWithActiveRatio.add(selectedBlockToStockCountListWithCount.get(i));
            }
        }

        // 4. Validation
        // set to 0 now. if stock is less than PRE_SELECTED_STOCKS_TOO_LITTLE
        if (selectedBlockToStockCountListWithActiveRatio.size() <= YiConstants.minSelectedBlockCount) {
            throw new YiException(ExceptionHandler.SELECTED_BLOCKS_TOO_LITTLE);
        }

        return selectedBlockToStockCountListWithActiveRatio;
    }

    /**
     * get the block map with block name as key and related stock count as value
     * @return key is block name, value is active stock quantity
     */
    HashMap<String, BlockValues> selectBlocksListWithPreSelectedStocks() throws YiException {
        BlockInfoReader preSelectedBlockInfoReader = new BlockInfoReader(YiConstants.getSelectorPath() + YiConstants.localPreSelectedBlockFileName);
        BlockData preSelectedBlockData = preSelectedBlockInfoReader.getBlockData();
        TreeSet<String> bcNames = new TreeSet<String>();
        bcNames.add(YiConstants.majorIncrease);
        bcNames.add(YiConstants.followIncrease);
        // Key is stock name, values are not matter at all
        TreeMap<String, HashSet<String>> preSelectedStocksToBlocksMap= preSelectedBlockData.getDistinctStocksToBlocksMap(allStocksMap, bcNames);
        // minTechValidePreSelectedBlockCount is to 0 now
        if (preSelectedStocksToBlocksMap.size() <= YiConstants.minTechValidePreSelectedBlockCount) {
            throw new YiException(ExceptionHandler.WAITING_SELECT_STOCKS_FILE);
        }
        // minValidPreSelectedStockCount is set to 10 now. if stock is less than PRE_SELECTED_STOCKS_TOO_LITTLE
        if (preSelectedStocksToBlocksMap.size() <= YiConstants.minValidPreSelectedStockCount) {
            throw new YiException(ExceptionHandler.PRE_SELECTED_STOCKS_TOO_LITTLE);
        }

        //Key is block name, Values are the stocks count that the block contains
        HashMap<String, BlockValues> preSelectedBlockToStockCountMap = new HashMap<String, BlockValues>();
        for(String stock : preSelectedStocksToBlocksMap.keySet()){
            //System.out.println(stock + " belong to " + preSelectedStocksToBlocksMap.get(stock));
            //Get all the blocks that contain the block
            HashSet<String> blocks = commonStocksToBlocksMap.get(stock);
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
        // set to 2 now. if stock is less than PRE_SELECTED_STOCKS_TOO_LITTLE
        if (preSelectedBlockToStockCountMap.size() <= YiConstants.minValidPreSelectedBlockCount) {
            throw new YiException(ExceptionHandler.PRE_SELECTED_BLOCKS_TOO_LITTLE);
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
            SelectBlockModel selectBlockModel = new SelectBlockModel(allStocksReader.getStocksMap(), commonStocksToBlocksMap, commonBlocksToStocksMap);
            selectBlockModel.select();
        } catch (YiException e) {
            e.printStackTrace();
        }
    }
}
