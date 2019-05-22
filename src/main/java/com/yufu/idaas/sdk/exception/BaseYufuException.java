package com.yufu.idaas.sdk.exception;

/**
 * Created by shuowang on 2018/5/2.
 */
abstract class BaseYufuException extends Exception {
    BaseYufuException(String msg) {
        super(msg);
    }

    BaseYufuException(String msg, Throwable e) {
        super(msg, e);
    }
}
