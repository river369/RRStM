package com.yi.select;

import com.yi.exception.ExceptionHandler;
import com.yi.exception.YiException;
import com.yi.stocks.AllStocksReader;

import java.util.List;
import java.util.Map;

/**
 * Created by jianguog on 17/3/7.
 */
public class SelectFramework {


    public void run(){
        // 1. Load all stocks from website
        long start = System.currentTimeMillis();
        // Read all stocks from website
        AllStocksReader allStocksReader = new AllStocksReader();
        // key is stock code, value is stock name
        Map<String, String> allStocksMap = allStocksReader.getStocksMap();
        if (allStocksMap.size() < 3000) {
            System.out.println("Exit since stock size is abnormal. The value is " + allStocksMap.size());
            System.exit(-1000);
        }
        //System.out.println(System.currentTimeMillis() - start);

        try {
            // 2. select the best blocks base on preselected stocks
            SelectBlockModel selectBlockModel = new SelectBlockModel(allStocksMap);
            List<Map.Entry<String, BlockValues>> topBlockList = selectBlockModel.select();

            // 3. Select the best stocks in the best blocks
            SelectStockModel selectStockModel = new SelectStockModel();
            selectStockModel.select(topBlockList);
        } catch (YiException e) {
            ExceptionHandler.HandleException(e);
        }
    }

    public static void main(String[] args) {
        SelectFramework selectFramework = new SelectFramework();
        selectFramework.run();
    }
}
