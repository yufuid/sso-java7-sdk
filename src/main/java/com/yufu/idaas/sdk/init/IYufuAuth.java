package com.yufu.idaas.sdk.init;

import com.yufu.idaas.sdk.exception.GenerateException;
import com.yufu.idaas.sdk.token.JWT;

import java.net.URL;
import java.util.Map;

/**
 * Created by shuowang on 2018/5/2.
 */
public interface IYufuAuth {
    String generateToken(Map<String, Object> Claims) throws GenerateException;
    URL generateIDPRedirectUrl(Map<String, Object> Claims) throws GenerateException;
    JWT verify(String id_token) throws Exception;
}
