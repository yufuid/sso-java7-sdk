package com.yufu.idaas.sdk.bouncycastle.asn1.pkcs;

import java.io.IOException;

public class PKCSIOException extends IOException {
    private Throwable cause;

    public PKCSIOException(String var1, Throwable var2) {
        super(var1);
        this.cause = var2;
    }

    public PKCSIOException(String var1) {
        super(var1);
    }

    public Throwable getCause() {
        return this.cause;
    }
}
