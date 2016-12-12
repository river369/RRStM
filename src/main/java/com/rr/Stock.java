package com.rr;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by jianguog on 16/12/8.
 */

public class Stock {

    ArrayList<Double> originClosePrice = new ArrayList();

    public static void main(String[] args) {

        //String fileName = "/Users/jianguog/stock/002384.txt";
        //String fileName = "/Users/jianguog/stock/600258.txt";
        //String fileName = "/Users/jianguog/stock/cool.txt";
        String fileName = "/Users/jianguog/stock/369.txt";
        Stock st = new Stock(fileName);
        try {
            st.calculate();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

//    public void calculate() throws IOException {
//        double maxValue = 0;
//        double maxLoc = 0;
//        for (int i=2; i< 730; i++){
//            double value = calculateWithNumber(i);
//            if (value>maxValue) {
//                maxValue = value;
//                maxLoc = i;
//            }
//            System.out.println("i="+i + "  value="+ value);
//        }
//        System.out.println("maxLoc="+maxLoc + "  maxValue="+ maxValue);
//    }

    public void calculate() throws IOException {
        double minValue = 99990;
        double maxLoc = 0;
        for (int i=2; i< 730; i++){
            double value = calculateWithNumber(i);
            if (value < minValue) {
                minValue = value;
                maxLoc = i;
            }
            System.out.println("i="+i + "  value="+ value);
        }
        System.out.println("maxLoc="+maxLoc + "  maxValue="+ minValue);
    }
    public double calculateWithNumber(int avgDays) throws IOException {

        // caluclate avg list first
        ArrayList<Double> avgPrices = buildAvgArray(avgDays);
        ArrayList<Integer> increasePoints = getIncreasePoints();

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

    ArrayList getIncreasePoints() {
        int totalDays = originClosePrice.size();
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
//        for (Double d : avgPrices){
//            System.out.println(d);
//        }
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

    public Stock(String fileName) {
        try {
            readInputs(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
