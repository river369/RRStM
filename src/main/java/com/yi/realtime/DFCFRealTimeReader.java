package com.yi.realtime;

import com.yi.YiConstants;
import com.yi.exception.ExceptionHandler;
import com.yi.exception.YiException;
import com.yi.utils.DateUtils;
import com.yi.utils.HttpReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jianguog on 17/2/20.
 */
public class DFCFRealTimeReader {
    public static void main(String[] args) {
        DFCFRealTimeReader dfcfRealTimeReader = new DFCFRealTimeReader();
        try {
            dfcfRealTimeReader.getDFCFRealTimeData();
        } catch (YiException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return key is stock, value is stock realtime data
     * @throws YiException
     */
    public Map<String, RealTimeData> getDFCFRealTimeData() throws YiException{
        List<Integer> newsCountList = new ArrayList<Integer>();
        String response = HttpReader.sendGet(YiConstants.dfcfURL,YiConstants.dfcfParameter);
        return parseRealTimeString(response);
        //System.out.println(response);
    }

    public Map<String, RealTimeData> parseRealTimeString(String html) throws YiException {
        if ( null == html) {
            throw new YiException(ExceptionHandler.DFCF_REAL_TIME_STOCKS_DATA_EXCEPTION);

        }
        String jsonarra=html.split("rank:")[1].split(",pages")[0];
        String stocks[]=jsonarra.split("\",");
        Map<String, RealTimeData> stockRealTimeMap = new HashMap<String, RealTimeData>();
        int skipedRows = 0;
        for (int i = 0; i < stocks.length; i++) {
            try {
                String row = stocks[i].replace("[\"", "").replace("\"", "").replace("]", "");
                String[] colums = row.split(",");
                if(colums.length < 25) {
                    System.out.println("Exception in DFCFRealTimeReader: get nothing for rows " + row);
                    continue;
                }
                RealTimeData realTimeData = new RealTimeData();
                realTimeData.setTimeInSecond(DateUtils.getCurrentTimeToSecondString());
                realTimeData.setId(colums[1]);
                realTimeData.setName(colums[2]);
                realTimeData.setPrice(Float.parseFloat(colums[3]));
                realTimeData.setChange(Float.parseFloat(colums[4]));
                realTimeData.setRange((float) (Float.parseFloat(colums[5].replace("%", "")) * 0.01));
                realTimeData.setAmplitude((float) (Float.parseFloat(colums[6].replace("%", "")) * 0.01));
                realTimeData.setTradingNumber(Integer.parseInt(colums[7].replace("%", "")));
                realTimeData.setTradingValue(Integer.parseInt(colums[8].replace("%", "")));
                realTimeData.setYesterdayFinishPrice(Float.parseFloat(colums[9]));
                realTimeData.setTodayStartPrice(Float.parseFloat(colums[10]));
                realTimeData.setMaxPrice(Float.parseFloat(colums[11]));
                realTimeData.setMinPrice(Float.parseFloat(colums[12]));
                realTimeData.setFiveminuateChange((float) (Float.parseFloat(colums[21].replace("%", "")) * 0.01));
                realTimeData.setVolumeRatio(Float.parseFloat(colums[22]));
                realTimeData.setTurnOver((float) (Float.parseFloat(colums[23])* 0.01));
                realTimeData.setPe(Float.parseFloat(colums[24]));
                stockRealTimeMap.put(realTimeData.getId(), realTimeData);
                //System.out.println(row);
                //System.out.println(realTimeData);
            } catch (NumberFormatException e) {
                skipedRows++;
            }
        }
        //System.out.println(skipedRows + " rows are skipped.");
        //System.out.println(stockRealTimeList.size());
        if (stockRealTimeMap.size() <= YiConstants.minDFCFRealTimeStockCount ||
                skipedRows >= YiConstants.maxDFCFRealTimeStockSkipCount) {
            throw new YiException(ExceptionHandler.DFCF_REAL_TIME_STOCKS_COUNT_TOO_LOW);
        }
        return stockRealTimeMap;
    }
}