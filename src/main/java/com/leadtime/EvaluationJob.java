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
public class EvaluationJob {

    Map<String, NodeLeadtime> leadtimeMap = new HashMap<String, NodeLeadtime>();
    Map<String, NodeLeadtime> evaluationMap = new HashMap<String, NodeLeadtime>();

    public static void main(String[] args) {

        String shipmentFileName = "/Users/jianguog/dropship/ASINLevelLeadTime/data/allshipment";
        File shipmentFile = new File(shipmentFileName);

        EvaluationJob evaluationJob = new EvaluationJob();
        try {
            evaluationJob.checkShipment(shipmentFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkShipment(File file) throws IOException {
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

        }
        bw.close();
        fw.close();

        br.close();
        fr.close();
    }

}
