package com.leadtime.data;

/**
 * Created by jianguog on 17/3/28.
 */
public class NodeLeadtimeOutScope {
    String node;
    int better;
    int worse;
    int total;

    public NodeLeadtimeOutScope(String node) {
        this.node = node;
    }

    public void addTotal() {
        this.total++;
    }
    public void addWorse() {
        this.worse++;
    }
    public void addBetter() {
        this.better++;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
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
        return  node +
                "," + better +
                "," + worse +
                "," + (better+worse) +
                "," + total;
    }

}
