package com.yi.db;

import java.sql.Timestamp;

/**
 * Created by jianguog on 17/3/12.
 */
public class SelectionItemRealTime {
    long id;
    String stock_id;
    float price;
    float yesterday_finish_price;
    float today_start_price;
    float volume_ratio;
    float turn_over;
    Timestamp creation_date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStock_id() {
        return stock_id;
    }

    public void setStock_id(String stock_id) {
        this.stock_id = stock_id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getYesterday_finish_price() {
        return yesterday_finish_price;
    }

    public void setYesterday_finish_price(float yesterday_finish_price) {
        this.yesterday_finish_price = yesterday_finish_price;
    }

    public float getToday_start_price() {
        return today_start_price;
    }

    public void setToday_start_price(float today_start_price) {
        this.today_start_price = today_start_price;
    }

    public float getVolume_ratio() {
        return volume_ratio;
    }

    public void setVolume_ratio(float volume_ratio) {
        this.volume_ratio = volume_ratio;
    }

    public float getTurn_over() {
        return turn_over;
    }

    public void setTurn_over(float turn_over) {
        this.turn_over = turn_over;
    }

    public Timestamp getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Timestamp creation_date) {
        this.creation_date = creation_date;
    }
}
