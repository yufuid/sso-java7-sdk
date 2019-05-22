package com.yufu.idaas.sdk.exception;

/**
 * Created by shuowang on 2018/6/11.
 */
public class TokenTooEarlyException extends BaseVerifyException {
    public TokenTooEarlyException(){
        super("Token is used too early");
    }
}
