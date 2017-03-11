package com.yi.select;

import com.yi.YiConstants;
import com.yi.blocks.BlockData;
import com.yi.blocks.BlockInfoReader;
import com.yi.exception.YiException;

import java.util.*;

/**
 * Created by jianguog on 17/3/7.
 */
public class SelectModel {

    /**
     *
     * @param allStocksMap key is stock code, value is stock name
     * @return
     * @throws YiException
     */
    public List<StockOutput> select(Map<String, String> allStocksMap ) throws YiException {
        // 0 Build common data. read common stock-block mapping from local static file
        BlockInfoReader commonBlockInfoReader = new BlockInfoReader(YiConstants.getSelectorPath() + YiConstants.localBlockInfoFileName);
        BlockData commonBlockData = commonBlockInfoReader.getBlockData();
        TreeMap<String, HashSet<String>> commonStocksToBlocksMap = commonBlockData.getDistinctStocksToBlocksMap(allStocksMap);
        TreeMap<String, HashSet<String>> commonBlocksToStocksMap = commonBlockData.getDistinctBlocksToStocksMap(allStocksMap);

        // 1. select the best blocks base on preselected stocks
        SelectBlockModel selectBlockModel = new SelectBlockModel(allStocksMap, commonStocksToBlocksMap, commonBlocksToStocksMap);
        List<Map.Entry<String, BlockValues>> topBlockList = selectBlockModel.select();

        // 2. Select the best stocks in the best blocks
        SelectStockModel selectStockModel = new SelectStockModel();
        List<Map.Entry<String, StockValues>> selectedStockList = selectStockModel.select(topBlockList);

        // 3 orgnize the output data
        List<StockOutput> stockOutputsList = new ArrayList<StockOutput>();
        for (int i = 0 ; i < selectedStockList.size(); i++) {
            String stockId = selectedStockList.get(i).getKey();
            StockOutput stockOutput = new StockOutput(stockId, allStocksMap.get(stockId.substring(2)),
                    commonStocksToBlocksMap.get(stockId), selectedStockList.get(i).getValue());
            stockOutputsList.add(stockOutput);
        }
        return stockOutputsList;
    }
}
