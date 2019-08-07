package com.yufu.idaas.sdk.exception;

/**
 * User: yunzhang
 * Date: 2019/8/7,7:46 PM
 */
public class InvalidIssuerException extends BaseVerifyException {

    public InvalidIssuerException() {
        super("Issuer is invalid");
    }
}
