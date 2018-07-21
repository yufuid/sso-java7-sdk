package com.yufu.idaas.sdk.bouncycastle.asn1;

/**
 * Created by mac on 2017/1/17.
 */

public class ASN1ParsingException extends IllegalStateException {
    private Throwable cause;

    public ASN1ParsingException(String var1) {
        super(var1);
    }

    public ASN1ParsingException(String var1, Throwable var2) {
        super(var1);
        this.cause = var2;
    }

    public Throwable getCause() {
        return this.cause;
    }
}
