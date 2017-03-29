package com.leadtime;

import com.leadtime.data.AUDShipmentDW;
import com.leadtime.data.NodeLeadtime;
import com.leadtime.data.NodeLeadtimeRange;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by jianguog on 17/3/28.
 */
public class LeadtimeChangeRelatedASINsJob {

    Map<String, String> nodeAsinMap = new HashMap<String, String>();

    public static void main(String[] args) {

        //String audHistoryFileName = "/Users/jianguog/dropship/ASINLevelLeadTime/data/audhistory";
        String audHistoryFileName = "/Users/jianguog/dropship/ASINLevelLeadTime/data/ftahistory";

        File audHistoryFile = new File(audHistoryFileName);
        String nodeASINFileName = "/Users/jianguog/dropship/ASINLevelLeadTime/data/node_asincount.txt";
        File nodeASINFile = new File(nodeASINFileName);

        LeadtimeChangeRelatedASINsJob leadTimeDistributionJob = new LeadtimeChangeRelatedASINsJob();
        try {
            leadTimeDistributionJob.readNodeASIN(nodeASINFile);
            leadTimeDistributionJob.checkASINCount(audHistoryFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkASINCount(File file) throws IOException {

        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String line;
        while((line = br.readLine()) != null){
            String[] colums = line.split("\t");
            System.out.println(colums[1] + "," + colums[0] + "," + nodeAsinMap.get(colums[0]));
        }

        br.close();
        fr.close();
    }


    public void readNodeASIN(File file) throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while((line = br.readLine()) != null){
            String[] colums = line.split("\t");
            String node =colums[0];
            nodeAsinMap.put(node,colums[1]);
            //System.out.println(nodeLeadtime);
        }
        br.close();
        fr.close();
    }
}
