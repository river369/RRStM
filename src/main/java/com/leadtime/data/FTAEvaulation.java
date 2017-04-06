package com.leadtime.data;

/**
 * Created by jianguog on 17/3/29.
 */
public class FTAEvaulation {
    String key;
    double total;
    double deameet;
    int eddmeet;
    int exsdmeet;
    int srmeet;

    public void addTotal(){
        total++;
    }
    public void addDeaMeet(){
        deameet++;
    }
    public void addExsdMeet(){
        exsdmeet++;
    }
    public void addEddMeet(){
        eddmeet++;
    }
    public void addSrMeet(){
        srmeet++;
    }

    public double getSrPercent() {
        return 1.0*srmeet/total;
    }
    public double getDeaPercent() {
        return 1.0*deameet/total;
    }
    public double getExsdPercent() {
        return 1.0*exsdmeet/total;
    }
    public double getEddPercent() {
        return 1.0*eddmeet/total;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getDeameet() {
        return deameet;
    }

    public void setDeameet(double deameet) {
        this.deameet = deameet;
    }

    public int getEddmeet() {
        return eddmeet;
    }

    public void setEddmeet(int eddmeet) {
        this.eddmeet = eddmeet;
    }

    public int getExsdmeet() {
        return exsdmeet;
    }

    public void setExsdmeet(int exsdmeet) {
        this.exsdmeet = exsdmeet;
    }

    public int getSrmeet() {
        return srmeet;
    }

    public void setSrmeet(int srmeet) {
        this.srmeet = srmeet;
    }

    @Override
    public String toString() {
        return key +
                "," + total +
                "," + deameet +
                "," + eddmeet +
                "," + exsdmeet +
                "," + srmeet +
                "," + getDeaPercent() +
                "," + getEddPercent() +
                "," + getExsdPercent() +
                "," + getSrPercent() ;
    }
}
