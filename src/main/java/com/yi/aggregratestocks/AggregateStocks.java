package com.yi.aggregratestocks;

import com.yi.YiConstants;
import com.yi.blocks.BC;
import com.yi.blocks.BK;
import com.yi.blocks.BlockData;
import com.yi.blocks.BlockInfoReader;

import java.util.Iterator;
import java.util.TreeMap;

/**
 * Created by jianguog on 17/2/20.
 */
public class AggregateStocks {
    public static void main(String[] args) {
        AggregateStocks aggregateStocks = new AggregateStocks();
        TreeMap<String, DistinctStock> distinctStockTreeMap = aggregateStocks.buildDistinctStocks();
        //aggregateStocks.printDistinctStockTreeMap(distinctStockTreeMap);
    }

    public TreeMap<String, DistinctStock> buildDistinctStocks(){
        BlockInfoReader blockInfoReader = new BlockInfoReader(YiConstants.getSelectorPath() + YiConstants.localBlockInfoFileName);
        BlockData blockData = blockInfoReader.getBlockData();
        //blockData.printBlockData();
        TreeMap<String, DistinctStock> distinctStockTreeMap = new TreeMap<String, DistinctStock>();
        for (BK bk: blockData.getBkList()) {
            for (BC bc :bk.getBcList()){
                String fullBKBCName = bk.getName() + "_" + bc.getName();
                for(String stockId : bc.getStockIds()) {
                    //System.out.println(stockId);
                    DistinctStock distinctStock = distinctStockTreeMap.get(stockId);
                    if (distinctStock == null) {
                        distinctStock = new DistinctStock();
                        distinctStockTreeMap.put(stockId, distinctStock);
                    }
                    distinctStock.getBlocks().add(fullBKBCName);
                }
            }
        }
        return distinctStockTreeMap;
    }

    void printDistinctStockTreeMap(TreeMap<String, DistinctStock> distinctStockTreeMap){
        Iterator stockIterator = distinctStockTreeMap.keySet().iterator();
        while (stockIterator.hasNext()) {
            System.out.println(stockIterator.next());
        }
    }
}
