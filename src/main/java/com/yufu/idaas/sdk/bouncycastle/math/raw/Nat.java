package com.yufu.idaas.sdk.bouncycastle.math.raw;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.yufu.idaas.sdk.bouncycastle.util.Pack;

import java.math.BigInteger;

public abstract class Nat {
    private static final long M = 4294967295L;

    public Nat() {
    }

    public static int add(int var0, int[] var1, int[] var2, int[] var3) {
        long var4 = 0L;

        for(int var6 = 0; var6 < var0; ++var6) {
            var4 += ((long)var1[var6] & 4294967295L) + ((long)var2[var6] & 4294967295L);
            var3[var6] = (int)var4;
            var4 >>>= 32;
        }

        return (int)var4;
    }

    public static int add33At(int var0, int var1, int[] var2, int var3) {
        long var4 = ((long)var2[var3 + 0] & 4294967295L) + ((long)var1 & 4294967295L);
        var2[var3 + 0] = (int)var4;
        var4 >>>= 32;
        var4 += ((long)var2[var3 + 1] & 4294967295L) + 1L;
        var2[var3 + 1] = (int)var4;
        var4 >>>= 32;
        return var4 == 0L?0:incAt(var0, var2, var3 + 2);
    }

    public static int add33At(int var0, int var1, int[] var2, int var3, int var4) {
        long var5 = ((long)var2[var3 + var4] & 4294967295L) + ((long)var1 & 4294967295L);
        var2[var3 + var4] = (int)var5;
        var5 >>>= 32;
        var5 += ((long)var2[var3 + var4 + 1] & 4294967295L) + 1L;
        var2[var3 + var4 + 1] = (int)var5;
        var5 >>>= 32;
        return var5 == 0L?0:incAt(var0, var2, var3, var4 + 2);
    }

    public static int add33To(int var0, int var1, int[] var2) {
        long var3 = ((long)var2[0] & 4294967295L) + ((long)var1 & 4294967295L);
        var2[0] = (int)var3;
        var3 >>>= 32;
        var3 += ((long)var2[1] & 4294967295L) + 1L;
        var2[1] = (int)var3;
        var3 >>>= 32;
        return var3 == 0L?0:incAt(var0, var2, 2);
    }

    public static int add33To(int var0, int var1, int[] var2, int var3) {
        long var4 = ((long)var2[var3 + 0] & 4294967295L) + ((long)var1 & 4294967295L);
        var2[var3 + 0] = (int)var4;
        var4 >>>= 32;
        var4 += ((long)var2[var3 + 1] & 4294967295L) + 1L;
        var2[var3 + 1] = (int)var4;
        var4 >>>= 32;
        return var4 == 0L?0:incAt(var0, var2, var3, 2);
    }

    public static int addBothTo(int var0, int[] var1, int[] var2, int[] var3) {
        long var4 = 0L;

        for(int var6 = 0; var6 < var0; ++var6) {
            var4 += ((long)var1[var6] & 4294967295L) + ((long)var2[var6] & 4294967295L) + ((long)var3[var6] & 4294967295L);
            var3[var6] = (int)var4;
            var4 >>>= 32;
        }

        return (int)var4;
    }

    public static int addBothTo(int var0, int[] var1, int var2, int[] var3, int var4, int[] var5, int var6) {
        long var7 = 0L;

        for(int var9 = 0; var9 < var0; ++var9) {
            var7 += ((long)var1[var2 + var9] & 4294967295L) + ((long)var3[var4 + var9] & 4294967295L) + ((long)var5[var6 + var9] & 4294967295L);
            var5[var6 + var9] = (int)var7;
            var7 >>>= 32;
        }

        return (int)var7;
    }

    public static int addDWordAt(int var0, long var1, int[] var3, int var4) {
        long var5 = ((long)var3[var4 + 0] & 4294967295L) + (var1 & 4294967295L);
        var3[var4 + 0] = (int)var5;
        var5 >>>= 32;
        var5 += ((long)var3[var4 + 1] & 4294967295L) + (var1 >>> 32);
        var3[var4 + 1] = (int)var5;
        var5 >>>= 32;
        return var5 == 0L?0:incAt(var0, var3, var4 + 2);
    }

