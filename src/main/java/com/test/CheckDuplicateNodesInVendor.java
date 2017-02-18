package com.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by jianguog on 17/1/6.
 */
public class CheckDuplicateNodesInVendor {

    //public Map<String, String> nodeZipMap = new HashMap<String,String>();
    public Map<String, NodeAdress> nodeAddressMap = new HashMap<String,NodeAdress>();
    public Map<String, String> nodeInvMap = new HashMap<String,String>();
    public Map<String, String> nodeShipMap = new HashMap<String,String>();

    public Map<String, String> nodeCreationDateMap = new HashMap<String, String>();
    public Set<String> veSet = new HashSet<String>();
    public Map<String, List<Node>> vendorNodesMap = new HashMap<String, List<Node>>();

    public void check(){
        for( String vendor : vendorNodesMap.keySet()){
            //System.out.println("vendor="+vendor);
            List<Node> ln = vendorNodesMap.get(vendor);
            //System.out.println(ln.size());
            Map<String, String> zips = new HashMap<String, String>();
            int dupTimes = 0;
            for (Node node :ln){
                if (node.nodeAdress == null || node.nodeAdress.zip == null || "".equalsIgnoreCase(node.nodeAdress.zip)) continue;
                if(zips.containsKey(node.nodeAdress.zip)){
                    dupTimes++;
                } else {
                    zips.put(node.nodeAdress.zip, "1");
                }
            }
            if (dupTimes>0) {
                for (Node node :ln){
                    String line = vendor + "," + (dupTimes+1) + "," + node.id + "," + node.creationDate
                            + "," + node.nodeAdress.toString() + "," + node.name+ "," + node.isVE ;
                    if(node.totalInv==null){
                        line = line+ ",,";
                    } else {
                        line = line +"," + node.totalInv;
                    }
                    if(node.totalShip==null){
                        line = line+ ",,";
                    } else {
                        line = line +"," + node.totalShip;
                    }

                    System.out.println(line);
                }

            }
        }
    }

    public void readNodeInv(File file) throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while((line = br.readLine()) != null){
            String[] colums = line.split("\t");
            nodeInvMap.put(colums[0],colums[1]+","+colums[2]);
        }
        br.close();
        fr.close();
    }

    public void readNodeShip(File file) throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while((line = br.readLine()) != null){
            String[] colums = line.split("\t");
            nodeShipMap.put(colums[0],colums[1]+","+colums[2]);
        }
        br.close();
        fr.close();
    }

    public void readNodeAdress(File file) throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while((line = br.readLine()) != null){
            //System.out.println(line);
            String[] colums = line.split("\t");
            NodeAdress nodeAdress = new NodeAdress();
            nodeAdress.zip = colums[1];
            nodeAdress.line1 = colums[2];
            nodeAdress.line2 = colums[3];
            nodeAdress.line3 = colums[4];
            nodeAdress.city = colums[5];
            nodeAdress.state = colums[6];
            nodeAdress.country = colums[7];
            nodeAddressMap.put(colums[0],nodeAdress);
        }
        br.close();
        fr.close();
    }

    public void readNodeCreationDate(File file) throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while((line = br.readLine()) != null){
            String[] colums = line.split("\t");
            nodeCreationDateMap.put(colums[0],colums[1]);
        }
        br.close();
        fr.close();
    }

    public void readWarehouseInputs(File file) throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while((line = br.readLine()) != null){
            String[] colums = line.split("\t");
            String vendor = colums[3];
            List<Node> nodes = vendorNodesMap.get(vendor);
            if (nodes == null) {
                nodes = new ArrayList<Node>();
                vendorNodesMap.put(vendor,nodes);
            }
            Node node = new Node();
            node.id = colums[2];
            node.name = colums[1];
            node.nodeAdress = nodeAddressMap.get(node.id);
            node.creationDate = nodeCreationDateMap.get(node.id);
            node.isVE = veSet.contains(node.id) ;
            node.totalInv = nodeInvMap.get(node.id.replace("\"",""));
            node.totalShip = nodeShipMap.get(node.id.replace("\"",""));
            nodes.add(node);
        }
        br.close();
        fr.close();
    }

    public void readVENodes(File file) throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while((line = br.readLine()) != null){
            String[] colums = line.split("\t");
            veSet.add(colums[0]);
        }
        br.close();
        fr.close();
    }

    public static void main(String[] args) {

        CheckDuplicateNodesInVendor cdv = new CheckDuplicateNodesInVendor();

        try {
            cdv.readNodeShip(new File("/Users/jianguog/dup_node_ship_sum"));

            File invFile = new File("/Users/jianguog/dup_node_inv_sum_db");
            cdv.readNodeInv(invFile);
            //System.out.println(cdv.nodeInvMap.size());

            File veNodesFile = new File("/Users/jianguog/venodes.tsv");
            cdv.readVENodes(veNodesFile);

            File nodeAddressFile = new File("/Users/jianguog/node_address.tsv");
            cdv.readNodeAdress(nodeAddressFile);

            //File nodeZipFile = new File("/Users/jianguog/node_zip1.tsv");
            //cdv.readNodeZipInputs(nodeZipFile);
            //System.out.println(cdv.nodeZipMap.size());

            File nodeCreationFile = new File("/Users/jianguog/node_creationdate.tsv");
            cdv.readNodeCreationDate(nodeCreationFile);

            File nodeFile = new File("/Users/jianguog/dup_vendors_nodes.tsv");
            cdv.readWarehouseInputs(nodeFile);
            //System.out.println(cdv.vendorNodesMap.size());

        } catch (IOException e) {
            e.printStackTrace();
        }

        cdv.check();
    }


}
