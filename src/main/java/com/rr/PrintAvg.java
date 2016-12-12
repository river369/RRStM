package com.rr;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by jianguog on 16/12/8.
 */

public class PrintAvg {

    ArrayList<Double> originClosePrice = new ArrayList();

    public static void main(String[] args) {
        //String fileName = "/Users/jianguog/stock/002384.txt";
        //String fileName = "/Users/jianguog/stock/600258.txt";
        String fileName = "/Users/jianguog/stock/369.txt";
        PrintAvg st = new PrintAvg(fileName);
        st.buildAvgArray(71);
    }

    ArrayList buildAvgArray (int avgDays){
        int totalDays = originClosePrice.size();
        ArrayList<Double> avgPrices = new ArrayList();
        for (int i = 0; i<avgDays ; i++  ){
            avgPrices.add(0.0);
        }
        for (int i = avgDays; i<totalDays; i++  ){
            double sum = 0;
            for (int j=0; j<avgDays; j++){
                sum = sum + (Double)originClosePrice.get(i-j -1);
            }
            double avg = sum/avgDays;
            //System.out.println(sum+ "  " + remainingDays + "  " + avg);

            avgPrices.add(avg);
        }
        for (Double d : avgPrices){
            System.out.println(d);
        }
        return avgPrices;
    }
    public void readInputs(String fileName) throws IOException {
        File file = new File(fileName);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while((line = br.readLine()) != null){
            String[] colums = line.split("\t");
            originClosePrice.add(Double.parseDouble(colums[4]));
        }
        br.close();
        fr.close();
    }

    public PrintAvg(String fileName) {
        try {
            readInputs(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
