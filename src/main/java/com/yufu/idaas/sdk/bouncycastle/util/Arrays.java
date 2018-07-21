package com.yufu.idaas.sdk.bouncycastle.util;

/**
 * Created by mac on 2017/1/17.
 */
import java.math.BigInteger;
import java.util.NoSuchElementException;

public final class Arrays {
    private Arrays() {
    }

    public static boolean areEqual(boolean[] var0, boolean[] var1) {
        if(var0 == var1) {
            return true;
        } else if(var0 != null && var1 != null) {
            if(var0.length != var1.length) {
                return false;
            } else {
                for(int var2 = 0; var2 != var0.length; ++var2) {
                    if(var0[var2] != var1[var2]) {
                        return false;
                    }
                }

                return true;
            }
        } else {
            return false;
        }
    }

    public static boolean areEqual(char[] var0, char[] var1) {
        if(var0 == var1) {
            return true;
        } else if(var0 != null && var1 != null) {
            if(var0.length != var1.length) {
                return false;
            } else {
                for(int var2 = 0; var2 != var0.length; ++var2) {
                    if(var0[var2] != var1[var2]) {
                        return false;
                    }
                }

                return true;
            }
        } else {
            return false;
        }
    }

    public static boolean areEqual(byte[] var0, byte[] var1) {
        if(var0 == var1) {
            return true;
        } else if(var0 != null && var1 != null) {
            if(var0.length != var1.length) {
                return false;
            } else {
                for(int var2 = 0; var2 != var0.length; ++var2) {
                    if(var0[var2] != var1[var2]) {
                        return false;
                    }
                }

                return true;
            }
        } else {
            return false;
        }
    }

    public static boolean constantTimeAreEqual(byte[] var0, byte[] var1) {
        if(var0 == var1) {
            return true;
        } else if(var0 != null && var1 != null) {
            if(var0.length != var1.length) {
                return false;
            } else {
                int var2 = 0;

                for(int var3 = 0; var3 != var0.length; ++var3) {
                    var2 |= var0[var3] ^ var1[var3];
                }

                return var2 == 0;
            }
        } else {
            return false;
        }
    }

    public static boolean areEqual(int[] var0, int[] var1) {
        if(var0 == var1) {
            return true;
        } else if(var0 != null && var1 != null) {
            if(var0.length != var1.length) {
                return false;
            } else {
                for(int var2 = 0; var2 != var0.length; ++var2) {
                    if(var0[var2] != var1[var2]) {
                        return false;
                    }
                }

                return true;
            }
        } else {
            return false;
        }
    }

    public static boolean areEqual(long[] var0, long[] var1) {
        if(var0 == var1) {
            return true;
        } else if(var0 != null && var1 != null) {
            if(var0.length != var1.length) {
                return false;
            } else {
                for(int var2 = 0; var2 != var0.length; ++var2) {
                    if(var0[var2] != var1[var2]) {
                        return false;
                    }
                }

                return true;
            }
        } else {
            return false;
        }
    }

    public static boolean areEqual(Object[] var0, Object[] var1) {
        if(var0 == var1) {
            return true;
        } else if(var0 != null && var1 != null) {
            if(var0.length != var1.length) {
                return false;
            } else {
                for(int var2 = 0; var2 != var0.length; ++var2) {
                    Object var3 = var0[var2];
                    Object var4 = var1[var2];
                    if(var3 == null) {
                        if(var4 != null) {
                            return false;
                        }
                    } else if(!var3.equals(var4)) {
                        return false;
                    }
                }

                return true;
            }
        } else {
            return false;
        }
    }

    public static boolean contains(short[] var0, short var1) {
        for(int var2 = 0; var2 < var0.length; ++var2) {
            if(var0[var2] == var1) {
                return true;
            }
        }

        return false;
    }

    public static boolean contains(int[] var0, int var1) {
        for(int var2 = 0; var2 < var0.length; ++var2) {
            if(var0[var2] == var1) {
                return true;
            }
        }

        return false;
    }

    public static void fill(byte[] var0, byte var1) {
        for(int var2 = 0; var2 < var0.length; ++var2) {
            var0[var2] = var1;
        }

    }

