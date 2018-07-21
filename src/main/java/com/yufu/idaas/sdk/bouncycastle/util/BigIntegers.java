package com.yufu.idaas.sdk.bouncycastle.util;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by mac on 2017/1/18.
 */
public final class BigIntegers {
    private static final int MAX_ITERATIONS = 1000;
    private static final BigInteger ZERO = BigInteger.valueOf(0L);

    public BigIntegers() {
    }

    public static byte[] asUnsignedByteArray(BigInteger var0) {
        byte[] var1 = var0.toByteArray();
        if(var1[0] == 0) {
            byte[] var2 = new byte[var1.length - 1];
            System.arraycopy(var1, 1, var2, 0, var2.length);
            return var2;
        } else {
            return var1;
        }
    }

    public static byte[] asUnsignedByteArray(int var0, BigInteger var1) {
        byte[] var2 = var1.toByteArray();
        if(var2.length == var0) {
            return var2;
        } else {
            int var3 = var2[0] == 0?1:0;
            int var4 = var2.length - var3;
            if(var4 > var0) {
                throw new IllegalArgumentException("standard length exceeded for value");
            } else {
                byte[] var5 = new byte[var0];
                System.arraycopy(var2, var3, var5, var5.length - var4, var4);
                return var5;
            }
        }
    }

    public static BigInteger createRandomInRange(BigInteger var0, BigInteger var1, SecureRandom var2) {
        int var3 = var0.compareTo(var1);
        if(var3 >= 0) {
            if(var3 > 0) {
                throw new IllegalArgumentException("\'min\' may not be greater than \'max\'");
            } else {
                return var0;
            }
        } else if(var0.bitLength() > var1.bitLength() / 2) {
            return createRandomInRange(ZERO, var1.subtract(var0), var2).add(var0);
        } else {
            for(int var4 = 0; var4 < 1000; ++var4) {
                BigInteger var5 = new BigInteger(var1.bitLength(), var2);
                if(var5.compareTo(var0) >= 0 && var5.compareTo(var1) <= 0) {
                    return var5;
                }
            }

            return (new BigInteger(var1.subtract(var0).bitLength() - 1, var2)).add(var0);
        }
    }

    public static BigInteger fromUnsignedByteArray(byte[] var0) {
        return new BigInteger(1, var0);
    }

    public static BigInteger fromUnsignedByteArray(byte[] var0, int var1, int var2) {
        byte[] var3 = var0;
        if(var1 != 0 || var2 != var0.length) {
            var3 = new byte[var2];
            System.arraycopy(var0, var1, var3, 0, var2);
        }

        return new BigInteger(1, var3);
    }
}
