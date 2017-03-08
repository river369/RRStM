package com.yi.utils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectRequest;
import com.yi.EnvConstants;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jianguog on 17/3/8.
 */
public class OSSUtil {

    public static void main(String[] args) {

    }
    public static void upload(File file){
        OSSClient ossClient = new OSSClient(EnvConstants.OSS_UPLOAD_URL, EnvConstants.ACCESS_KEY_ID,
                EnvConstants.ACCESS_KEY_SECRET);
        System.out.println("Uploading a new object to OSS from a file\n");
        ossClient.putObject(new PutObjectRequest(EnvConstants.OSS_CLCENTURY_BUCKET, EnvConstants.ACCESS_KEY_ID, file));
    }
}
