package com.leadtime;

import com.leadtime.data.*;

import java.io.*;
import java.util.*;

/**
 * Created by jianguog on 17/3/28.
 */
public class FTAEvaluationJob {
    Set<String> ftOverslaSet = new HashSet<String>();
    Set<String> goodKeySet = new HashSet<String>();
    Map<String, FTAEvaulation> evaluationMap = new HashMap<String, FTAEvaulation>();
    String type = "fc"; //fc, asin, gl, sort

    public static void main(String[] args) {
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        String FTOverSlaList = "/Users/jianguog/dropship/ASINLevelLeadTime/data/FTOverSlaList-all";
        File fTOverSlaListFile = new File(FTOverSlaList);
        String FTPackage = "/Users/jianguog/dropship/ASINLevelLeadTime/data/FTPackage.txt";
        File FTPackageFile = new File(FTPackage);
        String FTEResult = "/Users/jianguog/dropship/ASINLevelLeadTime/data/ftEvaluationResult.txt";

        FTAEvaluationJob evaluationJob = new FTAEvaluationJob();
        try {
            evaluationJob.readFCs(fTOverSlaListFile);
            evaluationJob.checkShipment(FTPackageFile);
            evaluationJob.recheckShipment(FTPackageFile);
            //evaluationJob.printImpact();
            //evaluationJob.printImpact(FTEResult);
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

            FTShipmentDW ftShipmentDW = new FTShipmentDW(line);
            if (!ftOverslaSet.contains(ftShipmentDW.getNode())){
                continue;
            }
            String key = getKey(ftShipmentDW, type);

            //System.out.println(ftShipmentDW);
            FTAEvaulation ftaEvaulation = evaluationMap.get(key);

            if (ftaEvaulation == null) {
                ftaEvaulation = new FTAEvaulation();
                ftaEvaulation.setKey(key);
                evaluationMap.put(key, ftaEvaulation);
            }
            ftaEvaulation.addTotal();
            if (ftShipmentDW.getExsd() ==  0 ){
                ftaEvaulation.addExsdMeet();
            }
            if (ftShipmentDW.getEdd() ==  1 ){
                ftaEvaulation.addEddMeet();
            }
            ftaEvaulation.setDeameet(ftaEvaulation.getDeameet() + ftShipmentDW.getDea());

            if (ftShipmentDW.getSr() ==  1 ){
                ftaEvaulation.addSrMeet();
            }
        }

        br.close();
        fr.close();
    }

    public void recheckShipment(File file) throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String line;
        line = br.readLine();
        int i = 0;
        int invalidCount = 0;
        int totalcount = 0;
        int goodcount = 0;
        while((line = br.readLine()) != null){

            FTShipmentDW ftShipmentDW = new FTShipmentDW(line);
            if (!ftOverslaSet.contains(ftShipmentDW.getNode())){
                continue;
            }
            if (!ftShipmentDW.getNode().equalsIgnoreCase("AEEX")){
                //continue;
            }
            String key = getKey(ftShipmentDW, type);
            totalcount++;
            if (evaluationMap.get(key).getDeaPercent() >= 0.94
                    && evaluationMap.get(key).getSrPercent() >= 0.9) {
                //System.out.println("----"+evaluationMap.get(key).getPercent());
                goodcount++;
            }
//            if (evaluationMap.get(key).getEddPercent() >= 0.9) {
//                goodcount++;
//            }
//            if (evaluationMap.get(key).getExsdPercent() >= 0.9) {
//                goodcount++;
//            }
//            if (evaluationMap.get(key).getDeaPercent() >= 0.90) {
//                //System.out.println("----"+evaluationMap.get(key).getPercent());
//                goodcount++;
//            }
//            if (evaluationMap.get(key).getSrPercent() >= 0.9) {
//                //System.out.println("----"+evaluationMap.get(key).getPercent());
//                goodcount++;
//            }
        }

        br.close();
        fr.close();
        System.out.println(totalcount + ";" + goodcount+";"+(1.0*goodcount)/totalcount);
    }

    String getKey(FTShipmentDW ftShipmentDW, String type){
        if (type.equalsIgnoreCase("fc")){
            return ftShipmentDW.getNode();
        }
        if (type.equalsIgnoreCase("gl")){
           // return ftShipmentDW.getNode() + "," + ftShipmentDW.getGlname();
        }
        if (type.equalsIgnoreCase("sort")){
            //return  ftShipmentDW.getNode() + "," + ftShipmentDW.getGlname();
        }
        if (type.equalsIgnoreCase("asin")){
            return ftShipmentDW.getNode() + "," + ftShipmentDW.getAsin();
        }

        return null;
    }
    public void readFCs(File file) throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while((line = br.readLine()) != null){
            String[] colums = line.split("\t");
            //System.out.println(colums[0]);
            ftOverslaSet.add(colums[0]);
        }
        br.close();
        fr.close();
    }

    public void printImpact(){

        for (String key : evaluationMap.keySet()) {
            System.out.println(evaluationMap.get(key));
        }

    }

    public void printImpact(String filename){
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter(filename);
            bw = new BufferedWriter(fw);
            for (String node : evaluationMap.keySet()) {
                //System.out.println(evaluationResultMap.get(node));
                bw.write(evaluationMap.get(node) + "\n");
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
}

//FC DEA
//0.9 999410;924538;0.9250837994416706

//FC DEA + SR
//999410;924538;0.9250837994416706

//ASIN DEA
//999410;831351;0.8318417866541259
//ASIN DEA + SR
//999410;831169;0.8316596792107344