package com.leadtime;

/**
 * Created by jianguog on 17/3/28.
 */
public class NodeLeadtimeRange {
    String node;
    int leadtime;

    int l0;
    int l23;
    int l47;
    int l71;
    int l119;
    int l167;
    int l335;
    int l503;
    int l671;
    int l839;
    int l1007;
    int upper;
//23	24	24 hours
//47	48	1-2 days
//71	72	2-3 days
//119	120	3-5 days
//167	168	5-7 days
//335	336	1-2 weeks
//503	504	2-3 weeks
//671	672	3-4 weeks
//839	840	3-5 weeks
//1007	1008	4-6 weeks

    void setData(double pt){
        if (pt < 6) {
            l0++;
        }
        if (pt > 6 && pt <=23) {
            l23++;
        }
        if (pt > 23  && pt <= 47) {
            l47++;
        }
        if (pt > 47  && pt <= 71) {
            l71++;
        }
        if (pt > 71  && pt <= 119) {
            l119++;
        }
        if (pt > 119 && pt <= 167) {
            l167++;
        }
        if (pt > 167 && pt <= 335) {
            l335++;
        }
        if (pt > 335 && pt <= 503) {
            l503++;
        }
        if (pt > 503  && pt <= 671) {
            l671++;
        }
        if (pt > 671  && pt <= 839) {
            l839++;
        }
        if (pt > 839  && pt <= 1007) {
            l1007++;
        }
        if (pt > 1007) {
            upper++;
        }
    }
    int getLeadTimeKinds(){
        int i = 0;
        if (l0 > 0) {
            i++;
        }
        if (l23 > 0) {
            i++;
        }
        if (l47 > 0) {
            i++;
        }
        if (l71 > 0) {
            i++;
        }
        if (l119 > 0) {
            i++;
        }
        if (l167 > 0) {
            i++;
        }
        if (l335 > 0) {
            i++;
        }
        if (l503 > 0) {
            i++;
        }
        if (l671 > 0) {
            i++;
        }
        if (l839 > 0) {
            i++;
        } if (l1007 > 0) {
            i++;
        } if (upper > 0) {
            i++;
        }
        return i;
    }
    int getTotalShipmentCount(){
        return l0 + l23 + l47 + l71 + l119
                +l167+l335+l503+l671+l839+l1007 + upper;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public int getLeadtime() {
        return leadtime;
    }

    public void setLeadtime(int leadtime) {
        this.leadtime = leadtime;
    }

    public int getL0() {
        return l0;
    }

    public void setL0(int l0) {
        this.l0 = l0;
    }

    public int getL23() {
        return l23;
    }

    public void setL23(int l23) {
        this.l23 = l23;
    }

    public int getL47() {
        return l47;
    }

    public void setL47(int l47) {
        this.l47 = l47;
    }

    public int getL71() {
        return l71;
    }

    public void setL71(int l71) {
        this.l71 = l71;
    }

    public int getL119() {
        return l119;
    }

    public void setL119(int l119) {
        this.l119 = l119;
    }

    public int getL167() {
        return l167;
    }

    public void setL167(int l167) {
        this.l167 = l167;
    }

    public int getL335() {
        return l335;
    }

    public void setL335(int l335) {
        this.l335 = l335;
    }

    public int getL503() {
        return l503;
    }

    public void setL503(int l503) {
        this.l503 = l503;
    }

    public int getL671() {
        return l671;
    }

    public void setL671(int l671) {
        this.l671 = l671;
    }

    public int getL839() {
        return l839;
    }

    public void setL839(int l839) {
        this.l839 = l839;
    }

    public int getL1007() {
        return l1007;
    }

    public void setL1007(int l1007) {
        this.l1007 = l1007;
    }

    public int getUpper() {
        return upper;
    }

    public void setUpper(int upper) {
        this.upper = upper;
    }

    @Override
    public String toString() {
        return  node+
                "," + leadtime +
                "," + l0 +
                "," + l23 +
                "," + l47 +
                "," + l71 +
                "," + l119 +
                "," + l167 +
                "," + l335 +
                "," + l503 +
                "," + l671 +
                "," + l839 +
                "," + l1007 +
                "," + upper +
                "," + getTotalShipmentCount() +
                "," + getLeadTimeKinds() ;
    }
}
