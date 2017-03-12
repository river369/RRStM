package com.yi;

import com.yi.utils.DateUtils;
import org.apache.ibatis.io.Resources;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by jianguog on 17/2/18.
 */
public class EnvConstants {
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    static {
        Properties prop = new Properties();
        try {
            InputStream inputStream = Resources.getResourceAsStream("env.properties");
            prop.load(inputStream);
            ACCESS_KEY_ID = prop.getProperty("aliyun.ACCESS_KEY_ID");
            ACCESS_KEY_SECRET = prop.getProperty("aliyun.ACCESS_KEY_SECRET");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static String OSS_BASE_URL = "http://oss.clcentury.com/";
    public static String OSS_UPLOAD_URL = "http://oss-cn-beijing.aliyuncs.com/";
    public static String OSS_AISTOCKYI_BUCKET = "aistockyi";
    public static String OSS_KT_PREFIX = "kt/";
    public static String OSS_KT_HISTORY_PREFIX = "kthistory/";

}