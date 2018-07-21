package com.yufu.idaas.sdk.bouncycastle.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by mac on 2017/1/17.
 */
public final class Strings {
    private static String LINE_SEPARATOR;

    public Strings() {
    }

    public static String fromUTF8ByteArray(byte[] var0) {
        int var1 = 0;
        int var2 = 0;

        while(var1 < var0.length) {
            ++var2;
            if((var0[var1] & 240) == 240) {
                ++var2;
                var1 += 4;
            } else if((var0[var1] & 224) == 224) {
                var1 += 3;
            } else if((var0[var1] & 192) == 192) {
                var1 += 2;
            } else {
                ++var1;
            }
        }

        char[] var3 = new char[var2];
        var1 = 0;

        char var4;
        for(var2 = 0; var1 < var0.length; var3[var2++] = var4) {
            if((var0[var1] & 240) == 240) {
                int var5 = (var0[var1] & 3) << 18 | (var0[var1 + 1] & 63) << 12 | (var0[var1 + 2] & 63) << 6 | var0[var1 + 3] & 63;
                int var6 = var5 - 65536;
                char var7 = (char)('\ud800' | var6 >> 10);
                char var8 = (char)('\udc00' | var6 & 1023);
                var3[var2++] = var7;
                var4 = var8;
                var1 += 4;
            } else if((var0[var1] & 224) == 224) {
                var4 = (char)((var0[var1] & 15) << 12 | (var0[var1 + 1] & 63) << 6 | var0[var1 + 2] & 63);
                var1 += 3;
            } else if((var0[var1] & 208) == 208) {
                var4 = (char)((var0[var1] & 31) << 6 | var0[var1 + 1] & 63);
                var1 += 2;
            } else if((var0[var1] & 192) == 192) {
                var4 = (char)((var0[var1] & 31) << 6 | var0[var1 + 1] & 63);
                var1 += 2;
            } else {
                var4 = (char)(var0[var1] & 255);
                ++var1;
            }
        }

        return new String(var3);
    }

    public static byte[] toUTF8ByteArray(String var0) {
        return toUTF8ByteArray(var0.toCharArray());
    }

    public static byte[] toUTF8ByteArray(char[] var0) {
        ByteArrayOutputStream var1 = new ByteArrayOutputStream();

        try {
            toUTF8ByteArray(var0, var1);
        } catch (IOException var3) {
            throw new IllegalStateException("cannot encode string to byte array!");
        }

        return var1.toByteArray();
    }

    public static void toUTF8ByteArray(char[] var0, OutputStream var1) throws IOException {
        char[] var2 = var0;

        for(int var3 = 0; var3 < var2.length; ++var3) {
            char var4 = var2[var3];
            if(var4 < 128) {
                var1.write(var4);
            } else if(var4 < 2048) {
                var1.write(192 | var4 >> 6);
                var1.write(128 | var4 & 63);
            } else if(var4 >= '\ud800' && var4 <= '\udfff') {
                if(var3 + 1 >= var2.length) {
                    throw new IllegalStateException("invalid UTF-16 codepoint");
                }

                char var5 = var4;
                ++var3;
                var4 = var2[var3];
                if(var5 > '\udbff') {
                    throw new IllegalStateException("invalid UTF-16 codepoint");
                }

                int var7 = ((var5 & 1023) << 10 | var4 & 1023) + 65536;
                var1.write(240 | var7 >> 18);
                var1.write(128 | var7 >> 12 & 63);
                var1.write(128 | var7 >> 6 & 63);
                var1.write(128 | var7 & 63);
            } else {
                var1.write(224 | var4 >> 12);
                var1.write(128 | var4 >> 6 & 63);
                var1.write(128 | var4 & 63);
            }
        }

    }

