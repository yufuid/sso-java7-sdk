package com.yufu.idaas.sdk.bouncycastle.math.ec;

import java.math.BigInteger;

/**
 * Created by mac on 2017/1/18.
 */
class ZTauElement {
    public final BigInteger u;
    public final BigInteger v;

    public ZTauElement(BigInteger var1, BigInteger var2) {
        this.u = var1;
        this.v = var2;
    }
}
