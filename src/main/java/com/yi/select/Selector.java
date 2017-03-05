package com.yi.select;

import com.yi.YiConstants;
import com.yi.blocks.BlockData;
import com.yi.blocks.BlockInfoReader;
import com.yi.stocks.AllStocksReader;

import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Created by jianguog on 17/3/5.
 */
public class Selector {
    public static void main(String[] args) {
        Selector selector = new Selector();
        selector.select();
    }

    public void select(){
        long start = System.currentTimeMillis();
        AllStocksReader allStocksReader = new AllStocksReader();
        Map<String, String> allStocksMap = allStocksReader.getStocksMap();
//        System.out.println(System.currentTimeMillis() - start);

        BlockInfoReader commonBlockInfoReader = new BlockInfoReader(YiConstants.blockInfoFileString);
        BlockData commonBlockData = commonBlockInfoReader.getBlockData();
        TreeMap<String, TreeSet<String>> stocksToBlocksMap= commonBlockData.getDistinctStocksToBlocksMap(allStocksMap);
//        for(String stock : stocksToBlocksMap.keySet()){
//            System.out.println(stock + " belong to " + stocksToBlocksMap.get(stock));
//        }
//        System.out.println(System.currentTimeMillis() - start);

        BlockInfoReader preSelectedBlockInfoReader = new BlockInfoReader(YiConstants.preSelectedBlockFileString);
        BlockData preSelectedBlockData = preSelectedBlockInfoReader.getBlockData();
        TreeSet<String> bcNames = new TreeSet<String>();
        bcNames.add("股池2：量单.");
        bcNames.add("MIDD主升.");
        TreeMap<String, TreeSet<String>> preSelectedStocksToBlocksMap= preSelectedBlockData.getDistinctStocksToBlocksMap(allStocksMap, bcNames);
//        for(String stock : preSelectedStocksToBlocksMap.keySet()){
//            System.out.println(stock + " belong to " + preSelectedStocksToBlocksMap.get(stock));
//        }
//        System.out.println(System.currentTimeMillis() - start);
    }
}
