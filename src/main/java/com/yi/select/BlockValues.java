package com.yi.select;

import java.util.HashSet;

/**
 * Created by jianguog on 17/3/7.
 */
public class BlockValues {
    int activeBlockCount;
    int allBlockCount;
    HashSet<String> stocksSet;

    public int getActiveBlockCount() {
        return activeBlockCount;
    }

    public void setActiveBlockCount(int activeBlockCount) {
        this.activeBlockCount = activeBlockCount;
    }

    public int getAllBlockCount() {
        return allBlockCount;
    }

    public void setAllBlockCount(int allBlockCount) {
        this.allBlockCount = allBlockCount;
    }

    public double getActiveRatioInBlock() {
        return (activeBlockCount + 0.0)/allBlockCount;
    }

    public HashSet<String> getStocksSet() {
        return stocksSet;
    }

    public void setStocksSet(HashSet<String> stocksSet) {
        this.stocksSet = stocksSet;
    }

    @Override
    public String toString() {
        return "BlockValues{" +
                "activeBlockCount=" + activeBlockCount +
                ", allBlockCount=" + allBlockCount +
                ", stocksSet=" + stocksSet +
                '}';
    }
}
