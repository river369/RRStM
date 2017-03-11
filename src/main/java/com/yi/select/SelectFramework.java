package com.yi.select;

import com.yi.EnvConstants;
import com.yi.YiConstants;
import com.yi.exception.ExceptionHandler;
import com.yi.exception.YiException;
import com.yi.stocks.AllStocksReader;
import com.yi.utils.OSSUtil;

import java.io.File;
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
            // 2. select the best blocks
            getPreselectedFiles();
            SelectModel selectModel = new SelectModel();
            List<StockOutput>  selectedStockList = selectModel.select(allStocksMap);
            for (StockOutput stockOutput : selectedStockList) {
                System.out.println(stockOutput);
            }

        } catch (YiException e) {
            ExceptionHandler.HandleException(e);
        }
    }

    void getPreselectedFiles(){
        String[] filesToDownLoad = {YiConstants.localPreSelectedBlockFileName, YiConstants.localBlockInfoFileName};
        for (String fileName : filesToDownLoad ) {
            String ossKey = EnvConstants.OSS_KT_PREFIX + fileName;
            while (! OSSUtil.exist(ossKey) ) {
                try {
                    System.out.println("Cannot fine OSS file " + ossKey);
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            OSSUtil.getObject(ossKey, new File(YiConstants.getSelectorPath() + fileName ));
        }

    }

    public static void main(String[] args) {
        SelectFramework selectFramework = new SelectFramework();
        selectFramework.run();
    }
}
