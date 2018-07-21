package com.yufu.idaas.sdk.bouncycastle.util.io.pem;

import java.io.IOException;

/**
 * Created by mac on 2017/1/18.
 */
public class PemGenerationException extends IOException {
    private Throwable cause;

    public PemGenerationException(String var1, Throwable var2) {
        super(var1);
        this.cause = var2;
    }

    public PemGenerationException(String var1) {
        super(var1);
    }

    public Throwable getCause() {
        return this.cause;
    }
}
