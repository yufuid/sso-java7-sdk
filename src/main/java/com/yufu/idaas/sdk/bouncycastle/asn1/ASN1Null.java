package com.yufu.idaas.sdk.bouncycastle.asn1;

import java.io.IOException;

/**
 * Created by mac on 2017/1/17.
 */

public abstract class ASN1Null extends ASN1Primitive {
    public ASN1Null() {
    }

    public static ASN1Null getInstance(Object var0) {
        if(var0 instanceof ASN1Null) {
            return (ASN1Null)var0;
        } else if(var0 != null) {
            try {
                return getInstance(ASN1Primitive.fromByteArray((byte[])((byte[])var0)));
            } catch (IOException var2) {
                throw new IllegalArgumentException("failed to construct NULL from byte[]: " + var2.getMessage());
            } catch (ClassCastException var3) {
                throw new IllegalArgumentException("unknown object in getInstance(): " + var0.getClass().getName());
            }
        } else {
            return null;
        }
    }

    public int hashCode() {
        return -1;
    }

    boolean asn1Equals(ASN1Primitive var1) {
        return var1 instanceof ASN1Null;
    }

    abstract void encode(ASN1OutputStream var1) throws IOException;

    public String toString() {
        return "NULL";
    }
}
