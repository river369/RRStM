package com.yi.blocks;

import com.yi.YiConstants;
import com.yi.exception.ExceptionHandler;
import com.yi.exception.YiException;

import java.util.*;

/**
 * Created by jianguog on 17/2/18.
 */
public class BlockData {
    List<BK> bkList = new ArrayList<BK>();

    public List<BK> getBkList() {
        return bkList;
    }

    public void setBkList(List<BK> bkList) {
        this.bkList = bkList;
    }

    /**
     *
     * @param allStocksMap all stock from east money
     * @return  Key is stock, value is blocks set
     */
    public TreeMap<String, HashSet<String>> getDistinctStocksToBlocksMap(Map<String, String> allStocksMap) throws YiException {
        return getDistinctStocksToBlocksMap(allStocksMap, null);
    }

    /**
     *
     * @param allStocksMap all stock from east money
     * @param bcNames bc name for filter purpose
     * @return
     */
    public TreeMap<String, HashSet<String>> getDistinctStocksToBlocksMap(Map<String, String> allStocksMap, TreeSet<String> bcNames)
            throws YiException {
        TreeMap<String, HashSet<String>> distinctStockTreeMap = new TreeMap<String, HashSet<String>>();
        for (BK bk: bkList) {
            for (BC bc :bk.getBcList()){
                if (bcNames != null && !bcNames.contains(bc.getName())){
                    // For the preselected case, only need to get a few bc name, so add a common filter here for BCName
                    continue;
                }
                String fullBKBCName = bk.getName() + "_" + bc.getName();
                for(String stockId : bc.getStockIds()) {
                    if (allStocksMap.containsKey(stockId.substring(2))){
                        HashSet<String> blocks = distinctStockTreeMap.get(stockId);
                        if (blocks == null) {
                            blocks = new HashSet<String>();
                            distinctStockTreeMap.put(stockId, blocks);
                        }
                        blocks.add(fullBKBCName);
                    }
                }
            }
        }
        if (bcNames == null && distinctStockTreeMap.size() < YiConstants.minCommonStockCount) {
            System.out.println(ExceptionHandler.SYSTEM_BLOCK_INFO_STOCKS_COUNT_TOO_LOW + " The value is " + distinctStockTreeMap.size());
            throw new YiException(ExceptionHandler.SYSTEM_BLOCK_INFO_STOCKS_COUNT_TOO_LOW);
        }
        return distinctStockTreeMap;
    }

    /**
     *
     * @param allStocksMap all stock from east money
     * @return  Key is block, value is stocks set
     */
    public TreeMap<String, HashSet<String>> getDistinctBlocksToStocksMap(Map<String, String> allStocksMap) throws YiException {
        TreeMap<String, HashSet<String>> distinctBlockTreeMap = new TreeMap<String, HashSet<String>>();
        for (BK bk: bkList) {
            for (BC bc :bk.getBcList()){
                String fullBKBCName = bk.getName() + "_" + bc.getName();
                HashSet<String> stocksSet = new HashSet<String>();
                for(String stockId : bc.getStockIds()) {
                    if (allStocksMap.containsKey(stockId.substring(2))){
                        stocksSet.add(stockId);
                    }
                }
                distinctBlockTreeMap.put(fullBKBCName, stocksSet);
            }
        }
        if (distinctBlockTreeMap.size() < YiConstants.minCommonBlockCount) {
            System.out.println(ExceptionHandler.SYSTEM_BLOCK_INFO_BLOCKS_COUNT_TOO_LOW + " The value is " + distinctBlockTreeMap.size());
            throw new YiException(ExceptionHandler.SYSTEM_BLOCK_INFO_BLOCKS_COUNT_TOO_LOW);
        }
        return distinctBlockTreeMap;
    }

    public void printBlockData(){
        for (BK bk: bkList) {
            //System.out.println("BK=" + bk.getName());
            for (BC bc :bk.getBcList()){
                //System.out.println("  BC=" + bc.getName());
                for(String stockId : bc.getStockIds()) {
                    //System.out.println("    " + stock);
                    System.out.println(bk.getName() + ";" + bc.getName() + ";" + stockId);
                }
            }
        }
    }
}

