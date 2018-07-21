package com.yufu.idaas.sdk.bouncycastle.asn1;

import java.io.IOException;

/**
 * Created by mac on 2017/1/17.
 */
public abstract class ASN1Primitive extends ASN1Object {
    ASN1Primitive() {
    }

    public static ASN1Primitive fromByteArray(byte[] var0) throws IOException {
        ASN1InputStream var1 = new ASN1InputStream(var0);

        try {
            ASN1Primitive var2 = var1.readObject();
            if(var1.available() != 0) {
                throw new IOException("Extra data detected in stream");
            } else {
                return var2;
            }
        } catch (ClassCastException var3) {
            throw new IOException("cannot recognise object in stream");
        }
    }

    public final boolean equals(Object var1) {
        return this == var1?true:var1 instanceof ASN1Encodable && this.asn1Equals(((ASN1Encodable)var1).toASN1Primitive());
    }

    public ASN1Primitive toASN1Primitive() {
        return this;
    }

    ASN1Primitive toDERObject() {
        return this;
    }

    ASN1Primitive toDLObject() {
        return this;
    }

    public abstract int hashCode();

    abstract boolean isConstructed();

    abstract int encodedLength() throws IOException;

    abstract void encode(ASN1OutputStream var1) throws IOException;

    abstract boolean asn1Equals(ASN1Primitive var1);
}
