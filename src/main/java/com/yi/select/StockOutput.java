package com.yi.select;

/**
 * Created by jianguog on 17/3/11.
 */
public class StockOutput {
    String id;
    String name;
    StockValues values;

    public StockOutput(String id, String name, StockValues values) {
        this.id = id;
        this.name = name;
        this.values = values;
    }

    @Override
    public String toString() {
        return  "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", values=" + values ;
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

    public StockValues getValues() {
        return values;
    }

    public void setValues(StockValues values) {
        this.values = values;
    }
}
