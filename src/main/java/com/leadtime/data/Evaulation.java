package com.leadtime.data;

/**
 * Created by jianguog on 17/3/29.
 */
public class Evaulation {
    String key;
    int selectedLeadtime;
    int better;
    int worse;
    int total;

    public void addTotal() {
        this.total++;
    }
    public void addWorse() {
        this.worse++;
    }
    public void addBetter() {
        this.better++;
    }
    public Evaulation(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getSelectedLeadtime() {
        return selectedLeadtime;
    }

    public void setSelectedLeadtime(int selectedLeadtime) {
        this.selectedLeadtime = selectedLeadtime;
    }

    public int getBetter() {
        return better;
    }

    public void setBetter(int better) {
        this.better = better;
    }

    public int getWorse() {
        return worse;
    }

    public void setWorse(int worse) {
        this.worse = worse;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return key +
                "," + selectedLeadtime +
                "," + better +
                "," + worse +
                "," + (better+worse) +
                "," + total;
    }
}