    public static void fill(char[] var0, char var1) {
        for(int var2 = 0; var2 < var0.length; ++var2) {
            var0[var2] = var1;
        }

    }

    public static void fill(long[] var0, long var1) {
        for(int var3 = 0; var3 < var0.length; ++var3) {
            var0[var3] = var1;
        }

    }

    public static void fill(short[] var0, short var1) {
        for(int var2 = 0; var2 < var0.length; ++var2) {
            var0[var2] = var1;
        }

    }

    public static void fill(int[] var0, int var1) {
        for(int var2 = 0; var2 < var0.length; ++var2) {
            var0[var2] = var1;
        }

    }

    public static int hashCode(byte[] var0) {
        if(var0 == null) {
            return 0;
        } else {
            int var1 = var0.length;
            int var2 = var1 + 1;

            while(true) {
                --var1;
                if(var1 < 0) {
                    return var2;
                }

                var2 *= 257;
                var2 ^= var0[var1];
            }
        }
    }

    public static int hashCode(byte[] var0, int var1, int var2) {
        if(var0 == null) {
            return 0;
        } else {
            int var3 = var2;
            int var4 = var2 + 1;

            while(true) {
                --var3;
                if(var3 < 0) {
                    return var4;
                }

                var4 *= 257;
                var4 ^= var0[var1 + var3];
            }
        }
    }

    public static int hashCode(char[] var0) {
        if(var0 == null) {
            return 0;
        } else {
            int var1 = var0.length;
            int var2 = var1 + 1;

            while(true) {
                --var1;
                if(var1 < 0) {
                    return var2;
                }

                var2 *= 257;
                var2 ^= var0[var1];
            }
        }
    }

    public static int hashCode(int[][] var0) {
        int var1 = 0;

        for(int var2 = 0; var2 != var0.length; ++var2) {
            var1 = var1 * 257 + hashCode(var0[var2]);
        }

        return var1;
    }

    public static int hashCode(int[] var0) {
        if(var0 == null) {
            return 0;
        } else {
            int var1 = var0.length;
            int var2 = var1 + 1;

            while(true) {
                --var1;
                if(var1 < 0) {
                    return var2;
                }

                var2 *= 257;
                var2 ^= var0[var1];
            }
        }
    }

    public static int hashCode(int[] var0, int var1, int var2) {
        if(var0 == null) {
            return 0;
        } else {
            int var3 = var2;
            int var4 = var2 + 1;

            while(true) {
                --var3;
                if(var3 < 0) {
                    return var4;
                }

                var4 *= 257;
                var4 ^= var0[var1 + var3];
            }
        }
    }

    public static int hashCode(long[] var0) {
        if(var0 == null) {
            return 0;
        } else {
            int var1 = var0.length;
            int var2 = var1 + 1;

            while(true) {
                --var1;
                if(var1 < 0) {
                    return var2;
                }

                long var3 = var0[var1];
                var2 *= 257;
                var2 ^= (int)var3;
                var2 *= 257;
                var2 ^= (int)(var3 >>> 32);
            }
        }
    }

    public static int hashCode(long[] var0, int var1, int var2) {
        if(var0 == null) {
            return 0;
        } else {
            int var3 = var2;
            int var4 = var2 + 1;

            while(true) {
                --var3;
                if(var3 < 0) {
                    return var4;
                }

                long var5 = var0[var1 + var3];
                var4 *= 257;
                var4 ^= (int)var5;
                var4 *= 257;
                var4 ^= (int)(var5 >>> 32);
            }
        }
    }

    public static int hashCode(short[][][] var0) {
        int var1 = 0;

        for(int var2 = 0; var2 != var0.length; ++var2) {
            var1 = var1 * 257 + hashCode(var0[var2]);
        }

        return var1;
    }

    public static int hashCode(short[][] var0) {
        int var1 = 0;

        for(int var2 = 0; var2 != var0.length; ++var2) {
            var1 = var1 * 257 + hashCode(var0[var2]);
        }

        return var1;
    }

