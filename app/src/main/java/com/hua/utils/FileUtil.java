package com.hua.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * <文件操作工具 ps:看了之间的文件工具 没有单独的获取本地路径的方法 特此重写一个>
 * @date 创建时间：2014年11月11日 下午03:00:04
 */
public class FileUtil {
    private static String databasepath = "/data/data/%s/database"; // %s is packageName

    public static String getTimeMillis() {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String thisTime = format.format(new Timestamp(System
                .currentTimeMillis()));

        return thisTime;

    }

    public static boolean writeFile(InputStream input, File output) {
        if (input == null || output == null) {
            return false;
        }

        FileOutputStream fos = null;
        boolean succ = false;

        try {
            fos = new FileOutputStream(output);
            byte[] buffer = new byte[1024 * 10];
            int length = -1;
            while ((length = input.read(buffer)) != -1) {
                fos.write(buffer, 0, length);
            }
            succ = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return succ;
    }



}
