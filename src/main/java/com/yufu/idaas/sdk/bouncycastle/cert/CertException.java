package com.yufu.idaas.sdk.bouncycastle.cert;

/**
 * Created by mac on 2017/1/18.
 */
public class CertException extends Exception {
    private Throwable cause;

    public CertException(String var1, Throwable var2) {
        super(var1);
        this.cause = var2;
    }

    public CertException(String var1) {
        super(var1);
    }

    public Throwable getCause() {
        return this.cause;
    }
}
