package com.yufu.idaas.sdk.bouncycastle.math.ec;


import com.yufu.idaas.sdk.bouncycastle.math.ec.endo.ECPoint;

import java.math.BigInteger;

public abstract class AbstractECMultiplier implements ECMultiplier {
    public AbstractECMultiplier() {
    }

    public ECPoint multiply(ECPoint var1, BigInteger var2) {
        int var3 = var2.signum();
        if(var3 != 0 && !var1.isInfinity()) {
            ECPoint var4 = this.multiplyPositive(var1, var2.abs());
            ECPoint var5 = var3 > 0?var4:var4.negate();
            return ECAlgorithms.validatePoint(var5);
        } else {
            return var1.getCurve().getInfinity();
        }
    }

    protected abstract ECPoint multiplyPositive(ECPoint var1, BigInteger var2);
}