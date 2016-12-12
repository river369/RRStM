package com.rr;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by jianguog on 16/12/8.
 */

public class Stock {
    public static void main(String[] args) {

        //002384 -295
        //String fileName = "/Users/jianguog/stock/002384.txt";
        //600258-44,
        //String fileName = "/Users/jianguog/stock/600258.txt";
        //000725-193
        //String fileName = "/Users/jianguog/stock/cool.txt";
        //601369-64(12)
        String fileName = "/Users/jianguog/stock/369.txt";
        Stock st = new Stock(fileName, 5, 0.02, 0.02);
        try {
            st.calculate();
            //st.calculateSomeDays(12);
            //st.calculateSomeDays(64);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ArrayList<Double> originClosePrice = new ArrayList();
    ArrayList<Double> smoothedClosePrice = new ArrayList();
    ArrayList<Integer> increasePoints = new ArrayList();
    double min = 99999999;
    double max = -1;
    double increaseMathcRatio;
    double lineMatchRatio;
    double increaseTolearnce;
    public ArrayList<Double> getClosePrice(){
        //return smoothedClosePrice;
        return originClosePrice;
    }

    public Stock(String fileName, int avg, double increaseMathcRatio, double lineMatchRatio) {
        try {
            readInputs(fileName);
            smoothedClosePrice = buildAvgArray(avg);
            this.increaseMathcRatio = increaseMathcRatio;
            this.increaseTolearnce = (max - min) * increaseMathcRatio;
            this.lineMatchRatio = lineMatchRatio;
            increasePoints = getIncreasePoints();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void calculate() throws IOException {
        double minValue = 99990;
        double maxValue = -1;
        double maxLoc = 0;
        //for (int i=28; i< 365; i++){
        for (int i=28; i< 540; i++){
            double value = calculateWithNumber(i);
            if (value > maxValue) {
                maxValue = value;
                maxLoc = i;
            }
            System.out.println("Calculating day average ="+i + "  value="+ value);
        }
        System.out.println("maxLoc="+maxLoc + "  maxValue="+ maxValue);
        System.out.println("min=" + min + " max=" + max + " increaseMathcRatio=" + increaseMathcRatio + " increaseTolearnce="+increaseTolearnce);
    }

    public void calculateSomeDays(int day) throws IOException {
        calculateWithNumber( day );
        System.out.println("min=" + min + " max=" + max + " increaseMathcRatio=" + increaseMathcRatio + " increaseTolearnce="+increaseTolearnce);

    }

    public double calculateWithNumber(int avgDays) throws IOException {

        // caluclate avg list first
        ArrayList<Double> priceList = getClosePrice();
        ArrayList<Double> avgPrices = buildAvgArray(avgDays);

        double sumValue = 0;
        double count = 0;
        for(Integer i : increasePoints ){
            if (i > getClosePrice().size() - 365) {
                //if (avgPrices.get(i) < priceList.get(i) && priceList.get(i) - avgPrices.get(i) < priceList.get(i)*ratio) {
                if (Math.abs(avgPrices.get(i) - priceList.get(i)) < priceList.get(i)*lineMatchRatio) {
                    //sumValue = sumValue + avgPrices.get(i) - originClosePrice.get(i);
                    count++;
                    System.out.println("found = " + i + "  " + avgPrices.get(i) + "  " + priceList.get(i));
                }
            }
        }
        return count;
        //return sumValue/count;
    }

    ArrayList getIncreasePoints() {
        ArrayList<Double> priceList = getClosePrice();
        int totalDays = priceList.size();
        ArrayList<Integer> increasePoints = new ArrayList();
        boolean isIncreasing = false;
        for (int i = 0; i<totalDays-1 - 30; i++ ){
            System.out.println("checking increasing=" + i + "priceList.get(i)=" + priceList.get(i) + " " + isIncreasing);
            if (canIncreaseMeetRange(priceList, i) && !isIncreasing) {
                increasePoints.add(i);
                System.out.println("adding increase" + i);
                isIncreasing = true;
            } else if (isDecreaseMeetRange( priceList, i) && isContinueDecrease( priceList, i)){
                isIncreasing = false;
            }
        }
        System.out.println(increasePoints.size());
        return increasePoints;

    }

    boolean canIncreaseMeetRange(ArrayList<Double> priceList, int now){
        for (int i = 1; i < 30; i++){
            Double nextV = priceList.get(now + i) ;
            if(nextV == null) {
                return false;
            }
            if (priceList.get(now+i-1) > priceList.get(now + i) && priceList.get(now) > priceList.get(now+i) ){
                return false;
            }
            if (priceList.get(now) < priceList.get(now + i) - increaseTolearnce){
                return true;
            }
        }
        return false;
    }

    boolean isDecreaseMeetRange(ArrayList<Double> priceList, int now){
        for (int i = 1; i < 5; i++){
            if (priceList.get(now) > priceList.get(now + i) + increaseTolearnce){
                return true;
            }
        }
        return false;
    }
    boolean isContinueDecrease(ArrayList<Double> priceList, int now){
        for (int i = 1; i < 5; i++){
            if (priceList.get(now) <= priceList.get(now + i)){
                return false;
            }
        }
        return true;
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
            double value = Double.parseDouble(colums[4]);
            if (value > max) {
                max = value;
            }
            if (value < min) {
                min = value;
            }
            originClosePrice.add(value);
        }
        br.close();
        fr.close();
    }
}
