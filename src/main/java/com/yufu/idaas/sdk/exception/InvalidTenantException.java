package com.yufu.idaas.sdk.exception;

/**
 * User: yunzhang
 * Date: 2019/8/7,7:39 PM
 */
public class InvalidTenantException extends BaseVerifyException {
    public InvalidTenantException() {
        super("tenantId is invalid");
    }
}
