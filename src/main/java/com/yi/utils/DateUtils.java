package com.yi.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by jianguog on 17/2/19.
 */
public class DateUtils {

    public static void main(String[] args) {

//        System.out.println(System.currentTimeMillis());
//        System.out.println(getTodayString());
//        System.out.println(getYesterdayString());
//        System.out.println(getCurrentTimeToSecondString());
//        System.out.println(getCurrentTimeToMinuteString());
//        System.out.println(getCurrentHour());
//        System.out.println(getCurrentMinutes());
    }

    public static String getTodayString(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(new Date());
    }

    public static boolean isToday(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = df.format(date);
        //System.out.println(dateString);
        //System.out.println(LeadTimeDateUtils.getTodayString());
        return dateString.equalsIgnoreCase(DateUtils.getTodayString());
    }

    public static String getYesterdayString(){
        return DateUtils.getDaysString(-1);
//        Date date=new Date();//取时间
//        Calendar calendar = new GregorianCalendar();
//        calendar.setTime(date);
//        calendar.add(calendar.DATE,-1);//把日期往前减少一天，若想把日期向后推一天则将负数改为正数
//        date=calendar.getTime();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        return formatter.format(date);
    }

    public static String getDaysString(int days){
        Date date=new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, days);
        date=calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }


    public static String getCurrentTimeToSecondString(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        return df.format(new Date());
    }

    public static String getCurrentTimeToMinuteString(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH:mm");
        return df.format(new Date());
    }

    public static int getCurrentHour(){
        SimpleDateFormat df = new SimpleDateFormat("HH");
        return Integer.parseInt(df.format(new Date()));
    }

    public static int getCurrentMinutes(){
        SimpleDateFormat df = new SimpleDateFormat("mm");
        return Integer.parseInt(df.format(new Date()));
    }

}
