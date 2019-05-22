package com.yufu.idaas.sdk.exception;

/**
 * Created by shuowang on 2018/6/11.
 */
public class InvalidSignatureException extends BaseVerifyException {
    public InvalidSignatureException() {
        super("Invalid Signature");
    }
}
