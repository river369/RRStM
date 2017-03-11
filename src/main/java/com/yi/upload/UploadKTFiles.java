package com.yi.upload;

import com.yi.EnvConstants;
import com.yi.YiConstants;
import com.yi.utils.DateUtils;
import com.yi.utils.OSSUtil;

import java.io.File;

/**
 * Created by jianguog on 17/3/7.
 */
public class UploadKTFiles {
    String[] filesToUpload = {YiConstants.localPreSelectedBlockFileName, YiConstants.localBlockInfoFileName};
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
        System.out.println("Upload Check at " + DateUtils.getCurrentTimeToSecondString());
        for (int i = 0; i < filesToUpload.length; i++) {
            String ktPath = YiConstants.getKTPath();
            File file = new File(ktPath + filesToUpload[i]);
            if (file.exists()) {
                long fileLength = file.length();
                if (fileLength == filesLength[i]) {
                    System.out.println("skip this upload file " + file.getAbsolutePath() + " since no change found.");
                } else {
                    filesLength[i] = fileLength;
                    upload(file);
                }
            } else {
                System.out.println("File " + file.getAbsolutePath() + " doesn't exist.");
            }
        }
    }

    public void upload(File file) {
        System.out.println("Uploading file " + file.getAbsolutePath() + " to " +  EnvConstants.OSS_KT_PREFIX );
        OSSUtil.upload(file, EnvConstants.OSS_KT_PREFIX + file.getName());
        OSSUtil.upload(file, EnvConstants.OSS_KT_HISTORY_PREFIX + file.getName() + "_" + DateUtils.getCurrentTimeToSecondString());
//        try {
//            String content = FileUtils.readFileToString(file, "UTF-16");
//            //System.out.println("[" + content + "]");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}
