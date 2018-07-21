package com.yufu.idaas.sdk.exception;

/**
 * Created by shuowang on 2018/6/11.
 */
public class VerifyException extends YufuException {
    public VerifyException(String msg) {
        super(msg);
    }

    public VerifyException(String msg, Throwable e) {
        super(msg, e);
    }
}
