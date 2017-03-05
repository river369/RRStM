package com.yi.blocks;

import com.yi.YiConstants;
import com.yi.aggregratestocks.DistinctStock;

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
    public TreeMap<String, TreeSet<String>> getDistinctStocksToBlocksMap(Map<String, String> allStocksMap, TreeSet<String> bcNames){
        TreeMap<String, TreeSet<String>> distinctStockTreeMap = new TreeMap<String, TreeSet<String>>();
        for (BK bk: bkList) {
            for (BC bc :bk.getBcList()){
                if (bcNames != null && !bcNames.contains(bc.getName())){
                    // For the preselected case, only need to get a few bc name, so add a common filter here for BCName
                    continue;
                }
                String fullBKBCName = bk.getName() + "_" + bc.getName();
                for(String stockId : bc.getStockIds()) {
                    //System.out.println(fullBKBCName+"["+stockId+"]");
                    //System.out.println(stockId.substring(2));
                    if (allStocksMap.containsKey(stockId.substring(2))){
                        TreeSet<String> blocks = distinctStockTreeMap.get(stockId);
                        if (blocks == null) {
                            blocks = new TreeSet<String>();
                            distinctStockTreeMap.put(stockId, blocks);
                        }
                        blocks.add(fullBKBCName);
                    }
                }
            }
        }
        return distinctStockTreeMap;
    }

    public TreeMap<String, TreeSet<String>> getDistinctStocksToBlocksMap(Map<String, String> allStocksMap){
        return getDistinctStocksToBlocksMap(allStocksMap, null);
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

