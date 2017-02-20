package com.yi.stocks;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Created by jianguog on 17/2/19.
 */
public class DistinctStock {

    //String stockId;

    // BK_BC, 行业_电力
    TreeSet<String> blocks = new TreeSet<String>();

    // list of news count
    ArrayList<String> newsCount = new ArrayList<String>();

    public TreeSet<String> getBlocks() {
        return blocks;
    }

    public void setBlocks(TreeSet<String> blocks) {
        this.blocks = blocks;
    }

    public ArrayList<String> getNewsCount() {
        return newsCount;
    }

    public void setNewsCount(ArrayList<String> newsCount) {
        this.newsCount = newsCount;
    }


}
