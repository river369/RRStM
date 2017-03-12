package com.yi.select;

import java.util.HashSet;

/**
 * Created by jianguog on 17/3/7.
 */
public class StockValues {

    private float price; // 3价格
    private float yesterdayFinishPrice; // 9 前收盘Price
    private float todayStartPrice; // 10 今开盘Price
    private float volumeRatio; //22 量比
    private float turnOver; //23 换手

    HashSet<String> drivenByBlocks = new HashSet<String>();
    HashSet<String> belongToBlocks = new HashSet<String>();;

    public StockValues(float price, float yesterdayFinishPrice, float todayStartPrice, float volumeRatio, float turnOver) {
        this.price = price;
        this.yesterdayFinishPrice = yesterdayFinishPrice;
        this.todayStartPrice = todayStartPrice;
        this.volumeRatio = volumeRatio;
        this.turnOver = turnOver;
    }

    //private float priceRateToYesterdayFinish;
    //private float priceRateToTodayStart;
    //前收涨跌幅
    public float getPriceRateToYesterdayFinish() {
        return price/yesterdayFinishPrice;
    }
    //今开涨跌幅
    public float getPriceRateToTodayStart() {
        return price/todayStartPrice;
    }

    public double getValueByType(String type){
        if (type.equalsIgnoreCase("priceRateToYesterdayFinish")) {
           return getPriceRateToYesterdayFinish();
        }
        if (type.equalsIgnoreCase("priceRateToTodayStart")) {
            return getPriceRateToTodayStart();
        }
        if (type.equalsIgnoreCase("volumeRatio")) {
            return getVolumeRatio();
        }
        if (type.equalsIgnoreCase("turnOver")) {
            return getTurnOver();
        }
        return 0;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getYesterdayFinishPrice() {
        return yesterdayFinishPrice;
    }

    public void setYesterdayFinishPrice(float yesterdayFinishPrice) {
        this.yesterdayFinishPrice = yesterdayFinishPrice;
    }

    public float getTodayStartPrice() {
        return todayStartPrice;
    }

    public void setTodayStartPrice(float todaystartPrice) {
        this.todayStartPrice = todaystartPrice;
    }

    public float getVolumeRatio() {
        return volumeRatio;
    }

    public void setVolumeRatio(float volumeRatio) {
        this.volumeRatio = volumeRatio;
    }

    public float getTurnOver() {
        return turnOver;
    }

    public void setTurnOver(float turnOver) {
        this.turnOver = turnOver;
    }

    public HashSet<String> getDrivenByBlocks() {
        return drivenByBlocks;
    }

    public void setDrivenByBlocks(HashSet<String> drivenByBlocks) {
        this.drivenByBlocks = drivenByBlocks;
    }

    public HashSet<String> getBelongToBlocks() {
        return belongToBlocks;
    }

    public void setBelongToBlocks(HashSet<String> belongToBlocks) {
        this.belongToBlocks = belongToBlocks;
    }

    @Override
    public String toString() {
        return "[" +
                "price=" + price +
                ", yesterdayFinishPrice=" + yesterdayFinishPrice +
                ", todayStartPrice=" + todayStartPrice +
                ", volumeRatio=" + volumeRatio +
                ", turnOver=" + turnOver +
                ", drivenByBlocks=" + drivenByBlocks +
                ", belongToBlocks=" + belongToBlocks +
                ']';
    }
}
