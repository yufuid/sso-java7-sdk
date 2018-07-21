package com.yufu.idaas.sdk.exception;

/**
 * Created by shuowang on 2018/5/2.
 */
public class CannotRetrieveKeyException extends VerifyException {

    public CannotRetrieveKeyException() {
        super("Can not retrieve key for token");
    }

    public CannotRetrieveKeyException(Throwable e) {
        super("Can not retrieve key for token", e);
    }
}