    public static int addDWordAt(int var0, long var1, int[] var3, int var4, int var5) {
        long var6 = ((long)var3[var4 + var5] & 4294967295L) + (var1 & 4294967295L);
        var3[var4 + var5] = (int)var6;
        var6 >>>= 32;
        var6 += ((long)var3[var4 + var5 + 1] & 4294967295L) + (var1 >>> 32);
        var3[var4 + var5 + 1] = (int)var6;
        var6 >>>= 32;
        return var6 == 0L?0:incAt(var0, var3, var4, var5 + 2);
    }

    public static int addDWordTo(int var0, long var1, int[] var3) {
        long var4 = ((long)var3[0] & 4294967295L) + (var1 & 4294967295L);
        var3[0] = (int)var4;
        var4 >>>= 32;
        var4 += ((long)var3[1] & 4294967295L) + (var1 >>> 32);
        var3[1] = (int)var4;
        var4 >>>= 32;
        return var4 == 0L?0:incAt(var0, var3, 2);
    }

    public static int addDWordTo(int var0, long var1, int[] var3, int var4) {
        long var5 = ((long)var3[var4 + 0] & 4294967295L) + (var1 & 4294967295L);
        var3[var4 + 0] = (int)var5;
        var5 >>>= 32;
        var5 += ((long)var3[var4 + 1] & 4294967295L) + (var1 >>> 32);
        var3[var4 + 1] = (int)var5;
        var5 >>>= 32;
        return var5 == 0L?0:incAt(var0, var3, var4, 2);
    }

    public static int addTo(int var0, int[] var1, int[] var2) {
        long var3 = 0L;

        for(int var5 = 0; var5 < var0; ++var5) {
            var3 += ((long)var1[var5] & 4294967295L) + ((long)var2[var5] & 4294967295L);
            var2[var5] = (int)var3;
            var3 >>>= 32;
        }

        return (int)var3;
    }

    public static int addTo(int var0, int[] var1, int var2, int[] var3, int var4) {
        long var5 = 0L;

        for(int var7 = 0; var7 < var0; ++var7) {
            var5 += ((long)var1[var2 + var7] & 4294967295L) + ((long)var3[var4 + var7] & 4294967295L);
            var3[var4 + var7] = (int)var5;
            var5 >>>= 32;
        }

        return (int)var5;
    }

    public static int addWordAt(int var0, int var1, int[] var2, int var3) {
        long var4 = ((long)var1 & 4294967295L) + ((long)var2[var3] & 4294967295L);
        var2[var3] = (int)var4;
        var4 >>>= 32;
        return var4 == 0L?0:incAt(var0, var2, var3 + 1);
    }

    public static int addWordAt(int var0, int var1, int[] var2, int var3, int var4) {
        long var5 = ((long)var1 & 4294967295L) + ((long)var2[var3 + var4] & 4294967295L);
        var2[var3 + var4] = (int)var5;
        var5 >>>= 32;
        return var5 == 0L?0:incAt(var0, var2, var3, var4 + 1);
    }

    public static int addWordTo(int var0, int var1, int[] var2) {
        long var3 = ((long)var1 & 4294967295L) + ((long)var2[0] & 4294967295L);
        var2[0] = (int)var3;
        var3 >>>= 32;
        return var3 == 0L?0:incAt(var0, var2, 1);
    }

    public static int addWordTo(int var0, int var1, int[] var2, int var3) {
        long var4 = ((long)var1 & 4294967295L) + ((long)var2[var3] & 4294967295L);
        var2[var3] = (int)var4;
        var4 >>>= 32;
        return var4 == 0L?0:incAt(var0, var2, var3, 1);
    }

    public static int[] copy(int var0, int[] var1) {
        int[] var2 = new int[var0];
        System.arraycopy(var1, 0, var2, 0, var0);
        return var2;
    }

    public static void copy(int var0, int[] var1, int[] var2) {
        System.arraycopy(var1, 0, var2, 0, var0);
    }

    public static int[] create(int var0) {
        return new int[var0];
    }

    public static long[] create64(int var0) {
        return new long[var0];
    }

    public static int dec(int var0, int[] var1) {
        for(int var2 = 0; var2 < var0; ++var2) {
            if(--var1[var2] != -1) {
                return 0;
            }
        }

        return -1;
    }

    public static int dec(int var0, int[] var1, int[] var2) {
        int var3 = 0;

        int var4;
        do {
            if(var3 >= var0) {
                return -1;
            }

            var4 = var1[var3] - 1;
            var2[var3] = var4;
            ++var3;
        } while(var4 == -1);

        while(var3 < var0) {
            var2[var3] = var1[var3];
            ++var3;
        }

        return 0;
    }

