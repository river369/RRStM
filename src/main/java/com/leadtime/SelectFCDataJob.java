package com.leadtime;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by jianguog on 17/3/28.
 */
public class SelectFCDataJob {

    Set<String> fcs = new HashSet<String>();

    Map<String, NodeLeadtimeRange> leadtimeRangeMap = new HashMap<String, NodeLeadtimeRange>();
    Map<String, NodeLeadtime> leadtimeMap = new HashMap<String, NodeLeadtime>();

    public static void main(String[] args) {

        String shipmentFileName = "/Users/jianguog/dropship/ASINLevelLeadTime/data/allshipment";
        File shipmentFile = new File(shipmentFileName);
//        String nodeLeadtimeFileName = "/Users/jianguog/dropship/ASINLevelLeadTime/data/node_current_leadtime";
//        File nodeLeadtimeFile = new File(nodeLeadtimeFileName);

        SelectFCDataJob leadTimeDistributionJob = new SelectFCDataJob();
        try {
            //leadTimeDistributionJob.readNodeLeadtime(nodeLeadtimeFile);
            leadTimeDistributionJob.checkShipment(shipmentFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkShipment(File file) throws IOException {
        fcs.add("AHER");
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        FileWriter fw = new FileWriter(file.getAbsolutePath()+ ".fc.txt");
        BufferedWriter bw = new BufferedWriter(fw);

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

            if (fcs.contains(audShipment.getNode())) {
                bw.write(audShipment + "\n");
                //System.out.println(audShipment);
            }
        }
        bw.close();
        fw.close();

        br.close();
        fr.close();
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
