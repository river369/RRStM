package com.yi.stocks;

import com.yi.YiConstants;
import com.yi.utils.HttpReader;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jianguog on 17/3/5.
 * Get all stocks from http://quote.eastmoney.com/stocklist.html
 *
 */
public class AllStocksReader {
    public static void main(String[] args) {
        AllStocksReader allStocks = new AllStocksReader();
        allStocks.getStocksMap();
    }

    public Map<String, String> getStocksMap() {
        String response = HttpReader.sendGet(YiConstants.eastMoneyUrl, "", YiConstants.gbkCharset);
        //System.out.println(response);
        return parseStockString(response);
    }

    public Map<String, String> parseStockString(String str) {
        Map<String, String> stockMap = new HashMap<String, String>();
        //<li><a target="_blank" href="http://quote.eastmoney.com/sz000660.html">*ST南华(000660)</a></li>
        String regEx = "<li><a target=\"_blank\" href=\"http://quote.eastmoney.com/\\S*</a></li>";
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx);
        // 忽略大小写的写法
        Matcher matcher = pattern.matcher(str);
        // 字符串是否与正则表达式相匹配
        while (matcher.find()) {
            String stockString = matcher.group(0);
            String temp1 = stockString.split(">")[2];//华瑞股份(300626)</a
            String stockName = temp1.split("\\(")[0];//华瑞股份
            String temp2 = temp1.split("\\(")[1]; //300626)</a
            String stockCode = temp2.split("\\)")[0];//300626
            //System.out.println(stockCode + "  " + stockName);
            stockMap.put(stockCode, stockName);
        }
        //System.out.println(stockMap.size());
        return stockMap;
    }


}
