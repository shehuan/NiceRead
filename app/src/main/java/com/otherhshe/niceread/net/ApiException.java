package com.otherhshe.niceread.net;

/**
 * Author: Othershe
 * Time:  2016/8/11 17:51
 */
public class ApiException extends RuntimeException {
    private static final int CODE1 = 1000;
    private static final int CODE2 = 1001;
    private static final int CODE3 = 1002;

    public ApiException() {
        super("服务器繁忙");
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(int code) {
        super(getApiExceptionMessage(code));
    }

    private static String getApiExceptionMessage(int code) {
        String message;
        switch (code) {
            case CODE1:
                message = "该用户不存在";
                break;
            case CODE2:
                message = "服务器繁忙";
                break;
            case CODE3:
                message = "请先登录";
                break;
            default:
                message = "未知错误";
                break;

        }
        return message;
    }
}
