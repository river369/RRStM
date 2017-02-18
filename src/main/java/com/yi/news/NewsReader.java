package com.yi.news;


import com.yi.YiConstants;
import com.yi.utils.CharsetUtils;
import com.yi.utils.HttpReader;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jianguog on 17/2/18.
 */
public class NewsReader {
    String charset = "GBK";
    String block = "板块";
    String[] keys = {"世界技术突破","国家政策","国家产业革命"};


    public static void main(String[] args) {
        NewsReader newReader = new NewsReader();
        newReader.getNewsCount();
        //newReader.parseNewsCount(null);
    }

    public void getNewsCount(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String response = HttpReader.sendGet(YiConstants.sinaSearchURL,
                buildParameters("600185", keys[1],df.format(new Date()), df.format(new Date())), charset);
        System.out.println(response);
        System.out.println(parseNewsCount(response));
    }

    String buildParameters(String stock, String key, String startTime, String endTime){
        String query = "c=news&q=" + stock + "+" + CharsetUtils.unicodeToGBK(block) + "+" + CharsetUtils.unicodeToGBK(key) + "&range=all&time=custom&stime="
                + startTime + "&etime=" + endTime + "&num=5";
        //query = "q=%C2%ED%B2%BC%C0%EF&range=all&c=news&sort=time";
        System.out.println(query);
        return query;
    }

    public String parseNewsCount(String str) {
        String regEx = "找到相关新闻\\S*篇";
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx);
        // 忽略大小写的写法
        // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        // 字符串是否与正则表达式相匹配
        boolean rs = matcher.find();
        return matcher.group(0);
    }



}
