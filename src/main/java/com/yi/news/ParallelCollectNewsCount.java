package com.yi.news;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jianguog on 17/2/20.
 */
public class ParallelCollectNewsCount {

    protected final int STOCKS_COUNT = 128;
    protected final int READER_COUNT = 8;

    protected AtomicBoolean _nInputsCompleted   = new AtomicBoolean(false);
    protected AtomicInteger _nCalculationCompleted = new AtomicInteger(0);

    protected BlockingQueue<String> _input_stockId = new ArrayBlockingQueue<String>(STOCKS_COUNT);

}
