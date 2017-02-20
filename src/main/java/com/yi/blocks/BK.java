package com.yi.blocks;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jianguog on 17/2/18.
 */
public class BK {
    String name;
    List<BC> bcList = new ArrayList<BC>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BC> getBcList() {
        return bcList;
    }

    public void setBcList(List<BC> bcList) {
        this.bcList = bcList;
    }
}