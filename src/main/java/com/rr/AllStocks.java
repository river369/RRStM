package com.rr;

import java.io.File;
import java.io.IOException;

/**
 * Created by jianguog on 16/12/13.
 */
public class AllStocks {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        //AllStocksReader all = new AllStocksReader("/Users/jianguog/stock/export");
        AllStocks all = new AllStocks(args[0]);
        all.goThrough();
        System.out.println("Runing time is " + (System.currentTimeMillis() - startTime));
    }

    String stockDir;

    public AllStocks(String stockDir) {
        this.stockDir = stockDir;
    }

    public void goThrough(){
        File dir = new File(stockDir);
        File[] files = dir.listFiles();
        for (File file: files) {
            Stock st = new Stock(file, 5, 0.02, 0.02, 0.03, ";");
            try {
                st.calculate();
                //st.calculateSomeDays(12);
                //st.calculateSomeDays(64);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
