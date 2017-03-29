package com.leadtime.data;

import com.leadtime.utils.LeadTimeDateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jianguog on 17/3/28.
 */
public class AUDShipmentDW {
    public boolean valid = true;
    String shipmentId;
    String ASIN;
    String glcode;
    String glname;
    String node;
    Date ofDate;
    Date shipDate;
    Date rsd;
    double processingTime;
    double processingTimeMinusWeekend;
    int leadtime;
    String sortType;

    public AUDShipmentDW(String line) {
        String[] colums = line.split("\t");
        if (colums.length<11 || "".equalsIgnoreCase(colums[9])) {
            //System.out.println(line);
            valid = false;
            return;
        }
        this.shipmentId = colums[1];
        this.node = colums[4];
        this.ASIN = colums[5];
        this.glcode = colums[6];
        this.glname = colums[7];

        SimpleDateFormat dt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {

            this.ofDate = dt.parse(colums[10]);
            this.shipDate  = dt.parse(colums[0]);
            this.rsd  = dt.parse(colums[9]);
            //System.out.println(this.shipDate.getTime() + "--" + this.ofDate.getTime());
            this.processingTime = (this.shipDate.getTime() - this.ofDate.getTime())/(60*60*1000.0);
            this.processingTimeMinusWeekend = LeadTimeDateUtils.filterOutWeekend(ofDate, shipDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String toString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return shipmentId +
                "," + node  +
                "," + ASIN   +
                "," + glcode  +
                "," + glname  +
                "," + df.format(ofDate) +
                "," + df.format(shipDate) +
                "," + df.format(rsd) +
                "," + processingTime +
                "," + processingTimeMinusWeekend +
                "," + getRange() +
                "," + leadtime +
                "," + getPreleadtime() +
                "," + sortType;

    }

    public int getRange(){
        double pt = processingTimeMinusWeekend;
        if (pt < 6) {
            return 0;
        }
        if (pt > 6 && pt <=23) {
            return 23;
        }
        if (pt > 23  && pt <= 47) {
            return 47;
        }
        if (pt > 47  && pt <= 71) {
            return 71;
        }
        if (pt > 71  && pt <= 119) {
            return  119;
        }
        if (pt > 119 && pt <= 167) {
            return 167;
        }
        if (pt > 167 && pt <= 335) {
            return 335;
        }
        if (pt > 335 && pt <= 503) {
            return 503;
        }
        if (pt > 503  && pt <= 671) {
            return 671;
        }
        if (pt > 671  && pt <= 839) {
            return 839;
        }
        if (pt > 839  && pt <= 1007) {
            return 1007;
        }
        if (pt > 1007) {
            return 2000;
        }
        return -1;
    }

    public int getPreleadtime() {
        if (leadtime <=23) {
            return 0;
        }
        if (leadtime <= 47) {
            return 23;
        }
        if (leadtime <= 71) {
            return 47;
        }
        if (leadtime <= 119) {
            return 71;
        }
        if (leadtime <= 335) {
            return 119;
        }
        if (leadtime <= 503) {
            return 335;
        }
        if (leadtime <= 671) {
            return 503;
        }
        if (leadtime <= 839) {
            return 503;
        }
        if (leadtime <= 1007) {
            return 671;
        }
        if (leadtime == 2000) {
            return 1007;
        }
        return -1;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public String getGlcode() {
        return glcode;
    }

    public void setGlcode(String glcode) {
        this.glcode = glcode;
    }

    public String getGlname() {
        return glname;
    }

    public void setGlname(String glname) {
        this.glname = glname;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getASIN() {
        return ASIN;
    }

    public void setASIN(String ASIN) {
        this.ASIN = ASIN;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public Date getOfDate() {
        return ofDate;
    }

    public void setOfDate(Date ofDate) {
        this.ofDate = ofDate;
    }

    public Date getShipDate() {
        return shipDate;
    }

    public void setShipDate(Date shipDate) {
        this.shipDate = shipDate;
    }

    public Date getRsd() {
        return rsd;
    }

    public void setRsd(Date rsd) {
        this.rsd = rsd;
    }

    public double getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(double processingTime) {
        this.processingTime = processingTime;
    }

    public int getLeadtime() {
        return leadtime;
    }

    public void setLeadtime(int leadtime) {
        this.leadtime = leadtime;
    }

    public double getProcessingTimeMinusWeekend() {
        return processingTimeMinusWeekend;
    }

    public void setProcessingTimeMinusWeekend(double processingTimeMinusWeekend) {
        this.processingTimeMinusWeekend = processingTimeMinusWeekend;
    }
}
