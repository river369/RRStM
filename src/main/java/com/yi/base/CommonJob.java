package com.yi.base;

import com.yi.utils.DateUtils;

/**
 * Created by jianguog on 17/3/7.
 */
public class CommonJob {
    public boolean alwayRun;

    public CommonJob(boolean alwayRun) {
        this.alwayRun = alwayRun;
    }

    public void checkTime(){
        if (alwayRun) return;
        int hour = DateUtils.getCurrentHour();
        while (hour < 9 || hour > 15 ) {
            System.out.println("The job only run between 9:00 and 15:00, the current hour is " + hour);
            sleep(60000);
        }
    }

    public void sleep(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
