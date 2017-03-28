package com.yi.select;

import com.yi.base.CommonJob;
import com.yi.db.Selection;
import com.yi.db.SelectionDao;
import com.yi.db.SelectionItem;
import com.yi.exception.ExceptionHandler;
import com.yi.exception.YiException;
import com.yi.stocks.AllStocksReader;
import com.yi.utils.DateUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by jianguog on 17/3/7.
 * Select stocks with Sys solutions
 * 第1轮：选强势股
     根据实时数据四个参数中的后三个选出领涨股（涨幅排名为前10％，turnover，volumeratio也前10%大），
     选出领跌股（跌幅排名为后10％，turnover，volumeratio也前10%大））
   第2轮：选强势板块
     选出领涨板块，条件（该版块总股数目大于67个时，领涨股要占3%以上。不足67个时候，领涨股要>=2)
     选出领跌板块，条件（该版块总股数目大于20个时，领跌股要占10%以上。不足20个时候，领跌股要>=2)
     从领涨板块中剔除那些同时也是领跌板块的板块。
 * 第三轮：同
 */
public class SelectFromRealTimeJob extends CommonJob {
    public SelectFromRealTimeJob(boolean alwayRun) {
        super(alwayRun);
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
                System.out.println("Selecting at " + DateUtils.getCurrentTimeToSecondString() + " , selection id is " + selection_id);
                // 3. select the best blocks
                SelectFromRealtimeModel selectModel = new SelectFromRealtimeModel();
                stockOutputList = selectModel.select(allStocksMap);

            } catch (YiException e) {
                msg = ExceptionHandler.HandleException(e);
            }
            // 4. processing the output to db
            processingOutputs (selection_id, stockOutputList, msg);
            sleep(5000);
        }

    }

    void processingOutputs (long selection_id, List<StockOutput> stockOutputList,  String msg) {
        if (null == stockOutputList) return;
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

    public static void main(String[] args) {
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        boolean alwaysRun = true;
        //System.out.println(args[0]);
        if (args != null && args.length > 0 && null != args[0] && "0".equalsIgnoreCase(args[0])){
            alwaysRun = false;
        }
        //System.out.println(alwaysRun);
        SelectFromRealTimeJob selectFramework = new SelectFromRealTimeJob(alwaysRun);
        selectFramework.run();
    }
}
