package com.yufu.idaas.sdk.bouncycastle.math.ec;



import com.yufu.idaas.sdk.bouncycastle.math.ec.endo.ECEndomorphism;
import com.yufu.idaas.sdk.bouncycastle.math.ec.endo.ECPoint;
import com.yufu.idaas.sdk.bouncycastle.math.ec.endo.GLVEndomorphism;
import com.yufu.idaas.sdk.bouncycastle.math.field.FiniteField;
import com.yufu.idaas.sdk.bouncycastle.math.field.PolynomialExtensionField;

import java.math.BigInteger;

public class ECAlgorithms {
    public ECAlgorithms() {
    }

    public static boolean isF2mCurve(ECCurve var0) {
        return isF2mField(var0.getField());
    }

    public static boolean isF2mField(FiniteField var0) {
        return var0.getDimension() > 1 && var0.getCharacteristic().equals(ECConstants.TWO) && var0 instanceof PolynomialExtensionField;
    }

    public static boolean isFpCurve(ECCurve var0) {
        return isFpField(var0.getField());
    }

    public static boolean isFpField(FiniteField var0) {
        return var0.getDimension() == 1;
    }

    public static ECPoint sumOfMultiplies(ECPoint[] var0, BigInteger[] var1) {
        if(var0 != null && var1 != null && var0.length == var1.length && var0.length >= 1) {
            int var2 = var0.length;
            switch(var2) {
                case 1:
                    return var0[0].multiply(var1[0]);
                case 2:
                    return sumOfTwoMultiplies(var0[0], var1[0], var0[1], var1[1]);
                default:
                    ECPoint var3 = var0[0];
                    ECCurve var4 = var3.getCurve();
                    ECPoint[] var5 = new ECPoint[var2];
                    var5[0] = var3;

                    for(int var6 = 1; var6 < var2; ++var6) {
                        var5[var6] = importPoint(var4, var0[var6]);
                    }

                    ECEndomorphism var7 = var4.getEndomorphism();
                    return var7 instanceof GLVEndomorphism ?validatePoint(implSumOfMultipliesGLV(var5, var1, (GLVEndomorphism)var7)):validatePoint(implSumOfMultiplies(var5, var1));
            }
        } else {
            throw new IllegalArgumentException("point and scalar arrays should be non-null, and of equal, non-zero, length");
        }
    }

    public static ECPoint sumOfTwoMultiplies(ECPoint var0, BigInteger var1, ECPoint var2, BigInteger var3) {
        ECCurve var4 = var0.getCurve();
        var2 = importPoint(var4, var2);
        if(var4 instanceof ECCurve.AbstractF2m) {
            ECCurve.AbstractF2m var5 = (ECCurve.AbstractF2m)var4;
            if(var5.isKoblitz()) {
                return validatePoint(var0.multiply(var1).add(var2.multiply(var3)));
            }
        }

        ECEndomorphism var6 = var4.getEndomorphism();
        return var6 instanceof GLVEndomorphism?validatePoint(implSumOfMultipliesGLV(new ECPoint[]{var0, var2}, new BigInteger[]{var1, var3}, (GLVEndomorphism)var6)):validatePoint(implShamirsTrickWNaf(var0, var1, var2, var3));
    }

    public static ECPoint shamirsTrick(ECPoint var0, BigInteger var1, ECPoint var2, BigInteger var3) {
        ECCurve var4 = var0.getCurve();
        var2 = importPoint(var4, var2);
        return validatePoint(implShamirsTrickJsf(var0, var1, var2, var3));
    }

    public static ECPoint importPoint(ECCurve var0, ECPoint var1) {
        ECCurve var2 = var1.getCurve();
        if(!var0.equals(var2)) {
            throw new IllegalArgumentException("Point must be on the same curve");
        } else {
            return var0.importPoint(var1);
        }
    }

    public static void montgomeryTrick(ECFieldElement[] var0, int var1, int var2) {
        montgomeryTrick(var0, var1, var2, (ECFieldElement)null);
    }

