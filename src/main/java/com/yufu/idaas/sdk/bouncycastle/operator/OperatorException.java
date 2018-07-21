package com.yufu.idaas.sdk.bouncycastle.operator;

/**
 * Created by mac on 2017/1/18.
 */
public class OperatorException extends Exception {
    private Throwable cause;

    public OperatorException(String var1, Throwable var2) {
        super(var1);
        this.cause = var2;
    }

    public OperatorException(String var1) {
        super(var1);
    }

    public Throwable getCause() {
        return this.cause;
    }
}

