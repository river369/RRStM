package com.yi.news;


import com.yi.YiConstants;
import com.yi.utils.CharsetUtils;
import com.yi.utils.DateUtils;
import com.yi.utils.HttpReader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jianguog on 17/2/18.
 */
public class NewsReader {

    String block = "板块";
    String[] keys = {"世界技术突破","国家政策","国家产业革命"};

    public static void main(String[] args) {
        NewsReader newReader = new NewsReader();
        newReader.getNewsCountList("600185");
        //newReader.parseNewsCount(null);
    }

    public List<Integer> getNewsCountList(String stock){
        List<Integer> newsCountList = new ArrayList<Integer>();
        for (String key : keys) {
            String response = HttpReader.sendGet(YiConstants.sinaSearchURL,
                    buildParameters(stock, key, DateUtils.getYesterdayString(), DateUtils.getTodayString()), YiConstants.gbkCharset);
            //System.out.println(response);
            String newsCountString = parseNewsCountString(response);
            int count = 0;
            if (newsCountString != null){
                count = Integer.parseInt(newsCountString);
                //System.out.println(count);
            }
            newsCountList.add(count);
        }
        return newsCountList;
    }

    String buildParameters(String stock, String key, String startTime, String endTime){
        String query = "c=news&q=" + stock + "+" + CharsetUtils.unicodeToGBK(block) + "+" + CharsetUtils.unicodeToGBK(key) + "&range=all&time=custom&stime="
                + startTime + "&etime=" + endTime + "&num=5";
        //query = "q=%C2%ED%B2%BC%C0%EF&range=all&c=news&sort=time";
        //System.out.println(query);
        return query;
    }

    public String parseNewsCountString(String str) {
        String prefix = "找到相关新闻";
        String suffix = "篇";
        String regEx = prefix + "\\S*" + suffix;
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx);
        // 忽略大小写的写法
        // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        // 字符串是否与正则表达式相匹配
        boolean rs = matcher.find();
        String newsCountString = matcher.group(0);
        if (newsCountString != null) {
//            System.out.println(newsCountString.length());
//            System.out.println(prefix.length());
//            System.out.println();
            newsCountString = newsCountString.substring(prefix.length(), newsCountString.length()-1);
            if (newsCountString != null){
                return newsCountString.replaceAll(",","");
            } else {
                return null;
            }

        } else {
            return null;
        }

    }



}
