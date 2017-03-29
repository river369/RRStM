package com.leadtime.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jianguog on 17/3/28.
 */
public class AUDShipmentDB {
    String shipmentId;
    String node;
    Date ofDate;
    Date shipDate;
    Date rsd;
    double processingTime;
    int leadtime;
    int preleadtime;

    public AUDShipmentDB(String line) {
        String[] colums = line.split("\t");
        this.shipmentId = colums[0];
        this.node = colums[1].replace("\"","");
        this.processingTime = Double.parseDouble(colums[4]);
        SimpleDateFormat dt = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        try {
            this.ofDate = dt.parse(colums[2].replace("\"",""));
            this.shipDate  = dt.parse(colums[3].replace("\"",""));
            this.rsd  = dt.parse(colums[5].replace("\"",""));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return "AUDShipmentDW{" +
                "shipmentId='" + shipmentId + '\'' +
                ", node='" + node + '\'' +
                ", ofDate=" +  df.format(ofDate) +
                ", shipDate=" + df.format(shipDate) +
                ", rsd=" + df.format(rsd) +
                ", processingTime=" + processingTime +
                ", leadtime=" + leadtime +
                ", preleadtime=" + preleadtime +
                '}';
    }

    public double getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(double processingTime) {
        this.processingTime = processingTime;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public Date getOfDate() {
        return ofDate;
    }

    public void setOfDate(Date ofDate) {
        this.ofDate = ofDate;
    }

    public Date getShipDate() {
        return shipDate;
    }

    public void setShipDate(Date shipDate) {
        this.shipDate = shipDate;
    }

    public Date getRsd() {
        return rsd;
    }

    public void setRsd(Date rsd) {
        this.rsd = rsd;
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


}
