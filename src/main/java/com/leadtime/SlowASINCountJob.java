package com.leadtime;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jianguog on 17/3/28.
 */
public class SlowASINCountJob {

    Map<String, String> nodeAsinMap = new HashMap<String, String>();

    public static void main(String[] args) {

        String audHistoryFileName = "/Users/jianguog/dropship/ASINLevelLeadTime/data/asinshipqty-1year-more";

        File audHistoryFile = new File(audHistoryFileName);
        SlowASINCountJob slowASINCountJob = new SlowASINCountJob();
        try {
            slowASINCountJob.checkASINCount(audHistoryFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkASINCount(File file) throws IOException {

        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String line;
        int slowASINCount = 0;
        br.readLine();
        while((line = br.readLine()) != null){
            String[] colums = line.split("\t");
            int count = Integer.parseInt(colums[1]);
            if (count > 10) {//             391220
//            if (count <=10) {//1285519 //3710813
//            if (count <=2) {//820849  //   2863358
//           if (count > 2) {  //759460   // 1238675
                slowASINCount++;
            }

        }
        System.out.println(slowASINCount);
        br.close();
        fr.close();
    }

}
