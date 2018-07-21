package com.yufu.idaas.sdk.bouncycastle.math.ec.endo;

import java.math.BigInteger;

/**
 * Created by mac on 2017/1/18.
 */
public interface GLVEndomorphism extends ECEndomorphism {
    BigInteger[] decomposeScalar(BigInteger var1);
}

