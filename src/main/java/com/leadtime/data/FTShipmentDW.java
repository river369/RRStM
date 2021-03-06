package com.leadtime.data;

import com.leadtime.utils.LeadTimeDateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jianguog on 17/4/1.
 */
public class FTShipmentDW {
    String shipmentId;
    String node;
    String asin;
    double dea;
    int edd;
    int sr;
    int exsd;
    String glname;
    String sortType;
    Date padDate;


//    warehouse_id	Legal Entity Id	fulfillment_shipment_id	ASIN	Order DateTime	ship_datetime	expected_ship_datetime	att_del_failed_pdd
// att_del_failed_edd	no_att_scan	units	total_packages	dea	edd	sr	is_miss_exsd
//    ABCJ	101	2272105260083	B008UQKTNO	2017/03/21 12:15:11	2017/03/22 14:51:18	2017/03/23 10:00:00	0	0	0	1	1	1	1	1	0


    public FTShipmentDW(String line) {
        //System.out.println(line);
        String[] colums = line.split("\t");
        shipmentId = colums[2];
        node = colums[0];
        asin = colums[3];
        dea = Double.parseDouble(colums[13]);
        edd = (int)Double.parseDouble(colums[14]);
        sr = (int)Double.parseDouble(colums[15]);
        exsd = (int)Double.parseDouble(colums[16]);

//        dea = Double.parseDouble(colums[12]);
//        edd = (int)Double.parseDouble(colums[13]);
//        sr = (int)Double.parseDouble(colums[14]);
//        exsd = (int)Double.parseDouble(colums[15]);

        SimpleDateFormat dt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {

            this.padDate = dt.parse(colums[7]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getAsin() {
        return asin;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }

    public double getDea() {
        return dea;
    }

    public void setDea(double dea) {
        this.dea = dea;
    }

    public int getEdd() {
        return edd;
    }

    public void setEdd(int edd) {
        this.edd = edd;
    }

    public int getSr() {
        return sr;
    }

    public void setSr(int sr) {
        this.sr = sr;
    }

    public int getExsd() {
        return exsd;
    }

    public void setExsd(int exsd) {
        this.exsd = exsd;
    }

    public String getGlname() {
        return glname;
    }

    public void setGlname(String glname) {
        this.glname = glname;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public Date getPadDate() {
        return padDate;
    }

    public void setPadDate(Date padDate) {
        this.padDate = padDate;
    }

    @Override
    public String toString() {
        return node + '\'' +
                ", asin='" + asin + '\'' +
                ", dea=" + dea +
                ", edd=" + edd +
                ", sr=" + sr +
                ", exsd=" + exsd +
                '}';
    }
}
