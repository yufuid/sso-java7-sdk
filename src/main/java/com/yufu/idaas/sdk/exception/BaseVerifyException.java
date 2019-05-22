package com.yufu.idaas.sdk.exception;

/**
 * Created by shuowang on 2018/6/11.
 */
public class BaseVerifyException extends BaseYufuException {
    public BaseVerifyException(String msg) {
        super(msg);
    }

    public BaseVerifyException(String msg, Throwable e) {
        super(msg, e);
    }
}
