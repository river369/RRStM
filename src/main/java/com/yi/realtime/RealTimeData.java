package com.yi.realtime;

/**
 * 
 *
 * 2,000001,平安银行,9.56,0.17,1.81%,1.92,898755,855710800,9.39,9.40,9.58,9.40,-,-,-,-,-,-,-,-,0.10%,-,0.53,6.58
 * 2,002687,乔治白,11.30,-0.15,-1.31%,2.27,44054,49377860,11.45,11.36,11.40,11.14,-,-,-,-,-,-,-,-,0.62%,-,1.38,70.63
 * 1,603990,麦迪科技,47.79,-1.29,-2.63%,5.24,7739,36928544,49.08,48.76,49.08,46.51,-,-,-,-,-,-,-,-,0.19%,-,3.87,184.61
 *
 */

public class RealTimeData {
    private String timeInSecond;
    private String id; // 1
    private String Name; // 2
    private float price; // 3价格
    private float change; // 4涨跌额?
    private float range; // 5涨跌幅?
    private float amplitude; //6振幅??
    private int tradingNumber; // 7??
    private int tradingValue; // 8??
    private float yesterdayFinishPrice; // 9 前收盘Price
    private float todaystartPrice; // 10 今开盘Price
    private float maxPrice; //11
    private float minPrice; //12
    private float fiveminuateChange; //21
    private float volumeRatio; //23 量比
    private float turnOver; //24 换手


    public String getTimeInSecond() {
        return timeInSecond;
    }

    public void setTimeInSecond(String timeInSecond) {
        this.timeInSecond = timeInSecond;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getChange() {
        return change;
    }

    public void setChange(float change) {
        this.change = change;
    }

    public float getRange() {
        return range;
    }

    public void setRange(float range) {
        this.range = range;
    }

    public float getAmplitude() {
        return amplitude;
    }

    public void setAmplitude(float amplitude) {
        this.amplitude = amplitude;
    }

    public int getTradingNumber() {
        return tradingNumber;
    }

    public void setTradingNumber(int tradingNumber) {
        this.tradingNumber = tradingNumber;
    }

    public int getTradingValue() {
        return tradingValue;
    }

    public void setTradingValue(int tradingValue) {
        this.tradingValue = tradingValue;
    }

    public float getYesterdayFinishPrice() {
        return yesterdayFinishPrice;
    }

    public void setYesterdayFinishPrice(float yesterdayFinishPrice) {
        this.yesterdayFinishPrice = yesterdayFinishPrice;
    }

    public float getTodaystartPrice() {
        return todaystartPrice;
    }

    public void setTodaystartPrice(float todaystartPrice) {
        this.todaystartPrice = todaystartPrice;
    }

    public float getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(float maxPrice) {
        this.maxPrice = maxPrice;
    }

    public float getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(float minPrice) {
        this.minPrice = minPrice;
    }

    public float getFiveminuateChange() {
        return fiveminuateChange;
    }

    public void setFiveminuateChange(float fiveminuateChange) {
        this.fiveminuateChange = fiveminuateChange;
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
}


