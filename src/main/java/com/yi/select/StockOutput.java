package com.yi.select;

import java.util.HashSet;

/**
 * Created by jianguog on 17/3/11.
 */
public class StockOutput {
    String id;
    String name;
    HashSet<String> blocks;
    StockValues values;

    public StockOutput(String id, String name, HashSet<String> blocks, StockValues values) {
        this.id = id;
        this.name = name;
        this.blocks = blocks;
        this.values = values;
    }

    @Override
    public String toString() {
        return "StockOutput{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", blocks=" + blocks +
                ", values=" + values +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashSet<String> getBlocks() {
        return blocks;
    }

    public void setBlocks(HashSet<String> blocks) {
        this.blocks = blocks;
    }

    public StockValues getValues() {
        return values;
    }

    public void setValues(StockValues values) {
        this.values = values;
    }
}
