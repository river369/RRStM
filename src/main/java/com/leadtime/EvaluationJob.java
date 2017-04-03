package com.leadtime;

import com.leadtime.data.AUDShipmentDW;
import com.leadtime.data.Evaulation;
import com.leadtime.data.NodeLeadtime;
import com.leadtime.data.NodeLeadtimeRange;

import java.io.*;
import java.util.*;

/**
 * Created by jianguog on 17/3/28.
 */
public class EvaluationJob {
    Set<String> fcs = new HashSet<String>();
    Map<String, List<AUDShipmentDW>> evaluationListMap = new HashMap<String, List<AUDShipmentDW>>();
    Map<String, Evaulation> evaluationResultMap = new HashMap<String, Evaulation>();
    Map<String, NodeLeadtime> leadtimeMap = new HashMap<String, NodeLeadtime>();

    Map<String, String> asinSortTypeMap = new HashMap<String, String>();

    //double bestPercentThreshold = 0.35;
    double bestPercentThreshold = 0.15;

    public static void main(String[] args) {
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        String shipmentFileName = "/Users/jianguog/dropship/ASINLevelLeadTime/data/allshipment";
        File shipmentFile = new File(shipmentFileName);

        String asinSTFileName = "/Users/jianguog/dropship/ASINLevelLeadTime/data/asinsorttype";
        File asinSTFileNameFile = new File(asinSTFileName);

        String outputFileName = "/Users/jianguog/dropship/ASINLevelLeadTime/data/evaluation.txt";

        String nodeLeadtimeFileName = "/Users/jianguog/dropship/ASINLevelLeadTime/data/node_current_leadtime";
        File nodeLeadtimeFile = new File(nodeLeadtimeFileName);

        EvaluationJob evaluationJob = new EvaluationJob();
        try {
            evaluationJob.readNodeLeadtime(nodeLeadtimeFile);
            evaluationJob.readAsinSorttype(asinSTFileNameFile);
            evaluationJob.checkShipment(shipmentFile);
            evaluationJob.evaluation();
            evaluationJob.printEvaluation(outputFileName);
            //evaluationJob.printEvaluationInConsole();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public EvaluationJob() {
        //fcs.add("AMUG");
//
//        String[] qudsiArray = new String[]{"AFMT","AFMU","AFMV","AEEV","AEEX","AEEW","ACPH", "ADBX", "ACPI"};
//        for(String s : qudsiArray){
//            fcs.add(s);
//        }
//
//        String[] wootArray = new String[] {"VUXA","AAMS","AGSL"} ;
//        for(String s : wootArray){
//            fcs.add(s);
//        }
//
//        String[] ingramArray = new String[] {"AZOT"} ;
//        for(String s : ingramArray){
//            fcs.add(s);
//        }
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
            if (!audShipment.valid ) {
                invalidCount ++;
                continue;
            }
            if  (fcs.contains(audShipment.getNode())){
                continue;
            }

            NodeLeadtime nodeLeadtime = leadtimeMap.get(audShipment.getNode());

            if (nodeLeadtime == null || nodeLeadtime.getLeadtime() == 0) {
                continue;
            }

            audShipment.setSortType(asinSortTypeMap.get(audShipment.getASIN()));

//            if  (!audShipment.getNode().equalsIgnoreCase("AAFY")){
//                continue;
//            }
//            if (!audShipment.getASIN().equalsIgnoreCase("B00DD5ZP4U")){
//                continue;
//            }
//            System.out.println(line);
//            System.out.println(audShipment);

            //Test at FC level
            // String key = audShipment.getNode();
            // Test at GL level
            //String key = audShipment.getNode() + "," + audShipment.getGlname();
            // Test at Sortype

            //String key = audShipment.getNode() + "," + audShipment.getSortType();
            // Test at ASIN
            String key = audShipment.getNode() + "," + audShipment.getASIN()+ "," + audShipment.getSortType();

            List<AUDShipmentDW> evaluationList = evaluationListMap.get(key);
            if (evaluationList == null) {
                evaluationList = new ArrayList<AUDShipmentDW>();
                evaluationListMap.put(key, evaluationList);
            }

            evaluationList.add(audShipment);

        }

        br.close();
        fr.close();
    }

    public void evaluation(){
        for (String key : evaluationListMap.keySet()) {
            List<AUDShipmentDW> evaluationList = evaluationListMap.get(key);
            Collections.sort(evaluationList, new Comparator<AUDShipmentDW>() {
                public int compare(AUDShipmentDW o1, AUDShipmentDW o2) {
                    return o1.getRange() < o2.getRange() ? 1 : -1;
                }
            });

            int selectBlockCount = (int)(evaluationList.size() * bestPercentThreshold);
            AUDShipmentDW selected = evaluationList.get(selectBlockCount);
            int selectedRange = selected.getRange();

            //System.out.println(selectBlockCount + "++"+ selectedRange);

            Evaulation evaulation = evaluationResultMap.get(key);
            if (evaulation == null) {
                evaulation = new Evaulation(key);
                evaulation.setSelectedLeadtime(selectedRange);
                evaluationResultMap.put(key, evaulation);
            }
            for (AUDShipmentDW audShipmentDW :evaluationList) {
                if (audShipmentDW.getRange() > selectedRange){
                    evaulation.addWorse();
                } else if (audShipmentDW.getRange() < selectedRange){
                    evaulation.addBetter();
                }
                evaulation.addTotal();
                //System.out.println(audShipmentDW);
            }

        }

    }

    public void printEvaluation(String filename){
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter(filename);
            bw = new BufferedWriter(fw);
            for (String node : evaluationResultMap.keySet()) {
                //System.out.println(evaluationResultMap.get(node));
                bw.write(evaluationResultMap.get(node) + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
                try {
                    if (fw != null) {
                        bw.close();
                    }
                    if (fw != null){
                        fw.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

        }
    }
    public void printEvaluationInConsole(){
        for (String node : evaluationResultMap.keySet()) {
            System.out.println(evaluationResultMap.get(node));
        }

    }

    public void readAsinSorttype(File file) throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while((line = br.readLine()) != null){
            String[] colums = line.split("\t");
            String asin = colums[5];
            String sortType =  colums[8];
            asinSortTypeMap.put(asin, sortType);
            //System.out.println(nodeLeadtime);
        }
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
