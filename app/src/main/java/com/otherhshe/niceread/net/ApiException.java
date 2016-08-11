package com.otherhshe.niceread.net;

/**
 * Author: Othershe
 * Time:  2016/8/11 17:51
 */
public class ApiException extends RuntimeException {
    public ApiException() {
        this("服务器异常...");
    }

    public ApiException(String message) {
        super(message);
    }
}
