package com.yi.realtime;

import com.yi.YiConstants;
import com.yi.utils.DateUtils;
import com.yi.utils.HttpReader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jianguog on 17/2/20.
 */
public class DFCFRealTimeReader {
    public static void main(String[] args) {
        DFCFRealTimeReader dfcfRealTimeReader = new DFCFRealTimeReader();
        dfcfRealTimeReader.getDFCFRealTimeData();
    }
    public List<Integer> getDFCFRealTimeData(){
        List<Integer> newsCountList = new ArrayList<Integer>();
        String response = HttpReader.sendGet(YiConstants.dfcfURL,YiConstants.dfcfParameter);
        parseNewsCountString(response);
        //System.out.println(response);
        return null;
    }

    public RealTimeData parseNewsCountString(String html) {
        String jsonarra=html.split("rank:")[1].split(",pages")[0];
        String stocks[]=jsonarra.split("\",");
        List<String> stocklist=new ArrayList<String>();
        for (int i = 0; i < stocks.length; i++) {
            String row = stocks[i].replace("[\"", "").replace("\"", "").replace("]", "");
            String[] colums = row.split(",");
            RealTimeData realTimeData = new RealTimeData();
            realTimeData.setTimeInSecond(DateUtils.getCurrentTimeToSecondString());
            realTimeData.setId(colums[1]);
            realTimeData.setName(colums[2]);
            realTimeData.setPrice(Float.parseFloat(colums[3]));
            realTimeData.setChange(Float.parseFloat(colums[4]));
            realTimeData.setRange((float)(Float.parseFloat(colums[5].replace("%", ""))*0.01));
            realTimeData.setAmplitude((float)(Float.parseFloat(colums[6].replace("%", ""))*0.01));
            realTimeData.setTradingNumber(Integer.parseInt(colums[7].replace("%", "")));
            realTimeData.setTradingValue(Integer.parseInt(colums[8].replace("%", "")));
            realTimeData.setYesterdayFinishPrice(Float.parseFloat(colums[9]));
            realTimeData.setTodaystartPrice(Float.parseFloat(colums[10]));
            realTimeData.setMaxPrice(Float.parseFloat(colums[11]));
            realTimeData.setMinPrice(Float.parseFloat(colums[12]));
            realTimeData.setFiveminuateChange((float) (Float.parseFloat(colums[21].replace("%", ""))*0.01));
            realTimeData.setVolumeRatio(Float.parseFloat(colums[23]));
            realTimeData.setTurnOver(Float.parseFloat(colums[24]));
        }
        return null;
    }
}
