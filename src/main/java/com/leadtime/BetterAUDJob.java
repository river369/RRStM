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
public class BetterAUDJob {

    Map<String, NodeLeadtime> leadtimeMap = new HashMap<String, NodeLeadtime>();

    public static void main(String[] args) {
        String shipmentFileName = "/Users/jianguog/dropship/ASINLevelLeadTime/data/allshipment";
        File shipmentFile = new File(shipmentFileName);
        String nodeLeadtimeFileName = "/Users/jianguog/dropship/ASINLevelLeadTime/data/node_current_leadtime";
        File nodeLeadtimeFile = new File(nodeLeadtimeFileName);

        BetterAUDJob betterAUDJob = new BetterAUDJob();
        try {
            betterAUDJob.readNodeLeadtime(nodeLeadtimeFile);
            betterAUDJob.checkShipment(shipmentFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readNodeLeadtime(File file) throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while((line = br.readLine()) != null){
            String[] colums = line.split("\t");
            String node =colums[1];
            int leadtime = Integer.parseInt(colums[4]);
            int preleadtime = Integer.parseInt(colums[5]);
            NodeLeadtime nodeLeadtime = new NodeLeadtime(node, leadtime, preleadtime);
            leadtimeMap.put(node,nodeLeadtime);
            //System.out.println(nodeLeadtime);
        }
        br.close();
        fr.close();
    }

    public void checkShipment(File file) throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        line = br.readLine();
        int i = 0;
        int invalidCount = 0;
        while((line = br.readLine()) != null){
            //System.out.println(line);
            //System.out.println(audShipment);
            AUDShipmentDW audShipment = new AUDShipmentDW(line);
            if (!audShipment.valid) {
                invalidCount++;
                continue;
            }
            NodeLeadtime nodeLeadtime = leadtimeMap.get(audShipment.getNode());
            if (nodeLeadtime != null) {
                audShipment.setLeadtime(nodeLeadtime.getLeadtime());
                audShipment.setPreleadtime(nodeLeadtime.getPreleadtime());
                //if (processingTime < nodeLeadtime.getLeadtime() ){
                if (audShipment.getProcessingTime() < nodeLeadtime.getPreleadtime()){
                    i++;
                    //System.out.println(i+ "," + audShipment);
                }
            }
        }
        System.out.println(i + "   " + invalidCount);
        br.close();
        fr.close();
    }
}

// 191,178   132,296
//1,249,258 allshipment