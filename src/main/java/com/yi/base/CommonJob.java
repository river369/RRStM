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
        while (hour < 9 || hour >= 15 || hour == 12) {
            System.out.println("The job only run between 9:00 and 15:00, the current hour is " + hour);
            sleep(60000);
            hour = DateUtils.getCurrentHour();
        }

        if (hour == 9) {
            int mins = DateUtils.getCurrentMinutes();
            while (mins < 30 ) {
                System.out.println("The job only run after 9:30, the current minutes is " + mins);
                sleep(60000);
                mins = DateUtils.getCurrentMinutes();
            }
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
