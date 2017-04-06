package com.leadtime;

import com.leadtime.data.*;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by jianguog on 17/3/28.
 */
public class FTAEvaluationJob {
    Set<String> ftOverslaSet = new HashSet<String>();
    Set<String> goodKeySet = new HashSet<String>();
    Map<String, Integer> shipmentASINCount = new HashMap<String, Integer>();

    Map<String, FTAEvaulation> evaluationMap = new HashMap<String, FTAEvaulation>();
    Set<String> allFC = new HashSet<String>();
    Set<String> stillValidFC = new HashSet<String>();
    String type = "asin"; //fc, asin, gl, sort
//  fc    1462316;549290;0.3756301647523517;211,410  ／／1331103.9999998934;778165.0000000568;0.5846012032118596;261;410
//  gl    1462316;553238;0.3783299916023623,340     ／／ 1331103.9999998934;772220.2059524357;0.580135140419154;357;410
//  st    1462316;542008;0.37065039293832525,348     ／／ 1331103.9999998934;777760.2154762496;0.584297106369083;372;410
//        1462316;817874;0.5593004521594511;405     // 1331103.9999998934;880589.8232990226;0.6615484765270732;405;410

    //1308416.9999999134;794627.0000000624;0.6073193790665475;264;384
    Map<String, String> asinSortTypeMap = new HashMap<String, String>();
    Map<String, String> asinGLMap = new HashMap<String, String>();

    Date maxDate;
    Date minDate;

    public static void main(String[] args) {
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        String FTOverSlaList = "/Users/jianguog/dropship/ASINLevelLeadTime/data/FTOverSlaList-all"; //all,oversla,ft
        File fTOverSlaListFile = new File(FTOverSlaList);
        String FTPackage = "/Users/jianguog/dropship/ASINLevelLeadTime/data/FTPackage.txt";
        File FTPackageFile = new File(FTPackage);
        String FTEResult = "/Users/jianguog/dropship/ASINLevelLeadTime/data/ftEvaluationResult.txt";

        String asinSTFileName = "/Users/jianguog/dropship/ASINLevelLeadTime/data/asin_gl_sorttype.txt";
        File asinSTFileNameFile = new File(asinSTFileName);

        FTAEvaluationJob evaluationJob = new FTAEvaluationJob();
        try {
            evaluationJob.readFCs(fTOverSlaListFile);
            evaluationJob.readAsinGLSorttype(asinSTFileNameFile);
            evaluationJob.checkShipmentAsinCount(FTPackageFile);
            evaluationJob.checkShipment(FTPackageFile);
            evaluationJob.recheckShipment(FTPackageFile);
            //evaluationJob.printImpact();
            //evaluationJob.printImpact(FTEResult);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FTAEvaluationJob(){
        SimpleDateFormat dt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            maxDate = dt.parse("2017/04/01 00:00:00");
            minDate = dt.parse("2017/03/01 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void readAsinGLSorttype(File file) throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while((line = br.readLine()) != null){
            String[] colums = line.split("\t");
            String node = colums[4];

            if (!ftOverslaSet.contains(node)){
                continue;
            }
            String asin = colums[5];
            String gl = colums[7];
            String sortType =  colums[8];

            asinSortTypeMap.put(asin, sortType);
            asinGLMap.put(asin, gl);
            //System.out.println(nodeLeadtime);
        }
        br.close();
        fr.close();
    }

    public void checkShipmentAsinCount(File file) throws IOException {

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
            if(ftShipmentDW.getPadDate().after(maxDate) || ftShipmentDW.getPadDate().before(minDate)){
                continue;
            }
            Integer count = shipmentASINCount.get(ftShipmentDW.getShipmentId());
            if(count == null) {
                count = new Integer(1);
            } else {
               count++;
            }
            shipmentASINCount.put(ftShipmentDW.getShipmentId(), count);
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

            FTShipmentDW ftShipmentDW = new FTShipmentDW(line);
            if (!ftOverslaSet.contains(ftShipmentDW.getNode())){
                continue;
            }
            if(ftShipmentDW.getPadDate().after(maxDate) || ftShipmentDW.getPadDate().before(minDate)){
                continue;
            }
            ftShipmentDW.setSortType(asinSortTypeMap.get(ftShipmentDW.getAsin()));
            ftShipmentDW.setGlname(asinGLMap.get(ftShipmentDW.getAsin()));
            String key = getKey(ftShipmentDW, type);

            //System.out.println(ftShipmentDW);
            FTAEvaulation ftaEvaulation = evaluationMap.get(key);

            if (ftaEvaulation == null) {
                ftaEvaulation = new FTAEvaulation();
                ftaEvaulation.setKey(key);
                evaluationMap.put(key, ftaEvaulation);
            }
            Integer count = shipmentASINCount.get(ftShipmentDW.getShipmentId());

            //ftaEvaulation.addTotal();
            //System.out.println(count);
            ftaEvaulation.setTotal(ftaEvaulation.getTotal() + (1.0/count));
            if (ftShipmentDW.getExsd() ==  0 ){
                ftaEvaulation.addExsdMeet();
            }
            if (ftShipmentDW.getEdd() ==  1 ){
                ftaEvaulation.addEddMeet();
            }

            ftaEvaulation.setDeameet(ftaEvaulation.getDeameet() + (ftShipmentDW.getDea()/count));

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
        double totalcount = 0;
        double goodcount = 0;
        while((line = br.readLine()) != null){

            FTShipmentDW ftShipmentDW = new FTShipmentDW(line);
            if (!ftOverslaSet.contains(ftShipmentDW.getNode())){
                continue;
            }
            if(ftShipmentDW.getPadDate().after(maxDate) || ftShipmentDW.getPadDate().before(minDate)){
                continue;
            }
            ftShipmentDW.setSortType(asinSortTypeMap.get(ftShipmentDW.getAsin()));
            ftShipmentDW.setGlname(asinGLMap.get(ftShipmentDW.getAsin()));
            String key = getKey(ftShipmentDW, type);
            if(evaluationMap.get(key) == null){
//                System.out.println(line + ":"+key);
                continue;
            }
            Integer count = shipmentASINCount.get(ftShipmentDW.getShipmentId());

            totalcount= totalcount+ (1.0/count);
            allFC.add(ftShipmentDW.getNode());
            if (evaluationMap.get(key).getDeaPercent() >= 0.94
                    && evaluationMap.get(key).getSrPercent() >= 0.9) {
                //System.out.println("----"+evaluationMap.get(key).getPercent());
                stillValidFC.add(ftShipmentDW.getNode());
                goodcount= goodcount+ (1.0/count);
                //goodcount++;
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
        System.out.println(totalcount + ";" + goodcount+";"+(1.0*goodcount)/totalcount + ";"+ stillValidFC.size()+ ";"+ allFC.size());
    }

    String getKey(FTShipmentDW ftShipmentDW, String type){
        if (type.equalsIgnoreCase("fc")){
            return ftShipmentDW.getNode();
        }
        if (type.equalsIgnoreCase("gl")){
          return ftShipmentDW.getNode() + "," + ftShipmentDW.getGlname();
        }
        if (type.equalsIgnoreCase("sort")){
            return  ftShipmentDW.getNode() + "," + ftShipmentDW.getSortType();
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