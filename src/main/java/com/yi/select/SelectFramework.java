package com.yi.select;

import com.yi.EnvConstants;
import com.yi.YiConstants;
import com.yi.db.Selection;
import com.yi.db.SelectionItem;
import com.yi.db.SelectionDao;
import com.yi.exception.ExceptionHandler;
import com.yi.exception.YiException;
import com.yi.stocks.AllStocksReader;
import com.yi.utils.DateUtils;
import com.yi.utils.OSSUtil;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by jianguog on 17/3/7.
 */
public class SelectFramework {
    boolean alwayRun;

    public SelectFramework(boolean alwayRun) {
        this.alwayRun = alwayRun;
    }

    public void run(){
        checkTime();
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

        // Start to iterate the run every 5 seconds
        while (true){
            List<StockOutput>  stockOutputList = null;
            String msg = null;
            checkTime();
            long selection_id = System.currentTimeMillis();
            try {
                // 2. download the preselected file
                System.out.println("Selecting at " + DateUtils.getCurrentTimeToSecondString());
                getPreselectedFiles();

                // 3. select the best blocks
                SelectModel selectModel = new SelectModel();
                stockOutputList = selectModel.select(allStocksMap);

            } catch (YiException e) {
                msg = ExceptionHandler.HandleException(e);
            }
            // 4. processing the output to db
            processingOutputs (selection_id, stockOutputList, msg);
            sleep(5000);
        }

    }

    void getPreselectedFiles(){
        String[] filesToDownLoad = {YiConstants.localPreSelectedBlockFileName, YiConstants.localBlockInfoFileName};
        for (String fileName : filesToDownLoad ) {
            String ossKey = EnvConstants.OSS_KT_PREFIX + fileName;
            while (! OSSUtil.exist(ossKey) ) {
                System.out.println("Cannot fine OSS file " + ossKey);
                sleep(5000);
            }
            OSSUtil.getObject(ossKey, new File(YiConstants.getSelectorPath() + fileName ));
        }
    }

    void checkTime(){
        if (alwayRun) return;
        int hour = DateUtils.getCurrentHour();
        while (hour < 9 || hour > 15 ) {
            System.out.println("The job only run between 9:00 and 15:00");
            sleep(60000);
        }
    }

    void processingOutputs (long selection_id, List<StockOutput> stockOutputList,  String msg) {
        System.out.println("Selected " + stockOutputList.size() + " stocks");
        SelectionDao selectionDao = new SelectionDao();
        Selection selection = new Selection();
        selection.setSelection_id(selection_id);
        int status = 0;
        if (msg != null) {
            status = 1;
            selection.setDescription(msg);
        }
        selection.setStatus(status);
        selectionDao.insertSelection(selection);

        if (stockOutputList.size() > 0) {
            for (StockOutput stockOutput : stockOutputList) {
                System.out.println(stockOutput);
                SelectionItem selectionItem = new SelectionItem();
                selectionItem.setSelection_id(selection_id);
                selectionItem.setStock_id(stockOutput.getId());
                selectionItem.setStock_name(stockOutput.getName());
                StockValues stockValues = stockOutput.getValues();
                selectionItem.setBelong_to_blocks(removeBracket(stockValues.getBelongToBlocks().toString()));
                selectionItem.setDriven_by_blocks(removeBracket(stockValues.getDrivenByBlocks().toString()));
                selectionItem.setPrice(stockValues.getPrice());
                selectionItem.setYesterday_finish_price(stockValues.getYesterdayFinishPrice());
                selectionItem.setToday_start_price(stockValues.getTodayStartPrice());
                selectionItem.setVolume_ratio(stockValues.getVolumeRatio());
                selectionItem.setTurn_over(stockValues.getTurnOver());
                selectionDao.insertSelectionItem(selectionItem);
            }
        }
    }

    String removeBracket(String str) {
        return str.replaceAll("\\[","").replaceAll("\\]","");
    }

    void sleep(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        boolean alwaysRun = true;
        //System.out.println(args[0]);
        if (args != null && args.length > 0 && null != args[0] && "0".equalsIgnoreCase(args[0])){
            alwaysRun = false;
        }
        //System.out.println(alwaysRun);
        SelectFramework selectFramework = new SelectFramework(alwaysRun);
        selectFramework.run();
    }
}
