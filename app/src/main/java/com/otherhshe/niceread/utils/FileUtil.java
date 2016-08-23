package com.otherhshe.niceread.utils;

import java.math.BigDecimal;

/**
 * Author: Othershe
 * Time: 2016/8/23 11:31
 */
public class FileUtil {
    public static long getFileLength(java.io.File dir) {
        long length = 0;
        for (java.io.File file :
                dir.listFiles()) {
            if (file.isFile()) {
                length += file.length();
            } else
                length += getFileLength(file);
        }
        return length;
    }

    public static String getFileSize(java.io.File dir) {
        BigDecimal bd;
        if (getFileLength(dir) > 1024 * 1024) {
            bd = new BigDecimal(getFileLength(dir) / 1000000);
            return bd.setScale(2, BigDecimal.ROUND_HALF_EVEN) + " M";

        } else {
            bd = new BigDecimal(getFileLength(dir) / 1000);
            return bd.setScale(0, BigDecimal.ROUND_HALF_EVEN) + " k";

        }
    }
}
