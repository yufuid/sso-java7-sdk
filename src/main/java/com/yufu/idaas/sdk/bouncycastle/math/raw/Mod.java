package com.yufu.idaas.sdk.bouncycastle.math.raw;

/**
 * Created by mac on 2017/1/18.
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//





import java.util.Random;

public abstract class Mod {
    public Mod() {
    }

    public static int inverse32(int var0) {
        int var1 = var0 * (2 - var0 * var0);
        var1 *= 2 - var0 * var1;
        var1 *= 2 - var0 * var1;
        var1 *= 2 - var0 * var1;
        return var1;
    }

    public static void invert(int[] var0, int[] var1, int[] var2) {
        int var3 = var0.length;
        if(Nat.isZero(var3, var1)) {
            throw new IllegalArgumentException("\'x\' cannot be 0");
        } else if(Nat.isOne(var3, var1)) {
            System.arraycopy(var1, 0, var2, 0, var3);
        } else {
            int[] var4 = Nat.copy(var3, var1);
            int[] var5 = Nat.create(var3);
            var5[0] = 1;
            int var6 = 0;
            if((var4[0] & 1) == 0) {
                var6 = inversionStep(var0, var4, var3, var5, var6);
            }

            if(Nat.isOne(var3, var4)) {
                inversionResult(var0, var6, var5, var2);
            } else {
                int[] var7 = Nat.copy(var3, var0);
                int[] var8 = Nat.create(var3);
                int var9 = 0;
                int var10 = var3;

                while(true) {
                    while(var4[var10 - 1] != 0 || var7[var10 - 1] != 0) {
                        if(Nat.gte(var10, var4, var7)) {
                            Nat.subFrom(var10, var7, var4);
                            var6 += Nat.subFrom(var3, var8, var5) - var9;
                            var6 = inversionStep(var0, var4, var10, var5, var6);
                            if(Nat.isOne(var10, var4)) {
                                inversionResult(var0, var6, var5, var2);
                                return;
                            }
                        } else {
                            Nat.subFrom(var10, var4, var7);
                            var9 += Nat.subFrom(var3, var5, var8) - var6;
                            var9 = inversionStep(var0, var7, var10, var8, var9);
                            if(Nat.isOne(var10, var7)) {
                                inversionResult(var0, var9, var8, var2);
                                return;
                            }
                        }
                    }

                    --var10;
                }
            }
        }
    }

    public static int[] random(int[] var0) {
        int var1 = var0.length;
        Random var2 = new Random();
        int[] var3 = Nat.create(var1);
        int var4 = var0[var1 - 1];
        var4 |= var4 >>> 1;
        var4 |= var4 >>> 2;
        var4 |= var4 >>> 4;
        var4 |= var4 >>> 8;
        var4 |= var4 >>> 16;

        do {
            for(int var5 = 0; var5 != var1; ++var5) {
                var3[var5] = var2.nextInt();
            }

            var3[var1 - 1] &= var4;
        } while(Nat.gte(var1, var3, var0));

        return var3;
    }

    public static void add(int[] var0, int[] var1, int[] var2, int[] var3) {
        int var4 = var0.length;
        int var5 = Nat.add(var4, var1, var2, var3);
        if(var5 != 0) {
            Nat.subFrom(var4, var0, var3);
        }

    }

    public static void subtract(int[] var0, int[] var1, int[] var2, int[] var3) {
        int var4 = var0.length;
        int var5 = Nat.sub(var4, var1, var2, var3);
        if(var5 != 0) {
            Nat.addTo(var4, var0, var3);
        }

    }

    private static void inversionResult(int[] var0, int var1, int[] var2, int[] var3) {
        if(var1 < 0) {
            Nat.add(var0.length, var2, var0, var3);
        } else {
            System.arraycopy(var2, 0, var3, 0, var0.length);
        }

    }

    private static int inversionStep(int[] var0, int[] var1, int var2, int[] var3, int var4) {
        int var5 = var0.length;

        int var6;
        for(var6 = 0; var1[0] == 0; var6 += 32) {
            Nat.shiftDownWord(var2, var1, 0);
        }

        int var7 = getTrailingZeroes(var1[0]);
        if(var7 > 0) {
            Nat.shiftDownBits(var2, var1, var7, 0);
            var6 += var7;
        }

        for(var7 = 0; var7 < var6; ++var7) {
            if((var3[0] & 1) != 0) {
                if(var4 < 0) {
                    var4 += Nat.addTo(var5, var0, var3);
                } else {
                    var4 += Nat.subFrom(var5, var0, var3);
                }
            }

            Nat.shiftDownBit(var5, var3, var4);
        }

        return var4;
    }

    private static int getTrailingZeroes(int var0) {
        int var1;
        for(var1 = 0; (var0 & 1) == 0; ++var1) {
            var0 >>>= 1;
        }

        return var1;
    }
}
