package com.yufu.idaas.sdk.bouncycastle.asn1;

import java.io.IOException;
import java.util.Enumeration;

/**
 * Created by mac on 2017/1/17.
 */
class LazyConstructionEnumeration implements Enumeration {
    private ASN1InputStream aIn;
    private Object nextObj;

    public LazyConstructionEnumeration(byte[] var1) {
        this.aIn = new ASN1InputStream(var1, true);
        this.nextObj = this.readObject();
    }

    public boolean hasMoreElements() {
        return this.nextObj != null;
    }

    public Object nextElement() {
        Object var1 = this.nextObj;
        this.nextObj = this.readObject();
        return var1;
    }

    private Object readObject() {
        try {
            return this.aIn.readObject();
        } catch (IOException var2) {
            throw new ASN1ParsingException("malformed DER construction: " + var2, var2);
        }
    }
}
