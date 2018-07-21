package com.yufu.idaas.sdk.bouncycastle.asn1;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by mac on 2017/1/17.
 */
class ConstructedOctetStream extends InputStream {
    private final ASN1StreamParser _parser;
    private boolean _first = true;
    private InputStream _currentStream;

    ConstructedOctetStream(ASN1StreamParser var1) {
        this._parser = var1;
    }

    public int read(byte[] var1, int var2, int var3) throws IOException {
        if(this._currentStream == null) {
            if(!this._first) {
                return -1;
            }

            ASN1OctetStringParser var4 = (ASN1OctetStringParser)this._parser.readObject();
            if(var4 == null) {
                return -1;
            }

            this._first = false;
            this._currentStream = var4.getOctetStream();
        }

        int var7 = 0;

        do {
            while(true) {
                int var5 = this._currentStream.read(var1, var2 + var7, var3 - var7);
                if(var5 >= 0) {
                    var7 += var5;
                    break;
                }

                ASN1OctetStringParser var6 = (ASN1OctetStringParser)this._parser.readObject();
                if(var6 == null) {
                    this._currentStream = null;
                    return var7 < 1?-1:var7;
                }

                this._currentStream = var6.getOctetStream();
            }
        } while(var7 != var3);

        return var7;
    }

    public int read() throws IOException {
        if(this._currentStream == null) {
            if(!this._first) {
                return -1;
            }

            ASN1OctetStringParser var1 = (ASN1OctetStringParser)this._parser.readObject();
            if(var1 == null) {
                return -1;
            }

            this._first = false;
            this._currentStream = var1.getOctetStream();
        }

        while(true) {
            int var3 = this._currentStream.read();
            if(var3 >= 0) {
                return var3;
            }

            ASN1OctetStringParser var2 = (ASN1OctetStringParser)this._parser.readObject();
            if(var2 == null) {
                this._currentStream = null;
                return -1;
            }

            this._currentStream = var2.getOctetStream();
        }
    }
}

