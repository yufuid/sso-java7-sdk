package com.yufu.idaas.sdk.exception;

/**
 * Created by shuowang on 2018/6/11.
 */
public final class TokenExpiredException extends BaseVerifyException {
    public TokenExpiredException() {
        super("Token is expired");
    }
}
