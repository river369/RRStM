package com.yi.upload;

import com.yi.YiConstants;
import com.yi.utils.OSSUtil;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by jianguog on 17/3/7.
 */
public class UploadKTFiles {
    String[] filesToUpload = {YiConstants.localBlockInfoFileString, YiConstants.localPreSelectedBlockFileString};
    long[] filesLength = new long[2];

    public static void main(String[] args) {
        UploadKTFiles uploadKTFiles = new UploadKTFiles();
        while (true) {
            uploadKTFiles.run();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    public void run() {
        for (int i = 0; i < filesToUpload.length; i++) {
            String path = getPath();
            File file = new File(path + filesToUpload[i]);
            long fileLength = file.length();
            if (fileLength == filesLength[i]){
                System.out.println("skip this upload...");
            } else {
                System.out.println("upload " + file.getAbsolutePath());
                filesLength[i] = fileLength;
                upload(file);
            }
        }
    }
    public String getPath() {
        String path = "D:\\KT\\User\\BlockInfo\\";
        String os = System.getProperty("os.name");
        if(os.toLowerCase().startsWith("mac")){
            path = "/Users/jianguog/other/yistock/";
        }
        return path;
    }
    public void upload(File file) {
        OSSUtil.upload(file);
        try {
            String content = FileUtils.readFileToString(file, "UTF-16");
            //System.out.println("[" + content + "]");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