    public static void montgomeryTrick(ECFieldElement[] var0, int var1, int var2, ECFieldElement var3) {
        ECFieldElement[] var4 = new ECFieldElement[var2];
        var4[0] = var0[var1];
        int var5 = 0;

        while(true) {
            ++var5;
            if(var5 >= var2) {
                --var5;
                if(var3 != null) {
                    var4[var5] = var4[var5].multiply(var3);
                }

                ECFieldElement var6;
                ECFieldElement var8;
                for(var6 = var4[var5].invert(); var5 > 0; var6 = var6.multiply(var8)) {
                    int var7 = var1 + var5--;
                    var8 = var0[var7];
                    var0[var7] = var4[var5].multiply(var6);
                }

                var0[var1] = var6;
                return;
            }

            var4[var5] = var4[var5 - 1].multiply(var0[var1 + var5]);
        }
    }

    public static ECPoint referenceMultiply(ECPoint var0, BigInteger var1) {
        BigInteger var2 = var1.abs();
        ECPoint var3 = var0.getCurve().getInfinity();
        int var4 = var2.bitLength();
        if(var4 > 0) {
            if(var2.testBit(0)) {
                var3 = var0;
            }

            for(int var5 = 1; var5 < var4; ++var5) {
                var0 = var0.twice();
                if(var2.testBit(var5)) {
                    var3 = var3.add(var0);
                }
            }
        }

        return var1.signum() < 0?var3.negate():var3;
    }

    public static ECPoint validatePoint(ECPoint var0) {
        if(!var0.isValid()) {
            throw new IllegalArgumentException("Invalid point");
        } else {
            return var0;
        }
    }

    static ECPoint implShamirsTrickJsf(ECPoint var0, BigInteger var1, ECPoint var2, BigInteger var3) {
        ECCurve var4 = var0.getCurve();
        ECPoint var5 = var4.getInfinity();
        ECPoint var6 = var0.add(var2);
        ECPoint var7 = var0.subtract(var2);
        ECPoint[] var8 = new ECPoint[]{var2, var7, var0, var6};
        var4.normalizeAll(var8);
        ECPoint[] var9 = new ECPoint[]{var8[3].negate(), var8[2].negate(), var8[1].negate(), var8[0].negate(), var5, var8[0], var8[1], var8[2], var8[3]};
        byte[] var10 = WNafUtil.generateJSF(var1, var3);
        ECPoint var11 = var5;
        int var12 = var10.length;

        while(true) {
            --var12;
            if(var12 < 0) {
                return var11;
            }

            byte var13 = var10[var12];
            int var14 = var13 << 24 >> 28;
            int var15 = var13 << 28 >> 28;
            int var16 = 4 + var14 * 3 + var15;
            var11 = var11.twicePlus(var9[var16]);
        }
    }

    static ECPoint implShamirsTrickWNaf(ECPoint var0, BigInteger var1, ECPoint var2, BigInteger var3) {
        boolean var4 = var1.signum() < 0;
        boolean var5 = var3.signum() < 0;
        var1 = var1.abs();
        var3 = var3.abs();
        int var6 = Math.max(2, Math.min(16, WNafUtil.getWindowSize(var1.bitLength())));
        int var7 = Math.max(2, Math.min(16, WNafUtil.getWindowSize(var3.bitLength())));
        WNafPreCompInfo var8 = WNafUtil.precompute(var0, var6, true);
        WNafPreCompInfo var9 = WNafUtil.precompute(var2, var7, true);
        ECPoint[] var10 = var4?var8.getPreCompNeg():var8.getPreComp();
        ECPoint[] var11 = var5?var9.getPreCompNeg():var9.getPreComp();
        ECPoint[] var12 = var4?var8.getPreComp():var8.getPreCompNeg();
        ECPoint[] var13 = var5?var9.getPreComp():var9.getPreCompNeg();
        byte[] var14 = WNafUtil.generateWindowNaf(var6, var1);
        byte[] var15 = WNafUtil.generateWindowNaf(var7, var3);
        return implShamirsTrickWNaf(var10, var12, var14, var11, var13, var15);
    }

