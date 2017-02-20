package com.yi.blocks;

import java.util.ArrayList;
import java.util.List;

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