    public static int hashCode(short[] var0) {
        if(var0 == null) {
            return 0;
        } else {
            int var1 = var0.length;
            int var2 = var1 + 1;

            while(true) {
                --var1;
                if(var1 < 0) {
                    return var2;
                }

                var2 *= 257;
                var2 ^= var0[var1] & 255;
            }
        }
    }

    public static int hashCode(Object[] var0) {
        if(var0 == null) {
            return 0;
        } else {
            int var1 = var0.length;
            int var2 = var1 + 1;

            while(true) {
                --var1;
                if(var1 < 0) {
                    return var2;
                }

                var2 *= 257;
                var2 ^= var0[var1].hashCode();
            }
        }
    }

    public static byte[] clone(byte[] var0) {
        if(var0 == null) {
            return null;
        } else {
            byte[] var1 = new byte[var0.length];
            System.arraycopy(var0, 0, var1, 0, var0.length);
            return var1;
        }
    }

    public static char[] clone(char[] var0) {
        if(var0 == null) {
            return null;
        } else {
            char[] var1 = new char[var0.length];
            System.arraycopy(var0, 0, var1, 0, var0.length);
            return var1;
        }
    }

    public static byte[] clone(byte[] var0, byte[] var1) {
        if(var0 == null) {
            return null;
        } else if(var1 != null && var1.length == var0.length) {
            System.arraycopy(var0, 0, var1, 0, var1.length);
            return var1;
        } else {
            return clone(var0);
        }
    }

    public static byte[][] clone(byte[][] var0) {
        if(var0 == null) {
            return (byte[][])null;
        } else {
            byte[][] var1 = new byte[var0.length][];

            for(int var2 = 0; var2 != var1.length; ++var2) {
                var1[var2] = clone(var0[var2]);
            }

            return var1;
        }
    }

    public static byte[][][] clone(byte[][][] var0) {
        if(var0 == null) {
            return (byte[][][])null;
        } else {
            byte[][][] var1 = new byte[var0.length][][];

            for(int var2 = 0; var2 != var1.length; ++var2) {
                var1[var2] = clone(var0[var2]);
            }

            return var1;
        }
    }

    public static int[] clone(int[] var0) {
        if(var0 == null) {
            return null;
        } else {
            int[] var1 = new int[var0.length];
            System.arraycopy(var0, 0, var1, 0, var0.length);
            return var1;
        }
    }

    public static long[] clone(long[] var0) {
        if(var0 == null) {
            return null;
        } else {
            long[] var1 = new long[var0.length];
            System.arraycopy(var0, 0, var1, 0, var0.length);
            return var1;
        }
    }

    public static long[] clone(long[] var0, long[] var1) {
        if(var0 == null) {
            return null;
        } else if(var1 != null && var1.length == var0.length) {
            System.arraycopy(var0, 0, var1, 0, var1.length);
            return var1;
        } else {
            return clone(var0);
        }
    }

    public static short[] clone(short[] var0) {
        if(var0 == null) {
            return null;
        } else {
            short[] var1 = new short[var0.length];
            System.arraycopy(var0, 0, var1, 0, var0.length);
            return var1;
        }
    }

    public static BigInteger[] clone(BigInteger[] var0) {
        if(var0 == null) {
            return null;
        } else {
            BigInteger[] var1 = new BigInteger[var0.length];
            System.arraycopy(var0, 0, var1, 0, var0.length);
            return var1;
        }
    }

    public static byte[] copyOf(byte[] var0, int var1) {
        byte[] var2 = new byte[var1];
        if(var1 < var0.length) {
            System.arraycopy(var0, 0, var2, 0, var1);
        } else {
            System.arraycopy(var0, 0, var2, 0, var0.length);
        }

        return var2;
    }

    public static char[] copyOf(char[] var0, int var1) {
        char[] var2 = new char[var1];
        if(var1 < var0.length) {
            System.arraycopy(var0, 0, var2, 0, var1);
        } else {
            System.arraycopy(var0, 0, var2, 0, var0.length);
        }

        return var2;
    }

