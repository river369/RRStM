package com.yi.news;

import com.yi.YiConstants;
import com.yi.blocks.BC;
import com.yi.blocks.BK;
import com.yi.blocks.BlockData;
import com.yi.blocks.BlockInfoReader;
import com.yi.stocks.AggregateStocks;
import com.yi.stocks.DistinctStock;
import com.yi.utils.DateUtils;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by jianguog on 17/2/20.
 */
public class CollectNewsCount {
    public static void main(String[] args) {
        CollectNewsCount collectNewsCount = new CollectNewsCount();
        collectNewsCount.collectNewCount();

    }

    public void collectNewCount(){
        AggregateStocks aggregateStocks = new AggregateStocks();
        TreeMap<String, DistinctStock> distinctStockTreeMap = aggregateStocks.buildDistinctStocks();
        Iterator stockIterator = distinctStockTreeMap.keySet().iterator();
        createNewsFileIfNotExist();
        //BufferedWriter outFile = null;
        FileWriter outFile = null;
        try {
            //outFile = new BufferedWriter(new FileWriter(YiConstants.newsCountsFile));
            outFile = new FileWriter(YiConstants.newsCountsFile);
            while (stockIterator.hasNext()) {
                String stockId = (String) stockIterator.next();
                stockId = stockId.substring(2);
                //System.out.println(stockId);
                long start = System.currentTimeMillis();
                NewsReader newReader = new NewsReader();
                List<Integer> newsCountList = newReader.getNewsCountList(stockId);
                String counts = "";
                for (Integer count : newsCountList) {
                    counts = counts + count + ";";
                }
                long runtimeLength = System.currentTimeMillis() - start;
                outFile.write(stockId+";"+counts +runtimeLength + "\n");
                outFile.flush();
                System.out.println(stockId+";"+counts+runtimeLength);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outFile != null) {
                try {
                    outFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    void createNewsFileIfNotExist(){
        File newsCountsFile = new File(YiConstants.newsCountsFile);
        if (!newsCountsFile.exists()){
            try {
                newsCountsFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileUtils fileUtils = new FileUtils();


    }

}
