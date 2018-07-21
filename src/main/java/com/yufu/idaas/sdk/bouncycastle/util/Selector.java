package com.yufu.idaas.sdk.bouncycastle.util;

/**
 * Created by mac on 2017/1/18.
 */
public interface Selector<T> extends Cloneable {
    boolean match(T var1);

    Object clone();
}

