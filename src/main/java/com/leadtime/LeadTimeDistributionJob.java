package com.leadtime;

import com.leadtime.data.AUDShipmentDW;
import com.leadtime.data.NodeLeadtime;
import com.leadtime.data.NodeLeadtimeRange;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jianguog on 17/3/28.
 */
public class LeadTimeDistributionJob {

    Map<String, NodeLeadtimeRange> leadtimeRangeMap = new HashMap<String, NodeLeadtimeRange>();
    Map<String, NodeLeadtime> leadtimeMap = new HashMap<String, NodeLeadtime>();

    public static void main(String[] args) {
        String shipmentFileName = "/Users/jianguog/dropship/ASINLevelLeadTime/data/allshipment";
        File shipmentFile = new File(shipmentFileName);
        String nodeLeadtimeFileName = "/Users/jianguog/dropship/ASINLevelLeadTime/data/node_current_leadtime";
        File nodeLeadtimeFile = new File(nodeLeadtimeFileName);

        LeadTimeDistributionJob leadTimeDistributionJob = new LeadTimeDistributionJob();
        try {
            leadTimeDistributionJob.readNodeLeadtime(nodeLeadtimeFile);
            leadTimeDistributionJob.checkShipment(shipmentFile);
            leadTimeDistributionJob.printDistribution();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkShipment(File file) throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        line = br.readLine();
        int i = 0;
        int invalidCount = 0;
        while((line = br.readLine()) != null){
            AUDShipmentDW audShipment = new AUDShipmentDW(line);
            if (!audShipment.valid) {
                invalidCount ++;
                continue;
            }

            NodeLeadtimeRange nodeLeadtimeRange = leadtimeRangeMap.get(audShipment.getNode());
            if (nodeLeadtimeRange == null) {
                nodeLeadtimeRange = new NodeLeadtimeRange();
                NodeLeadtime nodeLeadtime = leadtimeMap.get(audShipment.getNode());
                if (nodeLeadtime != null) {
                    nodeLeadtimeRange.setLeadtime(nodeLeadtime.getLeadtime());
                }
                nodeLeadtimeRange.setNode(audShipment.getNode());
                leadtimeRangeMap.put(audShipment.getNode(), nodeLeadtimeRange);
            }
            nodeLeadtimeRange.setData(audShipment.getProcessingTimeMinusWeekend());
            //System.out.println(line);
            //System.out.println(audShipment);
        }
        br.close();
        fr.close();
    }

    public void printDistribution(){
        for (String node : leadtimeRangeMap.keySet()) {
            System.out.println(leadtimeRangeMap.get(node));
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
}
