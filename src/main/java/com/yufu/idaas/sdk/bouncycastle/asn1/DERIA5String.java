package com.yufu.idaas.sdk.bouncycastle.asn1;

import com.yufu.idaas.sdk.bouncycastle.util.Arrays;
import com.yufu.idaas.sdk.bouncycastle.util.Strings;

import java.io.IOException;

/**
 * Created by mac on 2017/1/17.
 */
public class DERIA5String extends ASN1Primitive implements ASN1String {
    private final byte[] string;

    public static DERIA5String getInstance(Object var0) {
        if(var0 != null && !(var0 instanceof DERIA5String)) {
            if(var0 instanceof byte[]) {
                try {
                    return (DERIA5String)fromByteArray((byte[])((byte[])var0));
                } catch (Exception var2) {
                    throw new IllegalArgumentException("encoding error in getInstance: " + var2.toString());
                }
            } else {
                throw new IllegalArgumentException("illegal object in getInstance: " + var0.getClass().getName());
            }
        } else {
            return (DERIA5String)var0;
        }
    }

    public static DERIA5String getInstance(ASN1TaggedObject var0, boolean var1) {
        ASN1Primitive var2 = var0.getObject();
        return !var1 && !(var2 instanceof DERIA5String)?new DERIA5String(((ASN1OctetString)var2).getOctets()):getInstance(var2);
    }

    DERIA5String(byte[] var1) {
        this.string = var1;
    }

    public DERIA5String(String var1) {
        this(var1, false);
    }

    public DERIA5String(String var1, boolean var2) {
        if(var1 == null) {
            throw new NullPointerException("string cannot be null");
        } else if(var2 && !isIA5String(var1)) {
            throw new IllegalArgumentException("string contains illegal characters");
        } else {
            this.string = Strings.toByteArray(var1);
        }
    }

    public String getString() {
        return Strings.fromByteArray(this.string);
    }

    public String toString() {
        return this.getString();
    }

    public byte[] getOctets() {
        return Arrays.clone(this.string);
    }

    boolean isConstructed() {
        return false;
    }

    int encodedLength() {
        return 1 + StreamUtil.calculateBodyLength(this.string.length) + this.string.length;
    }

    void encode(ASN1OutputStream var1) throws IOException {
        var1.writeEncoded(22, this.string);
    }

    public int hashCode() {
        return Arrays.hashCode(this.string);
    }

    boolean asn1Equals(ASN1Primitive var1) {
        if(!(var1 instanceof DERIA5String)) {
            return false;
        } else {
            DERIA5String var2 = (DERIA5String)var1;
            return Arrays.areEqual(this.string, var2.string);
        }
    }

    public static boolean isIA5String(String var0) {
        for(int var1 = var0.length() - 1; var1 >= 0; --var1) {
            char var2 = var0.charAt(var1);
            if(var2 > 127) {
                return false;
            }
        }

        return true;
    }
}
