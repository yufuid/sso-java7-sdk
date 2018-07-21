package com.yufu.idaas.sdk.bouncycastle.asn1;

import com.yufu.idaas.sdk.bouncycastle.util.Arrays;
import com.yufu.idaas.sdk.bouncycastle.util.io.Streams;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by mac on 2017/1/17.
 */
public abstract class ASN1BitString extends ASN1Primitive implements ASN1String {
    private static final char[] table = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    protected final byte[] data;
    protected final int padBits;

    protected static int getPadBits(int var0) {
        int var1 = 0;

        int var2;
        for(var2 = 3; var2 >= 0; --var2) {
            if(var2 != 0) {
                if(var0 >> var2 * 8 != 0) {
                    var1 = var0 >> var2 * 8 & 255;
                    break;
                }
            } else if(var0 != 0) {
                var1 = var0 & 255;
                break;
            }
        }

        if(var1 == 0) {
            return 0;
        } else {
            for(var2 = 1; ((var1 <<= 1) & 255) != 0; ++var2) {
                ;
            }

            return 8 - var2;
        }
    }

    protected static byte[] getBytes(int var0) {
        if(var0 == 0) {
            return new byte[0];
        } else {
            int var1 = 4;

            for(int var2 = 3; var2 >= 1 && (var0 & 255 << var2 * 8) == 0; --var2) {
                --var1;
            }

            byte[] var4 = new byte[var1];

            for(int var3 = 0; var3 < var1; ++var3) {
                var4[var3] = (byte)(var0 >> var3 * 8 & 255);
            }

            return var4;
        }
    }

    public ASN1BitString(byte[] var1, int var2) {
        if(var1 == null) {
            throw new NullPointerException("data cannot be null");
        } else if(var1.length == 0 && var2 != 0) {
            throw new IllegalArgumentException("zero length data with non-zero pad bits");
        } else if(var2 <= 7 && var2 >= 0) {
            this.data = Arrays.clone(var1);
            this.padBits = var2;
        } else {
            throw new IllegalArgumentException("pad bits cannot be greater than 7 or less than 0");
        }
    }

    public String getString() {
        StringBuffer var1 = new StringBuffer("#");
        ByteArrayOutputStream var2 = new ByteArrayOutputStream();
        ASN1OutputStream var3 = new ASN1OutputStream(var2);

        try {
            var3.writeObject(this);
        } catch (IOException var6) {
            throw new ASN1ParsingException("Internal error encoding BitString: " + var6.getMessage(), var6);
        }

        byte[] var4 = var2.toByteArray();

        for(int var5 = 0; var5 != var4.length; ++var5) {
            var1.append(table[var4[var5] >>> 4 & 15]);
            var1.append(table[var4[var5] & 15]);
        }

        return var1.toString();
    }

    public int intValue() {
        int var1 = 0;
        byte[] var2 = this.data;
        if(this.padBits > 0 && this.data.length <= 4) {
            var2 = derForm(this.data, this.padBits);
        }

        for(int var3 = 0; var3 != var2.length && var3 != 4; ++var3) {
            var1 |= (var2[var3] & 255) << 8 * var3;
        }

        return var1;
    }

    public byte[] getOctets() {
        if(this.padBits != 0) {
            throw new IllegalStateException("attempt to get non-octet aligned data from BIT STRING");
        } else {
            return Arrays.clone(this.data);
        }
    }

    public byte[] getBytes() {
        return derForm(this.data, this.padBits);
    }

    public int getPadBits() {
        return this.padBits;
    }

    public String toString() {
        return this.getString();
    }

    public int hashCode() {
        return this.padBits ^ Arrays.hashCode(this.getBytes());
    }

    protected boolean asn1Equals(ASN1Primitive var1) {
        if(!(var1 instanceof ASN1BitString)) {
            return false;
        } else {
            ASN1BitString var2 = (ASN1BitString)var1;
            return this.padBits == var2.padBits && Arrays.areEqual(this.getBytes(), var2.getBytes());
        }
    }

    protected static byte[] derForm(byte[] var0, int var1) {
        byte[] var2 = Arrays.clone(var0);
        if(var1 > 0) {
            var2[var0.length - 1] = (byte)(var2[var0.length - 1] & 255 << var1);
        }

        return var2;
    }

    static ASN1BitString fromInputStream(int var0, InputStream var1) throws IOException {
        if(var0 < 1) {
            throw new IllegalArgumentException("truncated BIT STRING detected");
        } else {
            int var2 = var1.read();
            byte[] var3 = new byte[var0 - 1];
            if(var3.length != 0) {
                if(Streams.readFully(var1, var3) != var3.length) {
                    throw new EOFException("EOF encountered in middle of BIT STRING");
                }

                if(var2 > 0 && var2 < 8 && var3[var3.length - 1] != (byte)(var3[var3.length - 1] & 255 << var2)) {
                    return new DLBitString(var3, var2);
                }
            }

            return new DERBitString(var3, var2);
        }
    }

    public ASN1Primitive getLoadedObject() {
        return this.toASN1Primitive();
    }

    ASN1Primitive toDERObject() {
        return new DERBitString(this.data, this.padBits);
    }

    ASN1Primitive toDLObject() {
        return new DLBitString(this.data, this.padBits);
    }

    abstract void encode(ASN1OutputStream var1) throws IOException;
}
