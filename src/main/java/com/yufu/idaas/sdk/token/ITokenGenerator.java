package com.yufu.idaas.sdk.token;

import com.yufu.idaas.sdk.exception.GenerateException;

import java.util.Map;

/**
 * Created by shuowang on 2018/5/2.
 */
public interface ITokenGenerator {
    String generate(Map<String, Object> payload) throws GenerateException;

    String generate(JWT jwt) throws GenerateException;
}
