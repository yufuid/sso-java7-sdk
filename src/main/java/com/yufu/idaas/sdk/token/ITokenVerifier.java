package com.yufu.idaas.sdk.token;

import com.yufu.idaas.sdk.exception.BaseVerifyException;

/**
 * Created by shuowang on 2018/6/11.
 */
public interface ITokenVerifier {
    JWT verify(String token) throws BaseVerifyException;
}
