package com.leadtime;

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
            System.out.println(LeadTimeDateUtils.isSunday(date));
            System.out.println(LeadTimeDateUtils.isSaturday(date));
            date = dt.parse("2017/03/26 13:12:24");
            System.out.println(LeadTimeDateUtils.isSunday(date));
            System.out.println(LeadTimeDateUtils.isSaturday(date));
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
}
