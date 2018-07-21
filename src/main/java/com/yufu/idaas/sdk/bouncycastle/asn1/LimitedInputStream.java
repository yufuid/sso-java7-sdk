package com.yufu.idaas.sdk.bouncycastle.asn1;

import java.io.InputStream;

/**
 * Created by mac on 2017/1/17.
 */
abstract class LimitedInputStream extends InputStream {
    protected final InputStream _in;
    private int _limit;

    LimitedInputStream(InputStream var1, int var2) {
        this._in = var1;
        this._limit = var2;
    }

    int getRemaining() {
        return this._limit;
    }

    protected void setParentEofDetect(boolean var1) {
        if(this._in instanceof IndefiniteLengthInputStream) {
            ((IndefiniteLengthInputStream)this._in).setEofOn00(var1);
        }

    }
}