    static ECPoint implShamirsTrickWNaf(ECPoint var0, BigInteger var1, ECPointMap var2, BigInteger var3) {
        boolean var4 = var1.signum() < 0;
        boolean var5 = var3.signum() < 0;
        var1 = var1.abs();
        var3 = var3.abs();
        int var6 = Math.max(2, Math.min(16, WNafUtil.getWindowSize(Math.max(var1.bitLength(), var3.bitLength()))));
        ECPoint var7 = WNafUtil.mapPointWithPrecomp(var0, var6, true, var2);
        WNafPreCompInfo var8 = WNafUtil.getWNafPreCompInfo(var0);
        WNafPreCompInfo var9 = WNafUtil.getWNafPreCompInfo(var7);
        ECPoint[] var10 = var4?var8.getPreCompNeg():var8.getPreComp();
        ECPoint[] var11 = var5?var9.getPreCompNeg():var9.getPreComp();
        ECPoint[] var12 = var4?var8.getPreComp():var8.getPreCompNeg();
        ECPoint[] var13 = var5?var9.getPreComp():var9.getPreCompNeg();
        byte[] var14 = WNafUtil.generateWindowNaf(var6, var1);
        byte[] var15 = WNafUtil.generateWindowNaf(var6, var3);
        return implShamirsTrickWNaf(var10, var12, var14, var11, var13, var15);
    }

    private static ECPoint implShamirsTrickWNaf(ECPoint[] var0, ECPoint[] var1, byte[] var2, ECPoint[] var3, ECPoint[] var4, byte[] var5) {
        int var6 = Math.max(var2.length, var5.length);
        ECCurve var7 = var0[0].getCurve();
        ECPoint var8 = var7.getInfinity();
        ECPoint var9 = var8;
        int var10 = 0;

        for(int var11 = var6 - 1; var11 >= 0; --var11) {
            byte var12 = var11 < var2.length?var2[var11]:0;
            byte var13 = var11 < var5.length?var5[var11]:0;
            if((var12 | var13) == 0) {
                ++var10;
            } else {
                ECPoint var14 = var8;
                int var15;
                ECPoint[] var16;
                if(var12 != 0) {
                    var15 = Math.abs(var12);
                    var16 = var12 < 0?var1:var0;
                    var14 = var8.add(var16[var15 >>> 1]);
                }

                if(var13 != 0) {
                    var15 = Math.abs(var13);
                    var16 = var13 < 0?var4:var3;
                    var14 = var14.add(var16[var15 >>> 1]);
                }

                if(var10 > 0) {
                    var9 = var9.timesPow2(var10);
                    var10 = 0;
                }

                var9 = var9.twicePlus(var14);
            }
        }

        if(var10 > 0) {
            var9 = var9.timesPow2(var10);
        }

        return var9;
    }

    static ECPoint implSumOfMultiplies(ECPoint[] var0, BigInteger[] var1) {
        int var2 = var0.length;
        boolean[] var3 = new boolean[var2];
        WNafPreCompInfo[] var4 = new WNafPreCompInfo[var2];
        byte[][] var5 = new byte[var2][];

        for(int var6 = 0; var6 < var2; ++var6) {
            BigInteger var7 = var1[var6];
            var3[var6] = var7.signum() < 0;
            var7 = var7.abs();
            int var8 = Math.max(2, Math.min(16, WNafUtil.getWindowSize(var7.bitLength())));
            var4[var6] = WNafUtil.precompute(var0[var6], var8, true);
            var5[var6] = WNafUtil.generateWindowNaf(var8, var7);
        }

        return implSumOfMultiplies(var3, var4, var5);
    }

