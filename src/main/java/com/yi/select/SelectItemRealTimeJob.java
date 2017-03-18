package com.yi.select;

import com.yi.base.CommonJob;
import com.yi.db.SelectionDao;
import com.yi.db.SelectionItem;
import com.yi.db.SelectionItemRealTime;
import com.yi.exception.ExceptionHandler;
import com.yi.exception.YiException;
import com.yi.realtime.DFCFRealTimeReader;
import com.yi.realtime.RealTimeData;
import com.yi.utils.DateUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by jianguog on 17/3/7.
 *
 * Query the real time data of selected stocks for analysis
 */
public class SelectItemRealTimeJob extends CommonJob {

    public SelectItemRealTimeJob(boolean alwayRun) {
        super(alwayRun);
    }

    public void run(){
        // Start to iterate the run every 5 seconds
        while (true){
            checkTime();
            try {
                // 1. Get all stock infor from DFCF
                System.out.println("Getting DFCF Real time data for Stock in the past 2 days at " + DateUtils.getCurrentTimeToSecondString());
                DFCFRealTimeReader dfcfRealTimeReader = new DFCFRealTimeReader();
                Map<String, RealTimeData> dfcfRealTimeDataMap = dfcfRealTimeReader.getDFCFRealTimeData();

                SelectionDao selectionDao = new SelectionDao();
                List<SelectionItem> selectionItemList = selectionDao.getSelectedStockInXDaysBefore(-2);
                for (SelectionItem selectionItem : selectionItemList){
                    RealTimeData realTimeData = dfcfRealTimeDataMap.get(selectionItem.getStock_id().substring(2));
                    System.out.println(selectionItem.getStock_id() + "," + realTimeData);
                    SelectionItemRealTime selectionItemRealTime = new SelectionItemRealTime();
                    selectionItemRealTime.setStock_id(selectionItem.getStock_id());
                    selectionItemRealTime.setPrice(realTimeData.getPrice());
                    selectionItemRealTime.setYesterday_finish_price(realTimeData.getYesterdayFinishPrice());
                    selectionItemRealTime.setToday_start_price(realTimeData.getTodayStartPrice());
                    selectionItemRealTime.setTurn_over(realTimeData.getTurnOver());
                    selectionItemRealTime.setVolume_ratio(realTimeData.getVolumeRatio());
                    selectionDao.insertSelectionItemRealTime(selectionItemRealTime);
                }

            } catch (YiException e) {
                ExceptionHandler.HandleException(e);
            }
            sleep(5000);
        }

    }

    public static void main(String[] args) {
        boolean alwaysRun = true;
        //System.out.println(args[0]);
        if (args != null && args.length > 0 && null != args[0] && "0".equalsIgnoreCase(args[0])){
            alwaysRun = false;
        }
        //System.out.println(alwaysRun);
        SelectItemRealTimeJob selectItemRealTimeFramework = new SelectItemRealTimeJob(alwaysRun);
        selectItemRealTimeFramework.run();
    }
}
