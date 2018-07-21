package com.yufu.idaas.sdk.bouncycastle.asn1;

import com.yufu.idaas.sdk.bouncycastle.util.Encodable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by mac on 2017/1/17.
 */
public abstract class ASN1Object implements ASN1Encodable, Encodable {
    public ASN1Object() {
    }

    public byte[] getEncoded() throws IOException {
        ByteArrayOutputStream var1 = new ByteArrayOutputStream();
        ASN1OutputStream var2 = new ASN1OutputStream(var1);
        var2.writeObject(this);
        return var1.toByteArray();
    }

    public byte[] getEncoded(String var1) throws IOException {
        ByteArrayOutputStream var2;
        if(var1.equals("DER")) {
            var2 = new ByteArrayOutputStream();
            DEROutputStream var4 = new DEROutputStream(var2);
            var4.writeObject(this);
            return var2.toByteArray();
        } else if(var1.equals("DL")) {
            var2 = new ByteArrayOutputStream();
            DLOutputStream var3 = new DLOutputStream(var2);
            var3.writeObject(this);
            return var2.toByteArray();
        } else {
            return this.getEncoded();
        }
    }

    public int hashCode() {
        return this.toASN1Primitive().hashCode();
    }

    public boolean equals(Object var1) {
        if(this == var1) {
            return true;
        } else if(!(var1 instanceof ASN1Encodable)) {
            return false;
        } else {
            ASN1Encodable var2 = (ASN1Encodable)var1;
            return this.toASN1Primitive().equals(var2.toASN1Primitive());
        }
    }

    /** @deprecated */
    public ASN1Primitive toASN1Object() {
        return this.toASN1Primitive();
    }

    protected static boolean hasEncodedTagValue(Object var0, int var1) {
        return var0 instanceof byte[] && ((byte[])((byte[])var0))[0] == var1;
    }

    public abstract ASN1Primitive toASN1Primitive();
}
