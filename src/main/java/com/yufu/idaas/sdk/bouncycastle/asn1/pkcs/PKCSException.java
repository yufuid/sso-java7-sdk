package com.yufu.idaas.sdk.bouncycastle.asn1.pkcs;

/**
 * Created by mac on 2017/1/18.
 */
public class PKCSException extends Exception {
    private Throwable cause;

    public PKCSException(String var1, Throwable var2) {
        super(var1);
        this.cause = var2;
    }

    public PKCSException(String var1) {
        super(var1);
    }

    public Throwable getCause() {
        return this.cause;
    }
}
