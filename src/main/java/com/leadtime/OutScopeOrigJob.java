package com.leadtime;

import com.leadtime.data.AUDShipmentDW;
import com.leadtime.data.NodeLeadtime;
import com.leadtime.data.NodeLeadtimeOutScope;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by jianguog on 17/3/28.
 */
public class OutScopeOrigJob {
    Set<String> fcs = new HashSet<String>();
    Map<Integer, Integer> worseMap = new HashMap<Integer, Integer>();
    Map<String, NodeLeadtime> leadtimeMap = new HashMap<String, NodeLeadtime>();
    Map<String, NodeLeadtimeOutScope> outMap = new HashMap<String, NodeLeadtimeOutScope>();

    public static void main(String[] args) {
        String shipmentFileName = "/Users/jianguog/dropship/ASINLevelLeadTime/data/allshipment";
        File shipmentFile = new File(shipmentFileName);
        String nodeLeadtimeFileName = "/Users/jianguog/dropship/ASINLevelLeadTime/data/node_current_leadtime";
        File nodeLeadtimeFile = new File(nodeLeadtimeFileName);

        OutScopeOrigJob outScopeOrigJob = new OutScopeOrigJob();
        try {
            outScopeOrigJob.readNodeLeadtime(nodeLeadtimeFile);
            outScopeOrigJob.checkShipment(shipmentFile);
            outScopeOrigJob.printOutScope();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public OutScopeOrigJob() {
        String[] qudsiArray = new String[]{"AFMT","AFMU","AFMV","AEEV","AEEX","AEEW","ACPH", "ADBX", "ACPI"};
        String[] wootArray = new String[] {"VUXA","AAMS","AGSL"} ;
        String[] ingramArray = new String[] {"AZOT"} ;
        for(String s : qudsiArray){
            fcs.add(s);
        }
        for(String s : wootArray){
            fcs.add(s);
        }
        for(String s : ingramArray){
            fcs.add(s);
        }
    }

    public void checkShipment(File file) throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        line = br.readLine();
        int better = 0;
        int worse = 0;
        int total = 0;
        int invalidCount = 0;
        while((line = br.readLine()) != null){
            AUDShipmentDW audShipment = new AUDShipmentDW(line);
            if (!audShipment.valid) {
                invalidCount++;
                continue;
            }
            if (fcs.contains(audShipment.getNode())){
                continue;
            }

//            if ("AAVC".equalsIgnoreCase(audShipment.getNode())){
//                System.out.println(line);
//                System.out.println(audShipment.getProcessingTime() + " === "+ audShipment.getProcessingTimeMinusWeekend());
//                continue;
//            }
            NodeLeadtime nodeLeadtime = leadtimeMap.get(audShipment.getNode());

//            if (audShipment.getNode().equalsIgnoreCase("AGOC") && audShipment.getShipDate().after(audShipment.getRsd())  ){
//                System.out.println(line);
//                System.out.println(audShipment);
//            }

//            if (audShipment.getProcessingTimeMinusWeekend() <  audShipment.getProcessingTime()){
//                System.out.println(line);
//                System.out.println(audShipment);
//            }
            if (nodeLeadtime != null) {
                if (nodeLeadtime.getLeadtime() == 0) {
                    continue;
                }
                audShipment.setLeadtime(nodeLeadtime.getLeadtime());
                //audShipment.setPreleadtime(nodeLeadtime.getPreleadtime());
                NodeLeadtimeOutScope nodeLeadtimeOutScope = outMap.get(audShipment.getNode());
                if (nodeLeadtimeOutScope == null) {
                    nodeLeadtimeOutScope = new NodeLeadtimeOutScope(audShipment.getNode());
                    outMap.put(audShipment.getNode(), nodeLeadtimeOutScope );
                }
                nodeLeadtimeOutScope.addTotal();
                total++;
//                if (audShipment.getPreleadtime() != nodeLeadtime.getPreleadtime() && nodeLeadtime.getPreleadtime() !=367) {
//                    System.out.println(line);
//                    System.out.println(audShipment + "===" + nodeLeadtime.getPreleadtime());
//                }
                //if (audShipment.getProcessingTime() <= audShipment.getPreleadtime()){
                if (audShipment.getRange() <= audShipment.getPreleadtime() && audShipment.getLeadtime()>0){
                    better++;
                    nodeLeadtimeOutScope.addBetter();
                }
                //if (audShipment.getProcessingTimeMinusWeekend() > (audShipment.getLeadtime() + 12)){
                if (audShipment.getRange() > (audShipment.getLeadtime()+23-23)){
//                    Integer ik = worseMap.get(audShipment.getLeadtime());
//                    if (ik == null){
//                        ik = new Integer(0);
//                    }
//                    ik++;
//                    worseMap.put(audShipment.getLeadtime(), ik);
                    if (audShipment.getLeadtime() == 0) {
//                        System.out.println(audShipment.getNode());
//                        System.out.println(line);
//                        System.out.println(audShipment);
//                        System.out.println(audShipment.getProcessingTime() + "," +
//                                audShipment.getProcessingTimeMinusWeekend() + "," + audShipment.getRange()
//                                + "," + audShipment.getLeadtime());
                    }
                    worse++;
                    nodeLeadtimeOutScope.addWorse();
                }

            }
        }
        System.out.println(better + "," + worse + "," + total + ","+ invalidCount);
        System.out.println((better*1.0)/total + "," + (worse*1.0)/total +  ","+ invalidCount);
        System.out.println(worseMap);
        br.close();
        fr.close();
    }

    public void printOutScope(){
        for (String node : outMap.keySet()) {
            System.out.println(outMap.get(node));
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


// 191,178   132,296
//1,249,258 allshipment
//0.153033241

//181895,364597,7587
//204693,197747,7587 (after fix)
//1509315 allshipment
