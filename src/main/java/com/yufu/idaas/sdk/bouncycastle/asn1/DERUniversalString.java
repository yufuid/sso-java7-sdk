package com.yufu.idaas.sdk.bouncycastle.asn1;

import com.yufu.idaas.sdk.bouncycastle.util.Arrays;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by mac on 2017/1/17.
 */
public class DERUniversalString extends ASN1Primitive implements ASN1String {
    private static final char[] table = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private final byte[] string;

    public static DERUniversalString getInstance(Object var0) {
        if(var0 != null && !(var0 instanceof DERUniversalString)) {
            if(var0 instanceof byte[]) {
                try {
                    return (DERUniversalString)fromByteArray((byte[])((byte[])var0));
                } catch (Exception var2) {
                    throw new IllegalArgumentException("encoding error getInstance: " + var2.toString());
                }
            } else {
                throw new IllegalArgumentException("illegal object in getInstance: " + var0.getClass().getName());
            }
        } else {
            return (DERUniversalString)var0;
        }
    }

    public static DERUniversalString getInstance(ASN1TaggedObject var0, boolean var1) {
        ASN1Primitive var2 = var0.getObject();
        return !var1 && !(var2 instanceof DERUniversalString)?new DERUniversalString(((ASN1OctetString)var2).getOctets()):getInstance(var2);
    }

    public DERUniversalString(byte[] var1) {
        this.string = var1;
    }

    public String getString() {
        StringBuffer var1 = new StringBuffer("#");
        ByteArrayOutputStream var2 = new ByteArrayOutputStream();
        ASN1OutputStream var3 = new ASN1OutputStream(var2);

        try {
            var3.writeObject(this);
        } catch (IOException var6) {
            throw new RuntimeException("internal error encoding BitString");
        }

        byte[] var4 = var2.toByteArray();

        for(int var5 = 0; var5 != var4.length; ++var5) {
            var1.append(table[var4[var5] >>> 4 & 15]);
            var1.append(table[var4[var5] & 15]);
        }

        return var1.toString();
    }

    public String toString() {
        return this.getString();
    }

    public byte[] getOctets() {
        return this.string;
    }

    boolean isConstructed() {
        return false;
    }

    int encodedLength() {
        return 1 + StreamUtil.calculateBodyLength(this.string.length) + this.string.length;
    }

    void encode(ASN1OutputStream var1) throws IOException {
        var1.writeEncoded(28, this.getOctets());
    }

    boolean asn1Equals(ASN1Primitive var1) {
        return !(var1 instanceof DERUniversalString)?false: Arrays.areEqual(this.string, ((DERUniversalString)var1).string);
    }

    public int hashCode() {
        return Arrays.hashCode(this.string);
    }
}
