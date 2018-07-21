package com.yufu.idaas.sdk.bouncycastle.asn1;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by mac on 2017/1/17.
 */

public class DLOutputStream extends ASN1OutputStream {
    public DLOutputStream(OutputStream var1) {
        super(var1);
    }

    public void writeObject(ASN1Encodable var1) throws IOException {
        if(var1 != null) {
            var1.toASN1Primitive().toDLObject().encode(this);
        } else {
            throw new IOException("null object detected");
        }
    }
}
