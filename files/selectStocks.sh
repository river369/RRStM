#!/bin/bash
/home/software/jre1.8.0_121/bin/java -cp "/home/stock/lib/RRStM.jar:/home/stock/lib/*" com.yi.select.SelectFromKTFilesJob 0
/home/software/jre1.8.0_121/bin/java -cp "/home/stock/lib/RRStM.jar:/home/stock/lib/*" com.yi.select.SelectFromRealTimeJob 0

#/home/software/jre1.8.0_121/bin/java -cp /home/stock/lib/RRStM.jar:/home/stock/lib/mybatis-3.2.2.jar:/home/stock/lib/mysql-connector-java-bin.jar:/home/stock/lib/aliyun-sdk-oss-2.5.0.jar:/home/stock/lib/commons-codec-1.9.jar:/home/stock/lib/commons-io-2.5.jar:/home/stock/lib/commons-logging-1.2.jar:/home/stock/lib/hamcrest-core-1.1.jar:/home/stock/lib/httpclient-4.4.1.jar:/home/stock/lib/httpcore-4.4.1.jar:/home/stock/lib/jdom-1.1.jar com.yi.select.SelectFromKTFilesJob