    public static int decAt(int var0, int[] var1, int var2) {
        for(int var3 = var2; var3 < var0; ++var3) {
            if(--var1[var3] != -1) {
                return 0;
            }
        }

        return -1;
    }

    public static int decAt(int var0, int[] var1, int var2, int var3) {
        for(int var4 = var3; var4 < var0; ++var4) {
            if(--var1[var2 + var4] != -1) {
                return 0;
            }
        }

        return -1;
    }

    public static boolean eq(int var0, int[] var1, int[] var2) {
        for(int var3 = var0 - 1; var3 >= 0; --var3) {
            if(var1[var3] != var2[var3]) {
                return false;
            }
        }

        return true;
    }

    public static int[] fromBigInteger(int var0, BigInteger var1) {
        if(var1.signum() >= 0 && var1.bitLength() <= var0) {
            int var2 = var0 + 31 >> 5;
            int[] var3 = create(var2);

            for(int var4 = 0; var1.signum() != 0; var1 = var1.shiftRight(32)) {
                var3[var4++] = var1.intValue();
            }

            return var3;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static int getBit(int[] var0, int var1) {
        if(var1 == 0) {
            return var0[0] & 1;
        } else {
            int var2 = var1 >> 5;
            if(var2 >= 0 && var2 < var0.length) {
                int var3 = var1 & 31;
                return var0[var2] >>> var3 & 1;
            } else {
                return 0;
            }
        }
    }

    public static boolean gte(int var0, int[] var1, int[] var2) {
        for(int var3 = var0 - 1; var3 >= 0; --var3) {
            int var4 = var1[var3] ^ -2147483648;
            int var5 = var2[var3] ^ -2147483648;
            if(var4 < var5) {
                return false;
            }

            if(var4 > var5) {
                return true;
            }
        }

        return true;
    }

    public static int inc(int var0, int[] var1) {
        for(int var2 = 0; var2 < var0; ++var2) {
            if(++var1[var2] != 0) {
                return 0;
            }
        }

        return 1;
    }

    public static int inc(int var0, int[] var1, int[] var2) {
        int var3 = 0;

        int var4;
        do {
            if(var3 >= var0) {
                return 1;
            }

            var4 = var1[var3] + 1;
            var2[var3] = var4;
            ++var3;
        } while(var4 == 0);

        while(var3 < var0) {
            var2[var3] = var1[var3];
            ++var3;
        }

        return 0;
    }

    public static int incAt(int var0, int[] var1, int var2) {
        for(int var3 = var2; var3 < var0; ++var3) {
            if(++var1[var3] != 0) {
                return 0;
            }
        }

        return 1;
    }

    public static int incAt(int var0, int[] var1, int var2, int var3) {
        for(int var4 = var3; var4 < var0; ++var4) {
            if(++var1[var2 + var4] != 0) {
                return 0;
            }
        }

        return 1;
    }

    public static boolean isOne(int var0, int[] var1) {
        if(var1[0] != 1) {
            return false;
        } else {
            for(int var2 = 1; var2 < var0; ++var2) {
                if(var1[var2] != 0) {
                    return false;
                }
            }

            return true;
        }
    }

    public static boolean isZero(int var0, int[] var1) {
        for(int var2 = 0; var2 < var0; ++var2) {
            if(var1[var2] != 0) {
                return false;
            }
        }

        return true;
    }

    public static void mul(int var0, int[] var1, int[] var2, int[] var3) {
        var3[var0] = mulWord(var0, var1[0], var2, var3);

        for(int var4 = 1; var4 < var0; ++var4) {
            var3[var4 + var0] = mulWordAddTo(var0, var1[var4], var2, 0, var3, var4);
        }

    }

    public static void mul(int var0, int[] var1, int var2, int[] var3, int var4, int[] var5, int var6) {
        var5[var6 + var0] = mulWord(var0, var1[var2], var3, var4, var5, var6);

        for(int var7 = 1; var7 < var0; ++var7) {
            var5[var6 + var7 + var0] = mulWordAddTo(var0, var1[var2 + var7], var3, var4, var5, var6 + var7);
        }

    }

    public static int mulAddTo(int var0, int[] var1, int[] var2, int[] var3) {
        long var4 = 0L;

        for(int var6 = 0; var6 < var0; ++var6) {
            long var7 = (long)mulWordAddTo(var0, var1[var6], var2, 0, var3, var6) & 4294967295L;
            var7 += var4 + ((long)var3[var6 + var0] & 4294967295L);
            var3[var6 + var0] = (int)var7;
            var4 = var7 >>> 32;
        }

        return (int)var4;
    }

    public static int mulAddTo(int var0, int[] var1, int var2, int[] var3, int var4, int[] var5, int var6) {
        long var7 = 0L;

        for(int var9 = 0; var9 < var0; ++var9) {
            long var10 = (long)mulWordAddTo(var0, var1[var2 + var9], var3, var4, var5, var6) & 4294967295L;
            var10 += var7 + ((long)var5[var6 + var0] & 4294967295L);
            var5[var6 + var0] = (int)var10;
            var7 = var10 >>> 32;
            ++var6;
        }

        return (int)var7;
    }

    public static int mul31BothAdd(int var0, int var1, int[] var2, int var3, int[] var4, int[] var5, int var6) {
        long var7 = 0L;
        long var9 = (long)var1 & 4294967295L;
        long var11 = (long)var3 & 4294967295L;
        int var13 = 0;

        do {
            var7 += var9 * ((long)var2[var13] & 4294967295L) + var11 * ((long)var4[var13] & 4294967295L) + ((long)var5[var6 + var13] & 4294967295L);
            var5[var6 + var13] = (int)var7;
            var7 >>>= 32;
            ++var13;
        } while(var13 < var0);

        return (int)var7;
    }

    public static int mulWord(int var0, int var1, int[] var2, int[] var3) {
        long var4 = 0L;
        long var6 = (long)var1 & 4294967295L;
        int var8 = 0;

        do {
            var4 += var6 * ((long)var2[var8] & 4294967295L);
            var3[var8] = (int)var4;
            var4 >>>= 32;
            ++var8;
        } while(var8 < var0);

        return (int)var4;
    }

    public static int mulWord(int var0, int var1, int[] var2, int var3, int[] var4, int var5) {
        long var6 = 0L;
        long var8 = (long)var1 & 4294967295L;
        int var10 = 0;

        do {
            var6 += var8 * ((long)var2[var3 + var10] & 4294967295L);
            var4[var5 + var10] = (int)var6;
            var6 >>>= 32;
            ++var10;
        } while(var10 < var0);

        return (int)var6;
    }

    public static int mulWordAddTo(int var0, int var1, int[] var2, int var3, int[] var4, int var5) {
        long var6 = 0L;
        long var8 = (long)var1 & 4294967295L;
        int var10 = 0;

        do {
            var6 += var8 * ((long)var2[var3 + var10] & 4294967295L) + ((long)var4[var5 + var10] & 4294967295L);
            var4[var5 + var10] = (int)var6;
            var6 >>>= 32;
            ++var10;
        } while(var10 < var0);

        return (int)var6;
    }

    public static int mulWordDwordAddAt(int var0, int var1, long var2, int[] var4, int var5) {
        long var6 = 0L;
        long var8 = (long)var1 & 4294967295L;
        var6 += var8 * (var2 & 4294967295L) + ((long)var4[var5 + 0] & 4294967295L);
        var4[var5 + 0] = (int)var6;
        var6 >>>= 32;
        var6 += var8 * (var2 >>> 32) + ((long)var4[var5 + 1] & 4294967295L);
        var4[var5 + 1] = (int)var6;
        var6 >>>= 32;
        var6 += (long)var4[var5 + 2] & 4294967295L;
        var4[var5 + 2] = (int)var6;
        var6 >>>= 32;
        return var6 == 0L?0:incAt(var0, var4, var5 + 3);
    }

    public static int shiftDownBit(int var0, int[] var1, int var2) {
        int var3 = var0;

        while(true) {
            --var3;
            if(var3 < 0) {
                return var2 << 31;
            }

            int var4 = var1[var3];
            var1[var3] = var4 >>> 1 | var2 << 31;
            var2 = var4;
        }
    }

    public static int shiftDownBit(int var0, int[] var1, int var2, int var3) {
        int var4 = var0;

        while(true) {
            --var4;
            if(var4 < 0) {
                return var3 << 31;
            }

            int var5 = var1[var2 + var4];
            var1[var2 + var4] = var5 >>> 1 | var3 << 31;
            var3 = var5;
        }
    }

    public static int shiftDownBit(int var0, int[] var1, int var2, int[] var3) {
        int var4 = var0;

        while(true) {
            --var4;
            if(var4 < 0) {
                return var2 << 31;
            }

            int var5 = var1[var4];
            var3[var4] = var5 >>> 1 | var2 << 31;
            var2 = var5;
        }
    }

    public static int shiftDownBit(int var0, int[] var1, int var2, int var3, int[] var4, int var5) {
        int var6 = var0;

        while(true) {
            --var6;
            if(var6 < 0) {
                return var3 << 31;
            }

            int var7 = var1[var2 + var6];
            var4[var5 + var6] = var7 >>> 1 | var3 << 31;
            var3 = var7;
        }
    }

    public static int shiftDownBits(int var0, int[] var1, int var2, int var3) {
        int var4 = var0;

        while(true) {
            --var4;
            if(var4 < 0) {
                return var3 << -var2;
            }

            int var5 = var1[var4];
            var1[var4] = var5 >>> var2 | var3 << -var2;
            var3 = var5;
        }
    }

    public static int shiftDownBits(int var0, int[] var1, int var2, int var3, int var4) {
        int var5 = var0;

        while(true) {
            --var5;
            if(var5 < 0) {
                return var4 << -var3;
            }

            int var6 = var1[var2 + var5];
            var1[var2 + var5] = var6 >>> var3 | var4 << -var3;
            var4 = var6;
        }
    }

    public static int shiftDownBits(int var0, int[] var1, int var2, int var3, int[] var4) {
        int var5 = var0;

        while(true) {
            --var5;
            if(var5 < 0) {
                return var3 << -var2;
            }

            int var6 = var1[var5];
            var4[var5] = var6 >>> var2 | var3 << -var2;
            var3 = var6;
        }
    }

    public static int shiftDownBits(int var0, int[] var1, int var2, int var3, int var4, int[] var5, int var6) {
        int var7 = var0;

        while(true) {
            --var7;
            if(var7 < 0) {
                return var4 << -var3;
            }

            int var8 = var1[var2 + var7];
            var5[var6 + var7] = var8 >>> var3 | var4 << -var3;
            var4 = var8;
        }
    }

    public static int shiftDownWord(int var0, int[] var1, int var2) {
        int var3 = var0;

        while(true) {
            --var3;
            if(var3 < 0) {
                return var2;
            }

            int var4 = var1[var3];
            var1[var3] = var2;
            var2 = var4;
        }
    }

    public static int shiftUpBit(int var0, int[] var1, int var2) {
        for(int var3 = 0; var3 < var0; ++var3) {
            int var4 = var1[var3];
            var1[var3] = var4 << 1 | var2 >>> 31;
            var2 = var4;
        }

        return var2 >>> 31;
    }

    public static int shiftUpBit(int var0, int[] var1, int var2, int var3) {
        for(int var4 = 0; var4 < var0; ++var4) {
            int var5 = var1[var2 + var4];
            var1[var2 + var4] = var5 << 1 | var3 >>> 31;
            var3 = var5;
        }

        return var3 >>> 31;
    }

    public static int shiftUpBit(int var0, int[] var1, int var2, int[] var3) {
        for(int var4 = 0; var4 < var0; ++var4) {
            int var5 = var1[var4];
            var3[var4] = var5 << 1 | var2 >>> 31;
            var2 = var5;
        }

        return var2 >>> 31;
    }

    public static int shiftUpBit(int var0, int[] var1, int var2, int var3, int[] var4, int var5) {
        for(int var6 = 0; var6 < var0; ++var6) {
            int var7 = var1[var2 + var6];
            var4[var5 + var6] = var7 << 1 | var3 >>> 31;
            var3 = var7;
        }

        return var3 >>> 31;
    }

    public static long shiftUpBit64(int var0, long[] var1, int var2, long var3, long[] var5, int var6) {
        for(int var7 = 0; var7 < var0; ++var7) {
            long var8 = var1[var2 + var7];
            var5[var6 + var7] = var8 << 1 | var3 >>> 63;
            var3 = var8;
        }

        return var3 >>> 63;
    }

    public static int shiftUpBits(int var0, int[] var1, int var2, int var3) {
        for(int var4 = 0; var4 < var0; ++var4) {
            int var5 = var1[var4];
            var1[var4] = var5 << var2 | var3 >>> -var2;
            var3 = var5;
        }

        return var3 >>> -var2;
    }

    public static int shiftUpBits(int var0, int[] var1, int var2, int var3, int var4) {
        for(int var5 = 0; var5 < var0; ++var5) {
            int var6 = var1[var2 + var5];
            var1[var2 + var5] = var6 << var3 | var4 >>> -var3;
            var4 = var6;
        }

        return var4 >>> -var3;
    }

    public static long shiftUpBits64(int var0, long[] var1, int var2, int var3, long var4) {
        for(int var6 = 0; var6 < var0; ++var6) {
            long var7 = var1[var2 + var6];
            var1[var2 + var6] = var7 << var3 | var4 >>> -var3;
            var4 = var7;
        }

        return var4 >>> -var3;
    }

    public static int shiftUpBits(int var0, int[] var1, int var2, int var3, int[] var4) {
        for(int var5 = 0; var5 < var0; ++var5) {
            int var6 = var1[var5];
            var4[var5] = var6 << var2 | var3 >>> -var2;
            var3 = var6;
        }

        return var3 >>> -var2;
    }

    public static int shiftUpBits(int var0, int[] var1, int var2, int var3, int var4, int[] var5, int var6) {
        for(int var7 = 0; var7 < var0; ++var7) {
            int var8 = var1[var2 + var7];
            var5[var6 + var7] = var8 << var3 | var4 >>> -var3;
            var4 = var8;
        }

        return var4 >>> -var3;
    }

    public static long shiftUpBits64(int var0, long[] var1, int var2, int var3, long var4, long[] var6, int var7) {
        for(int var8 = 0; var8 < var0; ++var8) {
            long var9 = var1[var2 + var8];
            var6[var7 + var8] = var9 << var3 | var4 >>> -var3;
            var4 = var9;
        }

        return var4 >>> -var3;
    }

    public static void square(int var0, int[] var1, int[] var2) {
        int var3 = var0 << 1;
        int var4 = 0;
        int var5 = var0;
        int var6 = var3;

        do {
            --var5;
            long var7 = (long)var1[var5] & 4294967295L;
            long var9 = var7 * var7;
            --var6;
            var2[var6] = var4 << 31 | (int)(var9 >>> 33);
            --var6;
            var2[var6] = (int)(var9 >>> 1);
            var4 = (int)var9;
        } while(var5 > 0);

        for(int var11 = 1; var11 < var0; ++var11) {
            var4 = squareWordAdd(var1, var11, var2);
            addWordAt(var3, var4, var2, var11 << 1);
        }

        shiftUpBit(var3, var2, var1[0] << 31);
    }

    public static void square(int var0, int[] var1, int var2, int[] var3, int var4) {
        int var5 = var0 << 1;
        int var6 = 0;
        int var7 = var0;
        int var8 = var5;

        do {
            --var7;
            long var9 = (long)var1[var2 + var7] & 4294967295L;
            long var11 = var9 * var9;
            --var8;
            var3[var4 + var8] = var6 << 31 | (int)(var11 >>> 33);
            --var8;
            var3[var4 + var8] = (int)(var11 >>> 1);
            var6 = (int)var11;
        } while(var7 > 0);

        for(int var13 = 1; var13 < var0; ++var13) {
            var6 = squareWordAdd(var1, var2, var13, var3, var4);
            addWordAt(var5, var6, var3, var4, var13 << 1);
        }

        shiftUpBit(var5, var3, var4, var1[var2] << 31);
    }

    public static int squareWordAdd(int[] var0, int var1, int[] var2) {
        long var3 = 0L;
        long var5 = (long)var0[var1] & 4294967295L;
        int var7 = 0;

        do {
            var3 += var5 * ((long)var0[var7] & 4294967295L) + ((long)var2[var1 + var7] & 4294967295L);
            var2[var1 + var7] = (int)var3;
            var3 >>>= 32;
            ++var7;
        } while(var7 < var1);

        return (int)var3;
    }

    public static int squareWordAdd(int[] var0, int var1, int var2, int[] var3, int var4) {
        long var5 = 0L;
        long var7 = (long)var0[var1 + var2] & 4294967295L;
        int var9 = 0;

        do {
            var5 += var7 * ((long)var0[var1 + var9] & 4294967295L) + ((long)var3[var2 + var4] & 4294967295L);
            var3[var2 + var4] = (int)var5;
            var5 >>>= 32;
            ++var4;
            ++var9;
        } while(var9 < var2);

        return (int)var5;
    }

    public static int sub(int var0, int[] var1, int[] var2, int[] var3) {
        long var4 = 0L;

        for(int var6 = 0; var6 < var0; ++var6) {
            var4 += ((long)var1[var6] & 4294967295L) - ((long)var2[var6] & 4294967295L);
            var3[var6] = (int)var4;
            var4 >>= 32;
        }

        return (int)var4;
    }

    public static int sub(int var0, int[] var1, int var2, int[] var3, int var4, int[] var5, int var6) {
        long var7 = 0L;

        for(int var9 = 0; var9 < var0; ++var9) {
            var7 += ((long)var1[var2 + var9] & 4294967295L) - ((long)var3[var4 + var9] & 4294967295L);
            var5[var6 + var9] = (int)var7;
            var7 >>= 32;
        }

        return (int)var7;
    }

    public static int sub33At(int var0, int var1, int[] var2, int var3) {
        long var4 = ((long)var2[var3 + 0] & 4294967295L) - ((long)var1 & 4294967295L);
        var2[var3 + 0] = (int)var4;
        var4 >>= 32;
        var4 += ((long)var2[var3 + 1] & 4294967295L) - 1L;
        var2[var3 + 1] = (int)var4;
        var4 >>= 32;
        return var4 == 0L?0:decAt(var0, var2, var3 + 2);
    }

    public static int sub33At(int var0, int var1, int[] var2, int var3, int var4) {
        long var5 = ((long)var2[var3 + var4] & 4294967295L) - ((long)var1 & 4294967295L);
        var2[var3 + var4] = (int)var5;
        var5 >>= 32;
        var5 += ((long)var2[var3 + var4 + 1] & 4294967295L) - 1L;
        var2[var3 + var4 + 1] = (int)var5;
        var5 >>= 32;
        return var5 == 0L?0:decAt(var0, var2, var3, var4 + 2);
    }

    public static int sub33From(int var0, int var1, int[] var2) {
        long var3 = ((long)var2[0] & 4294967295L) - ((long)var1 & 4294967295L);
        var2[0] = (int)var3;
        var3 >>= 32;
        var3 += ((long)var2[1] & 4294967295L) - 1L;
        var2[1] = (int)var3;
        var3 >>= 32;
        return var3 == 0L?0:decAt(var0, var2, 2);
    }

    public static int sub33From(int var0, int var1, int[] var2, int var3) {
        long var4 = ((long)var2[var3 + 0] & 4294967295L) - ((long)var1 & 4294967295L);
        var2[var3 + 0] = (int)var4;
        var4 >>= 32;
        var4 += ((long)var2[var3 + 1] & 4294967295L) - 1L;
        var2[var3 + 1] = (int)var4;
        var4 >>= 32;
        return var4 == 0L?0:decAt(var0, var2, var3, 2);
    }

    public static int subBothFrom(int var0, int[] var1, int[] var2, int[] var3) {
        long var4 = 0L;

        for(int var6 = 0; var6 < var0; ++var6) {
            var4 += ((long)var3[var6] & 4294967295L) - ((long)var1[var6] & 4294967295L) - ((long)var2[var6] & 4294967295L);
            var3[var6] = (int)var4;
            var4 >>= 32;
        }

        return (int)var4;
    }

    public static int subBothFrom(int var0, int[] var1, int var2, int[] var3, int var4, int[] var5, int var6) {
        long var7 = 0L;

        for(int var9 = 0; var9 < var0; ++var9) {
            var7 += ((long)var5[var6 + var9] & 4294967295L) - ((long)var1[var2 + var9] & 4294967295L) - ((long)var3[var4 + var9] & 4294967295L);
            var5[var6 + var9] = (int)var7;
            var7 >>= 32;
        }

        return (int)var7;
    }

    public static int subDWordAt(int var0, long var1, int[] var3, int var4) {
        long var5 = ((long)var3[var4 + 0] & 4294967295L) - (var1 & 4294967295L);
        var3[var4 + 0] = (int)var5;
        var5 >>= 32;
        var5 += ((long)var3[var4 + 1] & 4294967295L) - (var1 >>> 32);
        var3[var4 + 1] = (int)var5;
        var5 >>= 32;
        return var5 == 0L?0:decAt(var0, var3, var4 + 2);
    }

    public static int subDWordAt(int var0, long var1, int[] var3, int var4, int var5) {
        long var6 = ((long)var3[var4 + var5] & 4294967295L) - (var1 & 4294967295L);
        var3[var4 + var5] = (int)var6;
        var6 >>= 32;
        var6 += ((long)var3[var4 + var5 + 1] & 4294967295L) - (var1 >>> 32);
        var3[var4 + var5 + 1] = (int)var6;
        var6 >>= 32;
        return var6 == 0L?0:decAt(var0, var3, var4, var5 + 2);
    }

    public static int subDWordFrom(int var0, long var1, int[] var3) {
        long var4 = ((long)var3[0] & 4294967295L) - (var1 & 4294967295L);
        var3[0] = (int)var4;
        var4 >>= 32;
        var4 += ((long)var3[1] & 4294967295L) - (var1 >>> 32);
        var3[1] = (int)var4;
        var4 >>= 32;
        return var4 == 0L?0:decAt(var0, var3, 2);
    }

    public static int subDWordFrom(int var0, long var1, int[] var3, int var4) {
        long var5 = ((long)var3[var4 + 0] & 4294967295L) - (var1 & 4294967295L);
        var3[var4 + 0] = (int)var5;
        var5 >>= 32;
        var5 += ((long)var3[var4 + 1] & 4294967295L) - (var1 >>> 32);
        var3[var4 + 1] = (int)var5;
        var5 >>= 32;
        return var5 == 0L?0:decAt(var0, var3, var4, 2);
    }

    public static int subFrom(int var0, int[] var1, int[] var2) {
        long var3 = 0L;

        for(int var5 = 0; var5 < var0; ++var5) {
            var3 += ((long)var2[var5] & 4294967295L) - ((long)var1[var5] & 4294967295L);
            var2[var5] = (int)var3;
            var3 >>= 32;
        }

        return (int)var3;
    }

    public static int subFrom(int var0, int[] var1, int var2, int[] var3, int var4) {
        long var5 = 0L;

        for(int var7 = 0; var7 < var0; ++var7) {
            var5 += ((long)var3[var4 + var7] & 4294967295L) - ((long)var1[var2 + var7] & 4294967295L);
            var3[var4 + var7] = (int)var5;
            var5 >>= 32;
        }

        return (int)var5;
    }

    public static int subWordAt(int var0, int var1, int[] var2, int var3) {
        long var4 = ((long)var2[var3] & 4294967295L) - ((long)var1 & 4294967295L);
        var2[var3] = (int)var4;
        var4 >>= 32;
        return var4 == 0L?0:decAt(var0, var2, var3 + 1);
    }

    public static int subWordAt(int var0, int var1, int[] var2, int var3, int var4) {
        long var5 = ((long)var2[var3 + var4] & 4294967295L) - ((long)var1 & 4294967295L);
        var2[var3 + var4] = (int)var5;
        var5 >>= 32;
        return var5 == 0L?0:decAt(var0, var2, var3, var4 + 1);
    }

    public static int subWordFrom(int var0, int var1, int[] var2) {
        long var3 = ((long)var2[0] & 4294967295L) - ((long)var1 & 4294967295L);
        var2[0] = (int)var3;
        var3 >>= 32;
        return var3 == 0L?0:decAt(var0, var2, 1);
    }

    public static int subWordFrom(int var0, int var1, int[] var2, int var3) {
        long var4 = ((long)var2[var3 + 0] & 4294967295L) - ((long)var1 & 4294967295L);
        var2[var3 + 0] = (int)var4;
        var4 >>= 32;
        return var4 == 0L?0:decAt(var0, var2, var3, 1);
    }

    public static BigInteger toBigInteger(int var0, int[] var1) {
        byte[] var2 = new byte[var0 << 2];

        for(int var3 = 0; var3 < var0; ++var3) {
            int var4 = var1[var3];
            if(var4 != 0) {
                Pack.intToBigEndian(var4, var2, var0 - 1 - var3 << 2);
            }
        }

        return new BigInteger(1, var2);
    }

    public static void zero(int var0, int[] var1) {
        for(int var2 = 0; var2 < var0; ++var2) {
            var1[var2] = 0;
        }

    }
}
