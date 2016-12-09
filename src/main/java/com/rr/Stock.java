package com.rr;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by jianguog on 16/12/8.
 */

public class Stock {

    public static void main(String[] args) {
        Stock st = new Stock();
        try {
            String fileName = "/Users/jianguog/stock/002384.txt";
            //String fileName = "/Users/jianguog/stock/600258.txt";
            st.readInputs(fileName);
            st.calculate();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    ArrayList<Double> originClosePrice = new ArrayList();
    public void calculate() throws IOException {
        double maxValue = 0;
        double maxLoc = 0;
        for (int i=2; i< 365; i++){
            double value = calculateWithNumber(i);
            if (value>maxValue) {
                maxValue = value;
                maxLoc = i;
            }
            System.out.println("i="+i + "  value="+ value);
        }
        System.out.println("maxLoc="+maxLoc + "  maxValue="+ maxValue);
    }
    public double calculateWithNumber(int avgDays) throws IOException {

        int totalDays = originClosePrice.size();
        // caluclate avg list first
        ArrayList<Double> avgPrices = buildAvgArray(avgDays, totalDays);
        ArrayList<Integer> increasePoints = getIncreasePoints(totalDays);

        double sumValue = 0;
        double count = 0;
        for(Integer i : increasePoints ){
            if (avgPrices.get(i)>originClosePrice.get(i)){
                sumValue = sumValue + avgPrices.get(i) - originClosePrice.get(i);
                count ++;
            }
        }
        return sumValue/count;
    }

    ArrayList getIncreasePoints(int totalDays) {
        ArrayList<Integer> increasePoints = new ArrayList();
        boolean isIncreasing = false;
        for (int i = 1; i<totalDays-1; i++ ){
            if (originClosePrice.get(i) > originClosePrice.get(i-1) + 0 && !isIncreasing) {
                increasePoints.add(i);
                isIncreasing = true;
            } else if (originClosePrice.get(i) < originClosePrice.get(i-1) - 0){
                isIncreasing = false;
            }
        }
        return increasePoints;

    }

    ArrayList buildAvgArray (int avgDays, int totalDays){
        ArrayList avgPrices = new ArrayList();
        int remainingDays = avgDays;
        for (int i = 0; i<totalDays; i++  ){
            if (i+ remainingDays > totalDays){
                remainingDays--;
            }
            double sum = 0;
            for (int j=0; j<remainingDays; j++){
                sum = sum + (Double)originClosePrice.get(i+j);
            }
            double avg = sum/remainingDays;
            //System.out.println(sum+ "  " + remainingDays + "  " + avg);
            avgPrices.add(avg);
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


}
