package com.yufu.idaas.sdk.bouncycastle.asn1;

import com.yufu.idaas.sdk.bouncycastle.util.io.Streams;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by mac on 2017/1/17.
 */
class DefiniteLengthInputStream extends LimitedInputStream {
    private static final byte[] EMPTY_BYTES = new byte[0];
    private final int _originalLength;
    private int _remaining;

    DefiniteLengthInputStream(InputStream var1, int var2) {
        super(var1, var2);
        if(var2 < 0) {
            throw new IllegalArgumentException("negative lengths not allowed");
        } else {
            this._originalLength = var2;
            this._remaining = var2;
            if(var2 == 0) {
                this.setParentEofDetect(true);
            }

        }
    }

    int getRemaining() {
        return this._remaining;
    }

    public int read() throws IOException {
        if(this._remaining == 0) {
            return -1;
        } else {
            int var1 = this._in.read();
            if(var1 < 0) {
                throw new EOFException("DEF length " + this._originalLength + " object truncated by " + this._remaining);
            } else {
                if(--this._remaining == 0) {
                    this.setParentEofDetect(true);
                }

                return var1;
            }
        }
    }

    public int read(byte[] var1, int var2, int var3) throws IOException {
        if(this._remaining == 0) {
            return -1;
        } else {
            int var4 = Math.min(var3, this._remaining);
            int var5 = this._in.read(var1, var2, var4);
            if(var5 < 0) {
                throw new EOFException("DEF length " + this._originalLength + " object truncated by " + this._remaining);
            } else {
                if((this._remaining -= var5) == 0) {
                    this.setParentEofDetect(true);
                }

                return var5;
            }
        }
    }

    byte[] toByteArray() throws IOException {
        if(this._remaining == 0) {
            return EMPTY_BYTES;
        } else {
            byte[] var1 = new byte[this._remaining];
            if((this._remaining -= Streams.readFully(this._in, var1)) != 0) {
                throw new EOFException("DEF length " + this._originalLength + " object truncated by " + this._remaining);
            } else {
                this.setParentEofDetect(true);
                return var1;
            }
        }
    }
}