    public static String toUpperCase(String var0) {
        boolean var1 = false;
        char[] var2 = var0.toCharArray();

        for(int var3 = 0; var3 != var2.length; ++var3) {
            char var4 = var2[var3];
            if(97 <= var4 && 122 >= var4) {
                var1 = true;
                var2[var3] = (char)(var4 - 97 + 65);
            }
        }

        if(var1) {
            return new String(var2);
        } else {
            return var0;
        }
    }

    public static String toLowerCase(String var0) {
        boolean var1 = false;
        char[] var2 = var0.toCharArray();

        for(int var3 = 0; var3 != var2.length; ++var3) {
            char var4 = var2[var3];
            if(65 <= var4 && 90 >= var4) {
                var1 = true;
                var2[var3] = (char)(var4 - 65 + 97);
            }
        }

        if(var1) {
            return new String(var2);
        } else {
            return var0;
        }
    }

    public static byte[] toByteArray(char[] var0) {
        byte[] var1 = new byte[var0.length];

        for(int var2 = 0; var2 != var1.length; ++var2) {
            var1[var2] = (byte)var0[var2];
        }

        return var1;
    }

    public static byte[] toByteArray(String var0) {
        byte[] var1 = new byte[var0.length()];

        for(int var2 = 0; var2 != var1.length; ++var2) {
            char var3 = var0.charAt(var2);
            var1[var2] = (byte)var3;
        }

        return var1;
    }

    public static int toByteArray(String var0, byte[] var1, int var2) {
        int var3 = var0.length();

        for(int var4 = 0; var4 < var3; ++var4) {
            char var5 = var0.charAt(var4);
            var1[var2 + var4] = (byte)var5;
        }

        return var3;
    }

    public static String fromByteArray(byte[] var0) {
        return new String(asCharArray(var0));
    }

    public static char[] asCharArray(byte[] var0) {
        char[] var1 = new char[var0.length];

        for(int var2 = 0; var2 != var1.length; ++var2) {
            var1[var2] = (char)(var0[var2] & 255);
        }

        return var1;
    }

    public static String[] split(String var0, char var1) {
        Vector var2 = new Vector();
        boolean var3 = true;

        while(var3) {
            int var5 = var0.indexOf(var1);
            if(var5 > 0) {
                String var4 = var0.substring(0, var5);
                var2.addElement(var4);
                var0 = var0.substring(var5 + 1);
            } else {
                var3 = false;
                var2.addElement(var0);
            }
        }

        String[] var7 = new String[var2.size()];

        for(int var6 = 0; var6 != var7.length; ++var6) {
            var7[var6] = (String)var2.elementAt(var6);
        }

        return var7;
    }

    public static StringList newList() {
        return new StringListImpl();
    }

    public static String lineSeparator() {
        return LINE_SEPARATOR;
    }

    static {
        try {
            LINE_SEPARATOR = (String) AccessController.doPrivileged(new PrivilegedAction() {
                public String run() {
                    return System.getProperty("line.separator");
                }
            });
        } catch (Exception var3) {
            try {
                LINE_SEPARATOR = String.format("%n", new Object[0]);
            } catch (Exception var2) {
                LINE_SEPARATOR = "\n";
            }
        }

    }

    private static class StringListImpl extends ArrayList<String> implements StringList {
        private StringListImpl() {
        }

        public boolean add(String var1) {
            return super.add(var1);
        }

        public String set(int var1, String var2) {
            return (String)super.set(var1, var2);
        }

        public void add(int var1, String var2) {
            super.add(var1, var2);
        }

        public String[] toStringArray() {
            String[] var1 = new String[this.size()];

            for(int var2 = 0; var2 != var1.length; ++var2) {
                var1[var2] = (String)this.get(var2);
            }

            return var1;
        }

        public String[] toStringArray(int var1, int var2) {
            String[] var3 = new String[var2 - var1];

            for(int var4 = var1; var4 != this.size() && var4 != var2; ++var4) {
                var3[var4 - var1] = (String)this.get(var4);
            }

            return var3;
        }
    }
}

