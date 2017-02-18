package com.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by jianguog on 17/1/6.
 */
public class printDupNodes {

    public Map<String, List<Node>> vendorNodesMap = new HashMap<String, List<Node>>();
    public Map<String, String> nodeZipMap = new HashMap<String,String>();

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
            node.zip = nodeZipMap.get(node.id);
            nodes.add(node);
        }
        br.close();
        fr.close();
    }

    public void readNodeZipInputs(File file) throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while((line = br.readLine()) != null){
            String[] colums = line.split("\t");
            nodeZipMap.put(colums[0],colums[1]);
        }
        br.close();
        fr.close();
    }

    public void print(){
        for( String vendor : vendorNodesMap.keySet()){
            //System.out.println("vendor="+vendor);
            List<Node> ln = vendorNodesMap.get(vendor);
            //System.out.println(ln.size());
            Map<String, String> zips = new HashMap<String, String>();
            int dupTimes = 0;
            for (Node node :ln){
                if (node.zip == null || "".equalsIgnoreCase(node.zip)) continue;
                if(zips.containsKey(node.zip)){
                    dupTimes++;
                } else {
                    zips.put(node.zip, "1");
                }
            }
            if (dupTimes>0) {
                for (Node node :ln){
                    String nodeName = node.id.replace("\"","");
                    System.out.println( "'"+ nodeName + "',");
                }
            }
        }
    }

    public static void main(String[] args) {

        printDupNodes cdv = new printDupNodes();

        String nodeZip = "/Users/jianguog/node_zip1.tsv";
        File nodeZipFile = new File(nodeZip);
        try {
            cdv.readNodeZipInputs(nodeZipFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(cdv.nodeZipMap.size());

        String node = "/Users/jianguog/dup_vendors_nodes.tsv";
        File nodeFile = new File(node);
        try {
            cdv.readWarehouseInputs(nodeFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(cdv.vendorNodesMap.size());

        cdv.print();

    }


}
