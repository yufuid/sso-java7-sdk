package com.yufu.idaas.sdk.exception;

/**
 * Created by shuowang on 2018/5/2.
 */
public class YufuException extends Exception {
    public YufuException(String msg) {
        super(msg);
    }

    public YufuException(String msg, Throwable e) {
        super(msg, e);
    }
}
