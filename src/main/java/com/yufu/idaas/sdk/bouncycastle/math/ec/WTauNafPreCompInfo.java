package com.yufu.idaas.sdk.bouncycastle.math.ec;

import com.yufu.idaas.sdk.bouncycastle.math.ec.endo.ECPoint;

public class WTauNafPreCompInfo implements PreCompInfo {
    protected ECPoint.AbstractF2m[] preComp = null;

    public WTauNafPreCompInfo() {
    }

    public ECPoint.AbstractF2m[] getPreComp() {
        return this.preComp;
    }

    public void setPreComp(ECPoint.AbstractF2m[] var1) {
        this.preComp = var1;
    }
}
