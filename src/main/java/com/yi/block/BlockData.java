package com.yi.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    void printBlockData(){
        for (BK bk: bkList) {
            //System.out.println("BK=" + bk.getName());
            for (BC bc :bk.getBcList()){
                //System.out.println("  BC=" + bc.getName());
                for(String stock : bc.getStocks()) {
                    //System.out.println("    " + stock);
                    System.out.println(bk.getName() + ";" + bc.getName() + ";" + stock);
                }

            }
        }
    }
}

