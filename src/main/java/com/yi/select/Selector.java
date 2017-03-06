package com.yi.select;

import com.yi.YiConstants;
import com.yi.blocks.BlockData;
import com.yi.blocks.BlockInfoReader;
import com.yi.stocks.AllStocksReader;

import java.util.*;

/**
 * Created by jianguog on 17/3/5.
 */
public class Selector {

    // key is stock code, value is stock name
    Map<String, String> allStocksMap;
    // Key is stock name, values are the blocks that contain the stock
    TreeMap<String, HashSet<String>> commonStocksToBlocksMap;
    // Key is block name, values are stocks that belong to the block
    TreeMap<String, HashSet<String>> commonBlocksToStocksMap;

    public static void main(String[] args) {
        Selector selector = new Selector();
        selector.select();
    }

    public void select(){
        long start = System.currentTimeMillis();
        // Read all stocks from website
        AllStocksReader allStocksReader = new AllStocksReader();
        allStocksMap = allStocksReader.getStocksMap();
        //System.out.println(System.currentTimeMillis() - start);

        // read common stock-block mapping from local static file
        BlockInfoReader commonBlockInfoReader = new BlockInfoReader(YiConstants.blockInfoFileString);
        BlockData commonBlockData = commonBlockInfoReader.getBlockData();
        commonStocksToBlocksMap = commonBlockData.getDistinctStocksToBlocksMap(allStocksMap);
        commonBlocksToStocksMap = commonBlockData.getDistinctBlocksToStocksMap(allStocksMap);

        // Select top blocks base on filter files
        List<Map.Entry<String, BlockValues>> topBlockList = selectTopBlocksList();
        for (Map.Entry<String, BlockValues> mapping : topBlockList) {
            System.out.println(mapping.getKey() + "," + mapping.getValue().getActiveBlockCount()
                            + "," + mapping.getValue().getAllBlockCount()
                            + "," + mapping.getValue().getActiveRatioInBlock());
        }
    }

    /**
     *
     * @return key is block name, value is active stock quantity
     */
    List<Map.Entry<String, BlockValues>> selectTopBlocksList(){
        //1. get the block map with block name as key and related stock count as value
        HashMap<String, BlockValues> preSelectedBlockToStockCountMap = selectBlocksListWithPreSelectedStocks();
//        for (String block : preSelectedBlockToStockCountMap.keySet()){
//            System.out.println(block + "-"  + preSelectedBlockToStockCountMap.get(block).getActiveBlockCount());
//        }

        //2. select the top 10% item and stock count should be greate than 2
        // sort the map to list, //降序排序
        List<Map.Entry<String, BlockValues>> preSelectedBlockToStockCountList =
                new ArrayList<Map.Entry<String, BlockValues>>(preSelectedBlockToStockCountMap.entrySet());
        Collections.sort(preSelectedBlockToStockCountList, new Comparator<Map.Entry<String, BlockValues>>() {
            public int compare(Map.Entry<String, BlockValues> o1, Map.Entry<String, BlockValues> o2) {
                return o1.getValue().getActiveBlockCount() < o2.getValue().getActiveBlockCount() ? 1 : -1;
            }
        });
        // select top
        List<Map.Entry<String, BlockValues>> selectedBlockToStockCountListWithCount = new ArrayList<Map.Entry<String, BlockValues>>();
        int selectBlockCount = (int)(preSelectedBlockToStockCountList.size() * YiConstants.bestStocksByCountRatio);
        for (int i = 0; i < selectBlockCount; i++) {
            if (preSelectedBlockToStockCountList.get(i).getValue().getActiveBlockCount() > YiConstants.bestStocksByCountMinCount) {
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
        selectBlockCount = (int)(selectedBlockToStockCountListWithCount.size() * YiConstants.bestStocksByActiveRatio);
        for (int i = 0; i < selectBlockCount; i++) {
            if (selectedBlockToStockCountListWithCount.get(i).getValue().getActiveRatioInBlock() > YiConstants.bestStocksByActiveRatioMin) {
                selectedBlockToStockCountListWithActiveRatio.add(selectedBlockToStockCountListWithCount.get(i));
            }
        }
        return selectedBlockToStockCountListWithActiveRatio;
    }

    /**
     * get the block map with block name as key and related stock count as value
     * @return key is block name, value is active stock quantity
     */
    HashMap<String, BlockValues> selectBlocksListWithPreSelectedStocks(){
        BlockInfoReader preSelectedBlockInfoReader = new BlockInfoReader(YiConstants.preSelectedBlockFileString);
        BlockData preSelectedBlockData = preSelectedBlockInfoReader.getBlockData();
        TreeSet<String> bcNames = new TreeSet<String>();
        bcNames.add(YiConstants.majorIncrease);
        bcNames.add(YiConstants.followIncrease);
        // Key is stock name, values are not matter at all
        TreeMap<String, HashSet<String>> preSelectedStocksToBlocksMap= preSelectedBlockData.getDistinctStocksToBlocksMap(allStocksMap, bcNames);

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
        return preSelectedBlockToStockCountMap;
    }

}
