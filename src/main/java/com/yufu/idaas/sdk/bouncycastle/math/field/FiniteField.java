package com.yufu.idaas.sdk.bouncycastle.math.field;

import java.math.BigInteger;

/**
 * Created by mac on 2017/1/18.
 */
public interface FiniteField {
    BigInteger getCharacteristic();

    int getDimension();
}