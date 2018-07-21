package com.yufu.idaas.sdk.bouncycastle.util.encoders;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by mac on 2017/1/17.
 */
public class HexEncoder implements Encoder {
    protected final byte[] encodingTable = new byte[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};
    protected final byte[] decodingTable = new byte[128];

    protected void initialiseDecodingTable() {
        int var1;
        for(var1 = 0; var1 < this.decodingTable.length; ++var1) {
            this.decodingTable[var1] = -1;
        }

        for(var1 = 0; var1 < this.encodingTable.length; ++var1) {
            this.decodingTable[this.encodingTable[var1]] = (byte)var1;
        }

        this.decodingTable[65] = this.decodingTable[97];
        this.decodingTable[66] = this.decodingTable[98];
        this.decodingTable[67] = this.decodingTable[99];
        this.decodingTable[68] = this.decodingTable[100];
        this.decodingTable[69] = this.decodingTable[101];
        this.decodingTable[70] = this.decodingTable[102];
    }

    public HexEncoder() {
        this.initialiseDecodingTable();
    }

    public int encode(byte[] var1, int var2, int var3, OutputStream var4) throws IOException {
        for(int var5 = var2; var5 < var2 + var3; ++var5) {
            int var6 = var1[var5] & 255;
            var4.write(this.encodingTable[var6 >>> 4]);
            var4.write(this.encodingTable[var6 & 15]);
        }

        return var3 * 2;
    }

    private static boolean ignore(char var0) {
        return var0 == 10 || var0 == 13 || var0 == 9 || var0 == 32;
    }

    public int decode(byte[] var1, int var2, int var3, OutputStream var4) throws IOException {
        int var7 = 0;

        int var8;
        for(var8 = var2 + var3; var8 > var2 && ignore((char)var1[var8 - 1]); --var8) {
            ;
        }

        for(int var9 = var2; var9 < var8; ++var7) {
            while(var9 < var8 && ignore((char)var1[var9])) {
                ++var9;
            }

            byte var5;
            for(var5 = this.decodingTable[var1[var9++]]; var9 < var8 && ignore((char)var1[var9]); ++var9) {
                ;
            }

            byte var6 = this.decodingTable[var1[var9++]];
            if((var5 | var6) < 0) {
                throw new IOException("invalid characters encountered in Hex data");
            }

            var4.write(var5 << 4 | var6);
        }

        return var7;
    }

    public int decode(String var1, OutputStream var2) throws IOException {
        int var5 = 0;

        int var6;
        for(var6 = var1.length(); var6 > 0 && ignore(var1.charAt(var6 - 1)); --var6) {
            ;
        }

        for(int var7 = 0; var7 < var6; ++var5) {
            while(var7 < var6 && ignore(var1.charAt(var7))) {
                ++var7;
            }

            byte var3;
            for(var3 = this.decodingTable[var1.charAt(var7++)]; var7 < var6 && ignore(var1.charAt(var7)); ++var7) {
                ;
            }

            byte var4 = this.decodingTable[var1.charAt(var7++)];
            if((var3 | var4) < 0) {
                throw new IOException("invalid characters encountered in Hex string");
            }

            var2.write(var3 << 4 | var4);
        }

        return var5;
    }
}

