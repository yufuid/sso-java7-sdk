package com.yufu.idaas.sdk.authn;

import com.yufu.idaas.sdk.exception.CannotRetrieveKeyException;

/**
 * Created by shuowang on 2018/5/2.
 */
public interface IKeySupplier<T> {
    T getKeyFromPath(String path) throws CannotRetrieveKeyException;
    T getKeyFromString(String keyString) throws CannotRetrieveKeyException;
}
