package com.yufu.idaas.sdk.bouncycastle.asn1;

import com.yufu.idaas.sdk.bouncycastle.util.Arrays;

import java.io.IOException;

/**
 * Created by mac on 2017/1/17.
 */
public class DERBMPString extends ASN1Primitive implements ASN1String {
    private final char[] string;

    public static DERBMPString getInstance(Object var0) {
        if(var0 != null && !(var0 instanceof DERBMPString)) {
            if(var0 instanceof byte[]) {
                try {
                    return (DERBMPString)fromByteArray((byte[])((byte[])var0));
                } catch (Exception var2) {
                    throw new IllegalArgumentException("encoding error in getInstance: " + var2.toString());
                }
            } else {
                throw new IllegalArgumentException("illegal object in getInstance: " + var0.getClass().getName());
            }
        } else {
            return (DERBMPString)var0;
        }
    }

    public static DERBMPString getInstance(ASN1TaggedObject var0, boolean var1) {
        ASN1Primitive var2 = var0.getObject();
        return !var1 && !(var2 instanceof DERBMPString)?new DERBMPString(ASN1OctetString.getInstance(var2).getOctets()):getInstance(var2);
    }

    DERBMPString(byte[] var1) {
        char[] var2 = new char[var1.length / 2];

        for(int var3 = 0; var3 != var2.length; ++var3) {
            var2[var3] = (char)(var1[2 * var3] << 8 | var1[2 * var3 + 1] & 255);
        }

        this.string = var2;
    }

    DERBMPString(char[] var1) {
        this.string = var1;
    }

    public DERBMPString(String var1) {
        this.string = var1.toCharArray();
    }

    public String getString() {
        return new String(this.string);
    }

    public String toString() {
        return this.getString();
    }

    public int hashCode() {
        return Arrays.hashCode(this.string);
    }

    protected boolean asn1Equals(ASN1Primitive var1) {
        if(!(var1 instanceof DERBMPString)) {
            return false;
        } else {
            DERBMPString var2 = (DERBMPString)var1;
            return Arrays.areEqual(this.string, var2.string);
        }
    }

    boolean isConstructed() {
        return false;
    }

    int encodedLength() {
        return 1 + StreamUtil.calculateBodyLength(this.string.length * 2) + this.string.length * 2;
    }

    void encode(ASN1OutputStream var1) throws IOException {
        var1.write(30);
        var1.writeLength(this.string.length * 2);

        for(int var2 = 0; var2 != this.string.length; ++var2) {
            char var3 = this.string[var2];
            var1.write((byte)(var3 >> 8));
            var1.write((byte)var3);
        }

    }
}

