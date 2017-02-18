package com.test;

/**
 * Created by jianguog on 17/1/11.
 */
public class NodeAdress {
    public String zip;
    public String line1;
    public String line2;
    public String line3;
    public String city;
    public String state;
    public String country;

    @Override
    public String toString() {
        return  zip + "," + line1+ "," + line2+ "," + line3 + "," + city+ "," + state+ "," + country;
    }
}