    public static int[] copyOf(int[] var0, int var1) {
        int[] var2 = new int[var1];
        if(var1 < var0.length) {
            System.arraycopy(var0, 0, var2, 0, var1);
        } else {
            System.arraycopy(var0, 0, var2, 0, var0.length);
        }

        return var2;
    }

    public static long[] copyOf(long[] var0, int var1) {
        long[] var2 = new long[var1];
        if(var1 < var0.length) {
            System.arraycopy(var0, 0, var2, 0, var1);
        } else {
            System.arraycopy(var0, 0, var2, 0, var0.length);
        }

        return var2;
    }

    public static BigInteger[] copyOf(BigInteger[] var0, int var1) {
        BigInteger[] var2 = new BigInteger[var1];
        if(var1 < var0.length) {
            System.arraycopy(var0, 0, var2, 0, var1);
        } else {
            System.arraycopy(var0, 0, var2, 0, var0.length);
        }

        return var2;
    }

    public static byte[] copyOfRange(byte[] var0, int var1, int var2) {
        int var3 = getLength(var1, var2);
        byte[] var4 = new byte[var3];
        if(var0.length - var1 < var3) {
            System.arraycopy(var0, var1, var4, 0, var0.length - var1);
        } else {
            System.arraycopy(var0, var1, var4, 0, var3);
        }

        return var4;
    }

    public static int[] copyOfRange(int[] var0, int var1, int var2) {
        int var3 = getLength(var1, var2);
        int[] var4 = new int[var3];
        if(var0.length - var1 < var3) {
            System.arraycopy(var0, var1, var4, 0, var0.length - var1);
        } else {
            System.arraycopy(var0, var1, var4, 0, var3);
        }

        return var4;
    }

    public static long[] copyOfRange(long[] var0, int var1, int var2) {
        int var3 = getLength(var1, var2);
        long[] var4 = new long[var3];
        if(var0.length - var1 < var3) {
            System.arraycopy(var0, var1, var4, 0, var0.length - var1);
        } else {
            System.arraycopy(var0, var1, var4, 0, var3);
        }

        return var4;
    }

    public static BigInteger[] copyOfRange(BigInteger[] var0, int var1, int var2) {
        int var3 = getLength(var1, var2);
        BigInteger[] var4 = new BigInteger[var3];
        if(var0.length - var1 < var3) {
            System.arraycopy(var0, var1, var4, 0, var0.length - var1);
        } else {
            System.arraycopy(var0, var1, var4, 0, var3);
        }

        return var4;
    }

    private static int getLength(int var0, int var1) {
        int var2 = var1 - var0;
        if(var2 < 0) {
            StringBuffer var3 = new StringBuffer(var0);
            var3.append(" > ").append(var1);
            throw new IllegalArgumentException(var3.toString());
        } else {
            return var2;
        }
    }

    public static byte[] append(byte[] var0, byte var1) {
        if(var0 == null) {
            return new byte[]{var1};
        } else {
            int var2 = var0.length;
            byte[] var3 = new byte[var2 + 1];
            System.arraycopy(var0, 0, var3, 0, var2);
            var3[var2] = var1;
            return var3;
        }
    }

    public static short[] append(short[] var0, short var1) {
        if(var0 == null) {
            return new short[]{var1};
        } else {
            int var2 = var0.length;
            short[] var3 = new short[var2 + 1];
            System.arraycopy(var0, 0, var3, 0, var2);
            var3[var2] = var1;
            return var3;
        }
    }

    public static int[] append(int[] var0, int var1) {
        if(var0 == null) {
            return new int[]{var1};
        } else {
            int var2 = var0.length;
            int[] var3 = new int[var2 + 1];
            System.arraycopy(var0, 0, var3, 0, var2);
            var3[var2] = var1;
            return var3;
        }
    }

    public static byte[] concatenate(byte[] var0, byte[] var1) {
        if(var0 != null && var1 != null) {
            byte[] var2 = new byte[var0.length + var1.length];
            System.arraycopy(var0, 0, var2, 0, var0.length);
            System.arraycopy(var1, 0, var2, var0.length, var1.length);
            return var2;
        } else {
            return var1 != null?clone(var1):clone(var0);
        }
    }