    static ECPoint implSumOfMultipliesGLV(ECPoint[] var0, BigInteger[] var1, GLVEndomorphism var2) {
        BigInteger var3 = var0[0].getCurve().getOrder();
        int var4 = var0.length;
        BigInteger[] var5 = new BigInteger[var4 << 1];
        int var6 = 0;

        for(int var7 = 0; var6 < var4; ++var6) {
            BigInteger[] var8 = var2.decomposeScalar(var1[var6].mod(var3));
            var5[var7++] = var8[0];
            var5[var7++] = var8[1];
        }

        ECPointMap var12 = var2.getPointMap();
        if(var2.hasEfficientPointMap()) {
            return implSumOfMultiplies(var0, var12, var5);
        } else {
            ECPoint[] var13 = new ECPoint[var4 << 1];
            int var14 = 0;

            for(int var9 = 0; var14 < var4; ++var14) {
                ECPoint var10 = var0[var14];
                ECPoint var11 = var12.map(var10);
                var13[var9++] = var10;
                var13[var9++] = var11;
            }

            return implSumOfMultiplies(var13, var5);
        }
    }

    static ECPoint implSumOfMultiplies(ECPoint[] var0, ECPointMap var1, BigInteger[] var2) {
        int var3 = var0.length;
        int var4 = var3 << 1;
        boolean[] var5 = new boolean[var4];
        WNafPreCompInfo[] var6 = new WNafPreCompInfo[var4];
        byte[][] var7 = new byte[var4][];

        for(int var8 = 0; var8 < var3; ++var8) {
            int var9 = var8 << 1;
            int var10 = var9 + 1;
            BigInteger var11 = var2[var9];
            var5[var9] = var11.signum() < 0;
            var11 = var11.abs();
            BigInteger var12 = var2[var10];
            var5[var10] = var12.signum() < 0;
            var12 = var12.abs();
            int var13 = Math.max(2, Math.min(16, WNafUtil.getWindowSize(Math.max(var11.bitLength(), var12.bitLength()))));
            ECPoint var14 = var0[var8];
            ECPoint var15 = WNafUtil.mapPointWithPrecomp(var14, var13, true, var1);
            var6[var9] = WNafUtil.getWNafPreCompInfo(var14);
            var6[var10] = WNafUtil.getWNafPreCompInfo(var15);
            var7[var9] = WNafUtil.generateWindowNaf(var13, var11);
            var7[var10] = WNafUtil.generateWindowNaf(var13, var12);
        }

        return implSumOfMultiplies(var5, var6, var7);
    }

    private static ECPoint implSumOfMultiplies(boolean[] var0, WNafPreCompInfo[] var1, byte[][] var2) {
        int var3 = 0;
        int var4 = var2.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            var3 = Math.max(var3, var2[var5].length);
        }

        ECCurve var17 = var1[0].getPreComp()[0].getCurve();
        ECPoint var6 = var17.getInfinity();
        ECPoint var7 = var6;
        int var8 = 0;

        for(int var9 = var3 - 1; var9 >= 0; --var9) {
            ECPoint var10 = var6;

            for(int var11 = 0; var11 < var4; ++var11) {
                byte[] var12 = var2[var11];
                byte var13 = var9 < var12.length?var12[var9]:0;
                if(var13 != 0) {
                    int var14 = Math.abs(var13);
                    WNafPreCompInfo var15 = var1[var11];
                    ECPoint[] var16 = var13 < 0 == var0[var11]?var15.getPreComp():var15.getPreCompNeg();
                    var10 = var10.add(var16[var14 >>> 1]);
                }
            }

            if(var10 == var6) {
                ++var8;
            } else {
                if(var8 > 0) {
                    var7 = var7.timesPow2(var8);
                    var8 = 0;
                }

                var7 = var7.twicePlus(var10);
            }
        }

        if(var8 > 0) {
            var7 = var7.timesPow2(var8);
        }

        return var7;
    }
}
