package com.leadtime.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by jianguog on 17/3/28.
 */
public class LeadTimeDateUtils {
    public static void main(String[] args) {
        SimpleDateFormat dt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            Date date = dt.parse("2017/03/25 13:12:24");

            Date start = dt.parse("2017/03/21 13:12:24");
            Date end = dt.parse("2017/03/23 13:12:24");
            System.out.println(LeadTimeDateUtils.filterOutWeekend(start, end));//48

            start = dt.parse("2017/03/24 13:12:24");
            end = dt.parse("2017/03/27 13:12:24");
            System.out.println(LeadTimeDateUtils.filterOutWeekend(start, end));//24

            start = dt.parse("2017/03/25 13:12:24");
            end = dt.parse("2017/03/29 13:12:24");
            System.out.println(LeadTimeDateUtils.filterOutWeekend(start, end));//61

            start = dt.parse("2017/03/26 13:12:24");
            end = dt.parse("2017/03/27 13:12:24");
            System.out.println(LeadTimeDateUtils.filterOutWeekend(start, end));//13

//            start = dt.parse("2017/03/12 23:30:54");
//            end = dt.parse("2017/03/15 12:16:36");
//            System.out.println(LeadTimeDateUtils.filterOutWeekend(start, end));//13
//            	2256616721083	107-8967533-4969869	2017/03/12 19:27:17	ADGD	B005HSGXG0	196	gl_furniture	1	2017/03/20 09:00:00
//            2256616721083,ADGD,B005HSGXG0,196,gl_furniture,2017-03-12 23:30:54,2017-03-15 12:16:36,2017-03-20 09:00:00,60.76166666666666,48.76166666666666,71,0,0
        } catch (ParseException e) {
            e.printStackTrace();
        }


//        System.out.println(System.currentTimeMillis());
//        System.out.println(getTodayString());
//        System.out.println(getYesterdayString());
//        System.out.println(getCurrentTimeToSecondString());
//        System.out.println(getCurrentTimeToMinuteString());
//        System.out.println(getCurrentHour());
//        System.out.println(getCurrentMinutes());
    }

    public static boolean isSunday(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK);
        return w == 1;
    }
    public static boolean isSaturday(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK);
        return w == 7;

    }
    public static int getWeekDay(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK);
        return w;

    }
    public static int getCurrentHour(Date date){
        SimpleDateFormat df = new SimpleDateFormat("HH");
        return Integer.parseInt(df.format(date));
    }

    /**
     * This is not a common functions, depends on range between 2-7
     * @param start
     * @param end
     * @return
     */
    public static double filterOutWeekend(Date start, Date end){

        double processingTime = (end.getTime() - start.getTime())/(60*60*1000.0);
        if (processingTime > 7*24 || processingTime < 48) {
            return processingTime;
        }
        double processingTimeMinusWeekend = processingTime;
        int startWeekDay = getWeekDay(start);
        int endWeekDay = getWeekDay(end);
        if (endWeekDay > startWeekDay && endWeekDay !=7 && startWeekDay != 1){ // before sat
        } else if (endWeekDay == 7 ) { // at sat, assume vendor still work
        } else if (endWeekDay < startWeekDay && endWeekDay != 1 && startWeekDay != 7) {
            processingTimeMinusWeekend -= 48;
        } else if ( endWeekDay == 1){// at sat, assume vendor still work
        } else if (startWeekDay == 7) {
            processingTimeMinusWeekend -= (24+(24-getCurrentHour(start)));
        } else if (startWeekDay == 1) {
            processingTimeMinusWeekend -= (24-getCurrentHour(start));
        }
        //System.out.println(startWeekDay + "," + endWeekDay + "," + processingTime + ","+ processingTimeMinusWeekend);
        return processingTimeMinusWeekend;
    }
}
