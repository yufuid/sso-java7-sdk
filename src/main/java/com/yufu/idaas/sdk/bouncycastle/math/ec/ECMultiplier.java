package com.yufu.idaas.sdk.bouncycastle.math.ec;

import com.yufu.idaas.sdk.bouncycastle.math.ec.endo.ECPoint;

import java.math.BigInteger;

/**
 * Created by mac on 2017/1/18.
 */
public interface ECMultiplier {
    ECPoint multiply(ECPoint var1, BigInteger var2);
}
