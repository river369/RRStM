package com.yi.db;

import java.sql.Timestamp;

/**
 * Created by jianguog on 17/3/12.
 */
public class SelectionItem {
    long id;
    long selection_id;
    String stock_id;
    String stock_name;
    String driven_by_blocks;
    String belong_to_blocks;
    float price;
    float yesterday_finish_price;
    float today_start_price;
    float volume_ratio;
    float turn_over;
    Timestamp creation_date;

    public long getSelection_id() {
        return selection_id;
    }

    public void setSelection_id(long selection_id) {
        this.selection_id = selection_id;
    }

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

    public String getStock_name() {
        return stock_name;
    }

    public void setStock_name(String stock_name) {
        this.stock_name = stock_name;
    }

    public String getDriven_by_blocks() {
        return driven_by_blocks;
    }

    public void setDriven_by_blocks(String driven_by_blocks) {
        this.driven_by_blocks = driven_by_blocks;
    }

    public String getBelong_to_blocks() {
        return belong_to_blocks;
    }

    public void setBelong_to_blocks(String belong_to_blocks) {
        this.belong_to_blocks = belong_to_blocks;
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
