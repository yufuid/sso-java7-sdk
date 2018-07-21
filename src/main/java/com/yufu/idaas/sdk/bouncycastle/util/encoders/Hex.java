package com.yufu.idaas.sdk.bouncycastle.util.encoders;

import com.yufu.idaas.sdk.bouncycastle.util.Strings;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by mac on 2017/1/17.
 */
public class Hex {
    private static final Encoder encoder = new HexEncoder();

    public Hex() {
    }

    public static String toHexString(byte[] var0) {
        return toHexString(var0, 0, var0.length);
    }

    public static String toHexString(byte[] var0, int var1, int var2) {
        byte[] var3 = encode(var0, var1, var2);
        return Strings.fromByteArray(var3);
    }

    public static byte[] encode(byte[] var0) {
        return encode(var0, 0, var0.length);
    }

    public static byte[] encode(byte[] var0, int var1, int var2) {
        ByteArrayOutputStream var3 = new ByteArrayOutputStream();

        try {
            encoder.encode(var0, var1, var2, var3);
        } catch (Exception var5) {
            throw new EncoderException("exception encoding Hex string: " + var5.getMessage(), var5);
        }

        return var3.toByteArray();
    }

    public static int encode(byte[] var0, OutputStream var1) throws IOException {
        return encoder.encode(var0, 0, var0.length, var1);
    }

    public static int encode(byte[] var0, int var1, int var2, OutputStream var3) throws IOException {
        return encoder.encode(var0, var1, var2, var3);
    }

    public static byte[] decode(byte[] var0) {
        ByteArrayOutputStream var1 = new ByteArrayOutputStream();

        try {
            encoder.decode(var0, 0, var0.length, var1);
        } catch (Exception var3) {
            throw new DecoderException("exception decoding Hex data: " + var3.getMessage(), var3);
        }

        return var1.toByteArray();
    }

    public static byte[] decode(String var0) {
        ByteArrayOutputStream var1 = new ByteArrayOutputStream();

        try {
            encoder.decode(var0, var1);
        } catch (Exception var3) {
            throw new DecoderException("exception decoding Hex string: " + var3.getMessage(), var3);
        }

        return var1.toByteArray();
    }

    public static int decode(String var0, OutputStream var1) throws IOException {
        return encoder.decode(var0, var1);
    }
}

