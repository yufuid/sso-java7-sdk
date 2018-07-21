package com.yufu.idaas.sdk.bouncycastle.asn1;

import java.io.IOException;

/**
 * Created by mac on 2017/1/17.
 */
public class DERNull extends ASN1Null {
    public static final DERNull INSTANCE = new DERNull();
    private static final byte[] zeroBytes = new byte[0];

    /** @deprecated */
    public DERNull() {
    }

    boolean isConstructed() {
        return false;
    }

    int encodedLength() {
        return 2;
    }

    void encode(ASN1OutputStream var1) throws IOException {
        var1.writeEncoded(5, zeroBytes);
    }
}

