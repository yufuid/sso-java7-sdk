package com.yufu.idaas.sdk.bouncycastle.math.ec;

/**
 * Created by mac on 2017/1/18.
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import com.yufu.idaas.sdk.bouncycastle.math.ec.endo.ECPoint;

import java.math.BigInteger;

;

public abstract class WNafUtil {
    public static final String PRECOMP_NAME = "bc_wnaf";
    private static final int[] DEFAULT_WINDOW_SIZE_CUTOFFS = new int[]{13, 41, 121, 337, 897, 2305};
    private static final byte[] EMPTY_BYTES = new byte[0];
    private static final int[] EMPTY_INTS = new int[0];
    private static final ECPoint[] EMPTY_POINTS = new ECPoint[0];

    public WNafUtil() {
    }

    public static int[] generateCompactNaf(BigInteger var0) {
        if(var0.bitLength() >>> 16 != 0) {
            throw new IllegalArgumentException("\'k\' must have bitlength < 2^16");
        } else if(var0.signum() == 0) {
            return EMPTY_INTS;
        } else {
            BigInteger var1 = var0.shiftLeft(1).add(var0);
            int var2 = var1.bitLength();
            int[] var3 = new int[var2 >> 1];
            BigInteger var4 = var1.xor(var0);
            int var5 = var2 - 1;
            int var6 = 0;
            int var7 = 0;

            for(int var8 = 1; var8 < var5; ++var8) {
                if(!var4.testBit(var8)) {
                    ++var7;
                } else {
                    int var9 = var0.testBit(var8)?-1:1;
                    var3[var6++] = var9 << 16 | var7;
                    var7 = 1;
                    ++var8;
                }
            }

            var3[var6++] = 65536 | var7;
            if(var3.length > var6) {
                var3 = trim(var3, var6);
            }

            return var3;
        }
    }

    public static int[] generateCompactWindowNaf(int var0, BigInteger var1) {
        if(var0 == 2) {
            return generateCompactNaf(var1);
        } else if(var0 >= 2 && var0 <= 16) {
            if(var1.bitLength() >>> 16 != 0) {
                throw new IllegalArgumentException("\'k\' must have bitlength < 2^16");
            } else if(var1.signum() == 0) {
                return EMPTY_INTS;
            } else {
                int[] var2 = new int[var1.bitLength() / var0 + 1];
                int var3 = 1 << var0;
                int var4 = var3 - 1;
                int var5 = var3 >>> 1;
                boolean var6 = false;
                int var7 = 0;
                int var8 = 0;

                while(var8 <= var1.bitLength()) {
                    if(var1.testBit(var8) == var6) {
                        ++var8;
                    } else {
                        var1 = var1.shiftRight(var8);
                        int var9 = var1.intValue() & var4;
                        if(var6) {
                            ++var9;
                        }

                        var6 = (var9 & var5) != 0;
                        if(var6) {
                            var9 -= var3;
                        }

                        int var10 = var7 > 0?var8 - 1:var8;
                        var2[var7++] = var9 << 16 | var10;
                        var8 = var0;
                    }
                }

                if(var2.length > var7) {
                    var2 = trim(var2, var7);
                }

                return var2;
            }
        } else {
            throw new IllegalArgumentException("\'width\' must be in the range [2, 16]");
        }
    }

    public static byte[] generateJSF(BigInteger var0, BigInteger var1) {
        int var2 = Math.max(var0.bitLength(), var1.bitLength()) + 1;
        byte[] var3 = new byte[var2];
        BigInteger var4 = var0;
        BigInteger var5 = var1;
        int var6 = 0;
        int var7 = 0;
        int var8 = 0;

        int var12 = 0;
        int var13 = 0;
        for(int var9 = 0; (var7 | var8) != 0 || var4.bitLength() > var9 || var5.bitLength() > var9; var3[var6++] = (byte)(var12 << 4 | var13 & 15)) {
            int var10 = (var4.intValue() >>> var9) + var7 & 7;
            int var11 = (var5.intValue() >>> var9) + var8 & 7;
            var12 = var10 & 1;
            if(var12 != 0) {
                var12 -= var10 & 2;
                if(var10 + var12 == 4 && (var11 & 3) == 2) {
                    var12 = -var12;
                }
            }

            var13 = var11 & 1;
            if(var13 != 0) {
                var13 -= var11 & 2;
                if(var11 + var13 == 4 && (var10 & 3) == 2) {
                    var13 = -var13;
                }
            }

            if(var7 << 1 == 1 + var12) {
                var7 ^= 1;
            }

            if(var8 << 1 == 1 + var13) {
                var8 ^= 1;
            }

            ++var9;
            if(var9 == 30) {
                var9 = 0;
                var4 = var4.shiftRight(30);
                var5 = var5.shiftRight(30);
            }
        }

        if(var3.length > var6) {
            var3 = trim(var3, var6);
        }

        return var3;
    }

    public static byte[] generateNaf(BigInteger var0) {
        if(var0.signum() == 0) {
            return EMPTY_BYTES;
        } else {
            BigInteger var1 = var0.shiftLeft(1).add(var0);
            int var2 = var1.bitLength() - 1;
            byte[] var3 = new byte[var2];
            BigInteger var4 = var1.xor(var0);

            for(int var5 = 1; var5 < var2; ++var5) {
                if(var4.testBit(var5)) {
                    var3[var5 - 1] = (byte)(var0.testBit(var5)?-1:1);
                    ++var5;
                }
            }

            var3[var2 - 1] = 1;
            return var3;
        }
    }

    public static byte[] generateWindowNaf(int var0, BigInteger var1) {
        if(var0 == 2) {
            return generateNaf(var1);
        } else if(var0 >= 2 && var0 <= 8) {
            if(var1.signum() == 0) {
                return EMPTY_BYTES;
            } else {
                byte[] var2 = new byte[var1.bitLength() + 1];
                int var3 = 1 << var0;
                int var4 = var3 - 1;
                int var5 = var3 >>> 1;
                boolean var6 = false;
                int var7 = 0;
                int var8 = 0;

                while(var8 <= var1.bitLength()) {
                    if(var1.testBit(var8) == var6) {
                        ++var8;
                    } else {
                        var1 = var1.shiftRight(var8);
                        int var9 = var1.intValue() & var4;
                        if(var6) {
                            ++var9;
                        }

                        var6 = (var9 & var5) != 0;
                        if(var6) {
                            var9 -= var3;
                        }

                        var7 += var7 > 0?var8 - 1:var8;
                        var2[var7++] = (byte)var9;
                        var8 = var0;
                    }
                }

                if(var2.length > var7) {
                    var2 = trim(var2, var7);
                }

                return var2;
            }
        } else {
            throw new IllegalArgumentException("\'width\' must be in the range [2, 8]");
        }
    }

    public static int getNafWeight(BigInteger var0) {
        if(var0.signum() == 0) {
            return 0;
        } else {
            BigInteger var1 = var0.shiftLeft(1).add(var0);
            BigInteger var2 = var1.xor(var0);
            return var2.bitCount();
        }
    }

    public static WNafPreCompInfo getWNafPreCompInfo(ECPoint var0) {
        return getWNafPreCompInfo(var0.getCurve().getPreCompInfo(var0, "bc_wnaf"));
    }

    public static WNafPreCompInfo getWNafPreCompInfo(PreCompInfo var0) {
        return var0 != null && var0 instanceof WNafPreCompInfo?(WNafPreCompInfo)var0:new WNafPreCompInfo();
    }

    public static int getWindowSize(int var0) {
        return getWindowSize(var0, DEFAULT_WINDOW_SIZE_CUTOFFS);
    }

    public static int getWindowSize(int var0, int[] var1) {
        int var2;
        for(var2 = 0; var2 < var1.length && var0 >= var1[var2]; ++var2) {
            ;
        }

        return var2 + 2;
    }

    public static ECPoint mapPointWithPrecomp(ECPoint var0, int var1, boolean var2, ECPointMap var3) {
        ECCurve var4 = var0.getCurve();
        WNafPreCompInfo var5 = precompute(var0, var1, var2);
        ECPoint var6 = var3.map(var0);
        WNafPreCompInfo var7 = getWNafPreCompInfo(var4.getPreCompInfo(var6, "bc_wnaf"));
        ECPoint var8 = var5.getTwice();
        if(var8 != null) {
            ECPoint var9 = var3.map(var8);
            var7.setTwice(var9);
        }

        ECPoint[] var14 = var5.getPreComp();
        ECPoint[] var10 = new ECPoint[var14.length];

        for(int var11 = 0; var11 < var14.length; ++var11) {
            var10[var11] = var3.map(var14[var11]);
        }

        var7.setPreComp(var10);
        if(var2) {
            ECPoint[] var13 = new ECPoint[var10.length];

            for(int var12 = 0; var12 < var13.length; ++var12) {
                var13[var12] = var10[var12].negate();
            }

            var7.setPreCompNeg(var13);
        }

        var4.setPreCompInfo(var6, "bc_wnaf", var7);
        return var6;
    }

    public static WNafPreCompInfo precompute(ECPoint var0, int var1, boolean var2) {
        ECCurve var3 = var0.getCurve();
        WNafPreCompInfo var4 = getWNafPreCompInfo(var3.getPreCompInfo(var0, "bc_wnaf"));
        int var5 = 0;
        int var6 = 1 << Math.max(0, var1 - 2);
        ECPoint[] var7 = var4.getPreComp();
        if(var7 == null) {
            var7 = EMPTY_POINTS;
        } else {
            var5 = var7.length;
        }

        if(var5 < var6) {
            var7 = resizeTable(var7, var6);
            if(var6 == 1) {
                var7[0] = var0.normalize();
            } else {
                int var8 = var5;
                if(var5 == 0) {
                    var7[0] = var0;
                    var8 = 1;
                }

                ECFieldElement var9 = null;
                if(var6 == 2) {
                    var7[1] = var0.threeTimes();
                } else {
                    ECPoint var10 = var4.getTwice();
                    ECPoint var11 = var7[var8 - 1];
                    if(var10 == null) {
                        var10 = var7[0].twice();
                        var4.setTwice(var10);
                        if(ECAlgorithms.isFpCurve(var3) && var3.getFieldSize() >= 64) {
                            switch(var3.getCoordinateSystem()) {
                                case 2:
                                case 3:
                                case 4:
                                    var9 = var10.getZCoord(0);
                                    var10 = var3.createPoint(var10.getXCoord().toBigInteger(), var10.getYCoord().toBigInteger());
                                    ECFieldElement var12 = var9.square();
                                    ECFieldElement var13 = var12.multiply(var9);
                                    var11 = var11.scaleX(var12).scaleY(var13);
                                    if(var5 == 0) {
                                        var7[0] = var11;
                                    }
                            }
                        }
                    }

                    while(var8 < var6) {
                        var7[var8++] = var11 = var11.add(var10);
                    }
                }

                var3.normalizeAll(var7, var5, var6 - var5, var9);
            }
        }

        var4.setPreComp(var7);
        if(var2) {
            ECPoint[] var14 = var4.getPreCompNeg();
            int var15;
            if(var14 == null) {
                var15 = 0;
                var14 = new ECPoint[var6];
            } else {
                var15 = var14.length;
                if(var15 < var6) {
                    var14 = resizeTable(var14, var6);
                }
            }

            while(var15 < var6) {
                var14[var15] = var7[var15].negate();
                ++var15;
            }

            var4.setPreCompNeg(var14);
        }

        var3.setPreCompInfo(var0, "bc_wnaf", var4);
        return var4;
    }

    private static byte[] trim(byte[] var0, int var1) {
        byte[] var2 = new byte[var1];
        System.arraycopy(var0, 0, var2, 0, var2.length);
        return var2;
    }

    private static int[] trim(int[] var0, int var1) {
        int[] var2 = new int[var1];
        System.arraycopy(var0, 0, var2, 0, var2.length);
        return var2;
    }

    private static ECPoint[] resizeTable(ECPoint[] var0, int var1) {
        ECPoint[] var2 = new ECPoint[var1];
        System.arraycopy(var0, 0, var2, 0, var0.length);
        return var2;
    }
}
