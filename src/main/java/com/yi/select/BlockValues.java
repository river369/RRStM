package com.yi.select;

/**
 * Created by jianguog on 17/3/7.
 */
public class BlockValues {
    int activeBlockCount;
    int allBlockCount;

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

}
