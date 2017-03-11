package com.yi.utils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.yi.EnvConstants;
import com.yi.YiConstants;

import java.io.File;

/**
 * Created by jianguog on 17/3/8.
 */
public class OSSUtil {

    public static void main(String[] args) {
        System.out.println(OSSUtil.exist(EnvConstants.OSS_KT_PREFIX + YiConstants.localPreSelectedBlockFileName));
    }
    public static OSSClient getClient(){
        OSSClient ossClient = new OSSClient(EnvConstants.OSS_UPLOAD_URL, EnvConstants.ACCESS_KEY_ID, EnvConstants.ACCESS_KEY_SECRET);
        return ossClient;
    }
    public static void upload(File file, String ossPath){
        OSSClient ossClient = getClient();
        ossClient.putObject(new PutObjectRequest(EnvConstants.OSS_AISTOCKYI_BUCKET, ossPath, file));
        ossClient.shutdown();
    }
    public static boolean exist(String ossPath){
        OSSClient ossClient = getClient();
        boolean existed = ossClient.doesObjectExist(EnvConstants.OSS_AISTOCKYI_BUCKET, ossPath);
        ossClient.shutdown();
        return existed;
    }
    public static void getObject(String ossPath, File file){
        OSSClient ossClient = getClient();
        ossClient.getObject(new GetObjectRequest(EnvConstants.OSS_AISTOCKYI_BUCKET, ossPath), file);
        ossClient.shutdown();
    }



}
