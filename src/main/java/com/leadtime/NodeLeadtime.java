package com.leadtime;

/**
 * Created by jianguog on 17/3/28.
 */
public class NodeLeadtime {
    String node;
    int leadtime;
    int preleadtime;

    public NodeLeadtime(String node, int leadtime, int preleadtime) {
        this.node = node;
        this.leadtime = leadtime;
        this.preleadtime = preleadtime;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public int getLeadtime() {
        return leadtime;
    }

    public void setLeadtime(int leadtime) {
        this.leadtime = leadtime;
    }

    public int getPreleadtime() {
        return preleadtime;
    }

    public void setPreleadtime(int preleadtime) {
        this.preleadtime = preleadtime;
    }

    @Override
    public String toString() {
        return "NodeLeadtime{" +
                "node='" + node + '\'' +
                ", leadtime=" + leadtime +
                ", preleadtime=" + preleadtime +
                '}';
    }
}
