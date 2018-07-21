package com.yufu.idaas.sdk.bouncycastle.asn1;

import com.yufu.idaas.sdk.bouncycastle.util.Arrays;
import com.yufu.idaas.sdk.bouncycastle.util.Strings;

import java.io.IOException;

/**
 * Created by mac on 2017/1/17.
 */
public class DERUTF8String extends ASN1Primitive implements ASN1String {
    private final byte[] string;

    public static DERUTF8String getInstance(Object var0) {
        if(var0 != null && !(var0 instanceof DERUTF8String)) {
            if(var0 instanceof byte[]) {
                try {
                    return (DERUTF8String)fromByteArray((byte[])((byte[])var0));
                } catch (Exception var2) {
                    throw new IllegalArgumentException("encoding error in getInstance: " + var2.toString());
                }
            } else {
                throw new IllegalArgumentException("illegal object in getInstance: " + var0.getClass().getName());
            }
        } else {
            return (DERUTF8String)var0;
        }
    }

    public static DERUTF8String getInstance(ASN1TaggedObject var0, boolean var1) {
        ASN1Primitive var2 = var0.getObject();
        return !var1 && !(var2 instanceof DERUTF8String)?new DERUTF8String(ASN1OctetString.getInstance(var2).getOctets()):getInstance(var2);
    }

    DERUTF8String(byte[] var1) {
        this.string = var1;
    }

    public DERUTF8String(String var1) {
        this.string = Strings.toUTF8ByteArray(var1);
    }

    public String getString() {
        return Strings.fromUTF8ByteArray(this.string);
    }

    public String toString() {
        return this.getString();
    }

    public int hashCode() {
        return Arrays.hashCode(this.string);
    }

    boolean asn1Equals(ASN1Primitive var1) {
        if(!(var1 instanceof DERUTF8String)) {
            return false;
        } else {
            DERUTF8String var2 = (DERUTF8String)var1;
            return Arrays.areEqual(this.string, var2.string);
        }
    }

    boolean isConstructed() {
        return false;
    }

    int encodedLength() throws IOException {
        return 1 + StreamUtil.calculateBodyLength(this.string.length) + this.string.length;
    }

    void encode(ASN1OutputStream var1) throws IOException {
        var1.writeEncoded(12, this.string);
    }
}

