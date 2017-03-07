package com.yi.realtime;

/**
 *
 * 1,603999,读者传媒,29.58,-0.09,-0.30%,1.18,16191,48065415,29.67,29.71,29.88,29.53,-,-,-,-,-,-,-,-,-0.03%,0.60,1.41,102.58
 * RealTimeData{timeInSecond='2017-03-07_12:02:06', id='603999', Name='读者传媒', price=29.58, change=-0.09, range=-0.003, amplitude=0.0117999995, tradingNumber=16191, tradingValue=48065415, yesterdayFinishPrice=29.67, todaystartPrice=29.71, maxPrice=29.88, minPrice=29.53, fiveminuateChange=-2.9999999E-4, volumeRatio=0.6, turnOver=1.41, pe=102.58}
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
    private int tradingNumber; // 7 成交量
    private int tradingValue; // 8 成交金额
    private float yesterdayFinishPrice; // 9 前收盘Price
    private float todaystartPrice; // 10 今开盘Price
    private float maxPrice; //11 今日最高
    private float minPrice; //12 今日最低
    private float fiveminuateChange; //21
    private float volumeRatio; //22 量比
    private float turnOver; //23 换手
    private float pe; //24 PE(动)


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

    public float getPe() {
        return pe;
    }

    public void setPe(float pe) {
        this.pe = pe;
    }

    @Override
    public String toString() {
        return "RealTimeData{" +
                "timeInSecond='" + timeInSecond + '\'' +
                ", id='" + id + '\'' +
                ", Name='" + Name + '\'' +
                ", price=" + price +
                ", change=" + change +
                ", range=" + range +
                ", amplitude=" + amplitude +
                ", tradingNumber=" + tradingNumber +
                ", tradingValue=" + tradingValue +
                ", yesterdayFinishPrice=" + yesterdayFinishPrice +
                ", todaystartPrice=" + todaystartPrice +
                ", maxPrice=" + maxPrice +
                ", minPrice=" + minPrice +
                ", fiveminuateChange=" + fiveminuateChange +
                ", volumeRatio=" + volumeRatio +
                ", turnOver=" + turnOver +
                ", pe=" + pe +
                '}';
    }
}