    public static byte[] concatenate(byte[] var0, byte[] var1, byte[] var2) {
        if(var0 != null && var1 != null && var2 != null) {
            byte[] var3 = new byte[var0.length + var1.length + var2.length];
            System.arraycopy(var0, 0, var3, 0, var0.length);
            System.arraycopy(var1, 0, var3, var0.length, var1.length);
            System.arraycopy(var2, 0, var3, var0.length + var1.length, var2.length);
            return var3;
        } else {
            return var0 == null?concatenate(var1, var2):(var1 == null?concatenate(var0, var2):concatenate(var0, var1));
        }
    }

    public static byte[] concatenate(byte[] var0, byte[] var1, byte[] var2, byte[] var3) {
        if(var0 != null && var1 != null && var2 != null && var3 != null) {
            byte[] var4 = new byte[var0.length + var1.length + var2.length + var3.length];
            System.arraycopy(var0, 0, var4, 0, var0.length);
            System.arraycopy(var1, 0, var4, var0.length, var1.length);
            System.arraycopy(var2, 0, var4, var0.length + var1.length, var2.length);
            System.arraycopy(var3, 0, var4, var0.length + var1.length + var2.length, var3.length);
            return var4;
        } else {
            return var3 == null?concatenate(var0, var1, var2):(var2 == null?concatenate(var0, var1, var3):(var1 == null?concatenate(var0, var2, var3):concatenate(var1, var2, var3)));
        }
    }

    public static int[] concatenate(int[] var0, int[] var1) {
        if(var0 == null) {
            return clone(var1);
        } else if(var1 == null) {
            return clone(var0);
        } else {
            int[] var2 = new int[var0.length + var1.length];
            System.arraycopy(var0, 0, var2, 0, var0.length);
            System.arraycopy(var1, 0, var2, var0.length, var1.length);
            return var2;
        }
    }

    public static byte[] prepend(byte[] var0, byte var1) {
        if(var0 == null) {
            return new byte[]{var1};
        } else {
            int var2 = var0.length;
            byte[] var3 = new byte[var2 + 1];
            System.arraycopy(var0, 0, var3, 1, var2);
            var3[0] = var1;
            return var3;
        }
    }

    public static short[] prepend(short[] var0, short var1) {
        if(var0 == null) {
            return new short[]{var1};
        } else {
            int var2 = var0.length;
            short[] var3 = new short[var2 + 1];
            System.arraycopy(var0, 0, var3, 1, var2);
            var3[0] = var1;
            return var3;
        }
    }

    public static int[] prepend(int[] var0, int var1) {
        if(var0 == null) {
            return new int[]{var1};
        } else {
            int var2 = var0.length;
            int[] var3 = new int[var2 + 1];
            System.arraycopy(var0, 0, var3, 1, var2);
            var3[0] = var1;
            return var3;
        }
    }

    public static byte[] reverse(byte[] var0) {
        if(var0 == null) {
            return null;
        } else {
            int var1 = 0;
            int var2 = var0.length;
            byte[] var3 = new byte[var2];

            while(true) {
                --var2;
                if(var2 < 0) {
                    return var3;
                }

                var3[var2] = var0[var1++];
            }
        }
    }

    public static int[] reverse(int[] var0) {
        if(var0 == null) {
            return null;
        } else {
            int var1 = 0;
            int var2 = var0.length;
            int[] var3 = new int[var2];

            while(true) {
                --var2;
                if(var2 < 0) {
                    return var3;
                }

                var3[var2] = var0[var1++];
            }
        }
    }

    public static class Iterator<T> implements java.util.Iterator<T> {
        private final T[] dataArray;
        private int position = 0;

        public Iterator(T[] var1) {
            this.dataArray = var1;
        }

        public boolean hasNext() {
            return this.position < this.dataArray.length;
        }

        public T next() {
            if(this.position == this.dataArray.length) {
                throw new NoSuchElementException("Out of elements: " + this.position);
            } else {
                return this.dataArray[this.position++];
            }
        }

        public void remove() {
            throw new UnsupportedOperationException("Cannot remove element from an Array.");
        }
    }
}
