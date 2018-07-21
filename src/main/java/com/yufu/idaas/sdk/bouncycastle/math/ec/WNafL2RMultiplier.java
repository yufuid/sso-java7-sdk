package com.yufu.idaas.sdk.bouncycastle.math.ec;

import com.yufu.idaas.sdk.bouncycastle.math.ec.endo.ECPoint;

import java.math.BigInteger;

public class WNafL2RMultiplier extends AbstractECMultiplier {
    public WNafL2RMultiplier() {
    }

    protected ECPoint multiplyPositive(ECPoint var1, BigInteger var2) {
        int var3 = Math.max(2, Math.min(16, this.getWindowSize(var2.bitLength())));
        WNafPreCompInfo var4 = WNafUtil.precompute(var1, var3, true);
        ECPoint[] var5 = var4.getPreComp();
        ECPoint[] var6 = var4.getPreCompNeg();
        int[] var7 = WNafUtil.generateCompactWindowNaf(var3, var2);
        ECPoint var8 = var1.getCurve().getInfinity();
        int var9 = var7.length;
        int var10;
        int var11;
        int var12;
        int var13;
        ECPoint[] var14;
        if(var9 > 1) {
            --var9;
            var10 = var7[var9];
            var11 = var10 >> 16;
            var12 = var10 & '\uffff';
            var13 = Math.abs(var11);
            var14 = var11 < 0?var6:var5;
            if(var13 << 2 < 1 << var3) {
                byte var15 = LongArray.bitLengths[var13];
                int var16 = var3 - var15;
                int var17 = var13 ^ 1 << var15 - 1;
                int var18 = (1 << var3 - 1) - 1;
                int var19 = (var17 << var16) + 1;
                var8 = var14[var18 >>> 1].add(var14[var19 >>> 1]);
                var12 -= var16;
            } else {
                var8 = var14[var13 >>> 1];
            }

            var8 = var8.timesPow2(var12);
        }

        while(var9 > 0) {
            --var9;
            var10 = var7[var9];
            var11 = var10 >> 16;
            var12 = var10 & '\uffff';
            var13 = Math.abs(var11);
            var14 = var11 < 0?var6:var5;
            ECPoint var20 = var14[var13 >>> 1];
            var8 = var8.twicePlus(var20);
            var8 = var8.timesPow2(var12);
        }

        return var8;
    }

    protected int getWindowSize(int var1) {
        return WNafUtil.getWindowSize(var1);
    }
}
